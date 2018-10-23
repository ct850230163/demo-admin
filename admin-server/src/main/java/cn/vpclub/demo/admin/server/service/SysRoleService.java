package cn.vpclub.demo.admin.server.service;



import cn.vpclub.demo.admin.feign.domain.entity.SysRoleEntity;
import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色 服务类
 * </p>
 * 
 * @author yk
 * @since  2018-06-01
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageResponse queryPage(SysRoleParam params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createdBy);
}
