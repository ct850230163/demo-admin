package cn.vpclub.demo.admin.feign.feignClient;

import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by chentao on 2018/5/31.
 */
@RequestMapping("/sysRoleMenuClient")
public interface SysRoleMenuClient {

    /**
     * 根据角色ID，获取菜单ID列表
     */
    @RequestMapping(value = "queryMenuIdList",method = RequestMethod.GET)
    List<Long> queryMenuIdList(@RequestParam("roleId")Long roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    @RequestMapping(value = "deleteBatch")
    int deleteBatch(SysRoleParam params);
}
