package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.feignClient.SysUserTokenClient;

import cn.vpclub.demo.admin.server.service.SysUserTokenService;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chentao on 2018/6/4.
 */
@RestController
public class SysUserTokenClientImpl implements SysUserTokenClient {
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Override
    public BaseResponse createToken(long userId) {
        return sysUserTokenService.createToken(userId);
    }

    @Override
    public BaseResponse logout(long userId) {
        return sysUserTokenService.logout(userId);
    }
}
