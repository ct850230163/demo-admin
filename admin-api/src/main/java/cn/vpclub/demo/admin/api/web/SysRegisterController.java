package cn.vpclub.demo.admin.api.web;

import cn.vpclub.demo.admin.feign.model.request.SysRegisterForm;
import cn.vpclub.demo.admin.api.service.SysRegisterService;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/register")
public class SysRegisterController {
    @Autowired
    private SysRegisterService sysRegisterService;

    //
//    @GetMapping("mapper.sys/registerCaptcha")
//    public Map<String, Object> registerCaptcha(@RequestParam("mobile") String mobile) {
//        try {
//            //判断手机号是否填写正确
//            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
//            if (!pattern.matcher(mobile).matches() && mobile.length() != 11) {
//                return BaseResponse.error("该手机号不存在");
//            }
//            //查找该手机号是否已注册
//            SysUserEntity conditionUser = new SysUserEntity();
//            conditionUser.setMobile(mobile);
//            EntityWrapper<SysUserEntity> condition = new EntityWrapper<>(conditionUser);
//            SysUserEntity userEntity = sysUserService.selectOne(condition);
//            if (null != userEntity) {
//                return BaseResponse.error("该手机已被注册！");
//            }
//            //生成验证码并存发送到用户手机后将验证码放入redis
//            int captcha = (int) ((Math.random() * 9 + 1) * 100000);
//            SmsResponse s = sendSms.sendCodeSms(mobile, String.valueOf(captcha), false);
//            cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.set(mobile + "registerCaptcha", captcha, 60 * 10);
//            return BaseResponse.success();
//        } catch (Exception e) {
//            return BaseResponse.error();
//        }
//    }*/
    @PostMapping("/register")
    public BaseResponse register(@RequestBody SysRegisterForm form) {
//        try {
//            /*//校验验证码是否正确
//            if (!form.getCode().equals(cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.get(form.getMobile() + "registerCaptcha"))) {
//                return BaseResponse.error("验证码不正确");
//            }*/
//            //校验用户名是否已存在
//            SysUserEntity confirmUsername = sysUserService.queryByUserName(form.getUsername());
//            if (null != confirmUsername) {
//                return R.error("用户名已存在");
//            }
//            //验证手机号是否存在
//            SysUserEntity registerUser = new SysUserEntity();
//            registerUser.setMobile(form.getMobile());
//            EntityWrapper<SysUserEntity> conditionWrap = new EntityWrapper<>(registerUser);
//            SysUserEntity confirmMobile = sysUserService.selectOne(conditionWrap);
//            if (null != confirmMobile) {
//                return R.error("该手机已绑定");
//            }
//            //将注册用户信息插入数据库
//            registerUser.setUserId(IdWorker.getId());
//            registerUser.setParentId(0L);
//            registerUser.setUsername(form.getUsername());
//            String salt = RandomStringUtils.randomAlphanumeric(20);
//            registerUser.setPassword(new Sha256Hash(form.getPassword(), salt).toHex());
//            registerUser.setSalt(salt);
//            registerUser.setCreateUserId(registerUser.getUserId());
//            registerUser.setCreateTime(new Date());
//            sysUserService.register(registerUser);
//            SysUserRoleEntity userRoleEntity=new SysUserRoleEntity();
//            userRoleEntity.setRoleId(1L);
//            userRoleEntity.setUserId(registerUser.getUserId());
//            sysUserRoleService.insert(userRoleEntity);
//            return R.ok("注册成功");
//        } catch (Exception e) {
//            return R.error();
//        }

        return sysRegisterService.register(form);
    }
}
