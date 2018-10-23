package cn.vpclub.demo.admin.api.web;

import cn.vpclub.demo.admin.api.common.exception.RRException;
import cn.vpclub.demo.admin.api.service.SysCaptchaService;
import cn.vpclub.demo.admin.feign.domain.entity.SysCaptchaEntity;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import cn.vpclub.demo.admin.feign.model.request.SysLoginForm;
import cn.vpclub.demo.admin.feign.model.response.FailResponse;
import cn.vpclub.demo.admin.feign.model.response.LoginResponse;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.admin.api.service.ShiroService;
import cn.vpclub.demo.admin.api.service.SysUserService;
import cn.vpclub.demo.admin.api.service.SysUserTokenService;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import cn.vpclub.wuhan.redis.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 登录相关
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class SysLoginController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private ShiroService shiroService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private Producer producer;

    /**
     * 验证码
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) {
        if (StringUtil.isEmpty(uuid)){
            throw new RRException("uuid不能为空",ReturnCodeEnum.CODE_1006.getCode());
        }

        try {
            response.setHeader("Cache-Control", "no-store, no-cache");
            response.setContentType("image/jpeg");

            //生成文字验证码
            String code = producer.createText();

            BufferedImage image = producer.createImage(code);

            SysCaptchaEntity sysCaptchaEntity = new SysCaptchaEntity();

            sysCaptchaEntity.setUuid(uuid);
            sysCaptchaEntity.setCode(code);

            sysCaptchaService.insertCaptcha(sysCaptchaEntity);

            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            IOUtils.closeQuietly(out);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseResponse login(@RequestBody SysLoginForm form) {
        BaseResponse baseResponse = null;
        try {
            //验证图片验证码
            boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
            if (!captcha) {
                return new FailResponse(ReturnCodeEnum.CODE_1010,"验证码不正确");
            }
            SysUserEntity user = null;
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            if (pattern.matcher(form.getUsername()).matches() && form.getUsername().length() == 11) {
                SysUserEntity mobileuser = new SysUserEntity();
                mobileuser.setMobile(form.getUsername());

                user = sysUserService.queryByPhone(mobileuser);
                //手机号不存在，密码错误
                if (null == user || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                    baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1003.getCode());
                    baseResponse.setMessage("手机号或密码不正确");
                    return baseResponse;
                }
            } else {
                //用户信息
                user = sysUserService.queryByUserName(form.getUsername());
                //账号不存在、密码错误
                if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                    baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1003.getCode());
                    baseResponse.setMessage("账号或密码不正确");
                    return baseResponse;
                }
            }

            //账号锁定
            if (user.getStatus() == 0) {
                baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
                baseResponse.setMessage("账号已被锁定,请联系管理员");
                return baseResponse;
            }

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUser(user);
            //生成token，并保存到数据库
            BaseResponse tokenResponse = sysUserTokenService.createToken(user.getUserId());
            loginResponse.setToken((String) tokenResponse.getDataInfo());
            Set<String> permissions = shiroService.getUserPermissions(user.getUserId());
            loginResponse.setPermissions(permissions);
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
            baseResponse.setDataInfo(loginResponse);



            return baseResponse;
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            return baseResponse;
        }
    }


    /**
     * 退出
     */
    @PostMapping("/logout")
    public BaseResponse logout(@RequestBody SysUserEntity user) {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        sysUserTokenService.logout(user.getUserId());
        return baseResponse;
    }

}
