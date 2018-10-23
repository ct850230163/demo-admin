package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.feignClient.SysRoleMenuClient;
import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import cn.vpclub.demo.admin.server.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by chentao on 2018/6/4.
 */
@RestController
public class SysRoleMenuClientImpl implements SysRoleMenuClient {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<Long> queryMenuIdList(@RequestParam("roleId") Long roleId) {
        return sysRoleMenuService.queryMenuIdList(roleId); }

    @Override
    public int deleteBatch(@RequestBody SysRoleParam params) {
        List<Long> roleIds  = params.getRoleIds();
        Long[] ids = (Long[]) roleIds.toArray();
        return sysRoleMenuService.deleteBatch(ids);
    }
}
