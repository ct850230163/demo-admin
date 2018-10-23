package cn.vpclub.demo.admin.feign.feignClient;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by chentao on 2018/5/31.
 */
@RequestMapping(value = "/sysUserRoleClient")
public interface SysUserRoleClient {
    /*@RequestMapping(value = "saveOrUpdate")
    void saveOrUpdate(@RequestParam("userId") Long userId, List<Long> roleIdList,Long createdBy);*/

    /**
     * 根据用户ID，获取角色ID列表
     */
    @RequestMapping(value = "queryRoleIdList")
    List<Long> queryRoleIdList(@RequestParam("userId") Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
  /*  @RequestMapping(value = "deleteBatch")
    int deleteBatch( SysRoleParam params);*/
}
