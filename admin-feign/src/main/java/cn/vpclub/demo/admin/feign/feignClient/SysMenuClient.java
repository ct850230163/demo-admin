package cn.vpclub.demo.admin.feign.feignClient;

import cn.vpclub.demo.admin.feign.domain.entity.SysMenuEntity;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  系统菜单 服务治理
 * </p>
 * @author  yk
 *@since  2018-06-01
 */
@RequestMapping(value = "/sysMenuClient")
public interface SysMenuClient {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    @RequestMapping(value = "queryListParentId",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    List<SysMenuEntity> queryListParentId(@RequestBody Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    @RequestMapping(value = "queryNotButtonList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    @RequestMapping(value = "getUserMenuList")
    List<SysMenuEntity> getUserMenuList(@RequestParam("userId") Long userId);

    /**
     * 删除
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    void delete(@RequestBody Long menuId);

    @RequestMapping(value = "/selectList" ,method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    List<SysMenuEntity> selectList( Object o);

    @RequestMapping(value = "/selectById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    SysMenuEntity selectById(@RequestBody Long parentId);

    @RequestMapping(value = "/insert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse insert(@RequestBody SysMenuEntity menu);

    @RequestMapping(value = "/updateById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse updateById(@RequestBody  SysMenuEntity menu);
}
