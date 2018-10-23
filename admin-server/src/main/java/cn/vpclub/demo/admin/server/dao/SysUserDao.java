package cn.vpclub.demo.admin.server.dao;

import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Repository
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	void deleteByUserIds(List<Long> ids);

	/**
	 * 添加新用户
	 */
	void addUser(SysUserEntity userEntity);

	/**
	 * 查询主账号下所有子账号
	 */
	List<SysUserEntity> queryByParentId(String parentId);

}
