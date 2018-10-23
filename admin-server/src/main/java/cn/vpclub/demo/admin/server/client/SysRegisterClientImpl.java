package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserRoleEntity;
import cn.vpclub.demo.admin.feign.feignClient.SysRegisterClient;
import cn.vpclub.demo.admin.feign.model.request.SysRegisterForm;
import cn.vpclub.demo.admin.server.service.SysUserRoleService;
import cn.vpclub.demo.admin.server.service.SysUserService;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Slf4j
public class SysRegisterClientImpl implements SysRegisterClient {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public BaseResponse register(@RequestBody SysRegisterForm form) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            /*//校验验证码是否正确
            if (!form.getCode().equals(cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.get(form.getMobile() + "registerCaptcha"))) {
                return BaseResponse.error("验证码不正确");
            }*/
            //校验用户名是否已存在
            SysUserEntity confirmUsername = sysUserService.queryByUserName(form.getUsername());
            if (null != confirmUsername) {
                baseResponse.setReturnCode(ReturnCodeEnum.CODE_1003.getCode());
                baseResponse.setMessage("用户名已存在");
                return baseResponse;
            }
            //验证手机号是否存在
            SysUserEntity registerUser = new SysUserEntity();
            registerUser.setMobile(form.getMobile());
            EntityWrapper<SysUserEntity> conditionWrap = new EntityWrapper<>(registerUser);
            SysUserEntity confirmMobile = sysUserService.selectOne(conditionWrap);
            if (null != confirmMobile) {
                baseResponse.setReturnCode(ReturnCodeEnum.CODE_1003.getCode());
                baseResponse.setMessage("该手机已绑定");
                return baseResponse;
            }
            //将注册用户信息插入数据库
            registerUser.setUserId(IdWorker.getId());
            registerUser.setParentId(0L);
            registerUser.setUsername(form.getUsername());
            String salt = RandomStringUtils.randomAlphanumeric(20);
            registerUser.setPassword(new Sha256Hash(form.getPassword(), salt).toHex());
            registerUser.setSalt(salt);
            registerUser.setStatus(1);
            registerUser.setCreateUserId(registerUser.getUserId());
            registerUser.setCreatedBy(registerUser.getCreateUserId());
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Long nowTime = Long.valueOf(sdf.format(new Date()));
            registerUser.setCreatedTime(new Date().getTime());
            registerUser.setUpdatedBy(registerUser.getCreateUserId());
            registerUser.setUpdatedTime(registerUser.getCreatedTime());
            sysUserService.register(registerUser);
            SysUserRoleEntity userRoleEntity=new SysUserRoleEntity();
            userRoleEntity.setRoleId(1L);
            userRoleEntity.setUserId(registerUser.getUserId());
            userRoleEntity.setCreatedBy(registerUser.getUserId());
            userRoleEntity.setCreatedTime(registerUser.getCreatedTime());
            userRoleEntity.setUpdatedBy(registerUser.getUserId());
            userRoleEntity.setUpdatedTime(registerUser.getCreatedTime());
            sysUserRoleService.insert(userRoleEntity);
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
            baseResponse.setMessage("注册成功");

        } catch (Exception e) {
            log.error(e.getMessage());
            baseResponse.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
            baseResponse.setMessage(ReturnCodeEnum.CODE_1005.getValue());
        }
        return baseResponse;
    }
}
