package cn.vpclub.demo.admin.server.service.impl;

import cn.vpclub.demo.admin.server.dao.SysRoleMenuDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysRoleMenuEntity;
import cn.vpclub.demo.admin.server.service.SysRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList,Long createdBy,Long updatedBy,Long createdTime,Long updatedTime) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});
		if(null == menuIdList || menuIdList.size() == 0){
			return ;
		}
		//保存角色与菜单关系
		List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
		for(Long menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setRoleId(roleId);
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setCreatedBy(createdBy);
			sysRoleMenuEntity.setUpdatedBy(updatedBy);
			sysRoleMenuEntity.setCreatedTime(createdTime);
			sysRoleMenuEntity.setUpdatedTime(updatedTime);
			list.add(sysRoleMenuEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
