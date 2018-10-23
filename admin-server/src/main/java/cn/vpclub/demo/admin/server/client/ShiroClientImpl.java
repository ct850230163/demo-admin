package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserTokenEntity;
import cn.vpclub.demo.admin.feign.feignClient.ShiroClient;
import cn.vpclub.demo.admin.server.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by chentao on 2018/6/4.
 */
@RestController
public class ShiroClientImpl implements ShiroClient {
    @Autowired
    private ShiroService shiroService;

    @Override
    public Set<String> getUserPermissions( long userId) {
        return shiroService.getUserPermissions(userId);
    }

    @Override
    public SysUserTokenEntity queryByToken(@RequestParam("token") String token) {
        return shiroService.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(@RequestParam("userId") Long userId) {
        return shiroService.queryUser(userId);
    }
}
