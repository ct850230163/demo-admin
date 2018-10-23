package cn.vpclub.demo.admin.feign.feignClient;



import cn.vpclub.demo.admin.feign.domain.entity.SysRoleEntity;
import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * <P>
 *  系统角色 服务治理类
 * </P>
 * @author  yk
 * @since  2018-06-01
 */
@RequestMapping(value = "/sysRoleService")
public interface SysRoleClient {

    @RequestMapping(value = "queryPage",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    PageResponse queryPage(SysRoleParam params);

    @RequestMapping(value = "save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    void save( SysRoleEntity role );

    @RequestMapping(value = "update" ,method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    void update( SysRoleEntity role);

    @RequestMapping(value = "deleteBatch",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteBatch(SysRoleParam params);

    @RequestMapping(value = "queryRoleIdList",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Long> queryRoleIdList( Long createdBy);

    @RequestMapping(value = "selectByParam",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    List<SysRoleEntity> selectByParam(SysRoleEntity entity);

    @RequestMapping(value = "selectById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    SysRoleEntity selectById(Long roleId);
}
