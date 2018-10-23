package cn.vpclub.demo.admin.feign.domain.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 角色与菜单对应关系 实体
 * 
 * @author yk
 * @since 2018-06-01
 */
@TableName("sys_role_menu")
public class SysRoleMenuEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;

	/**
	 * 创建人
	 */
	@TableField("created_by")
	private Long createdBy;

	/**
	 * 创建时间
	 */
	@TableField("created_time")
	private Long createdTime;

	/**
	 * 更新人
	 */
	@TableField("updated_by")
	private Long updatedBy;

	/**
	 * 更新时间
	 */
	@TableField("updated_time")
	private Long updatedTime;

	/**
	 * 删除标识(1:在线; 2:删除)
	 */
	@TableField("deleted")
	private Integer deleted;

	/**
	 * 设置：
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置：角色ID
	 * @param roleId 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：角色ID
	 * @return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置：菜单ID
	 * @param menuId 菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取：菜单ID
	 * @return Long
	 */
	public Long getMenuId() {
		return menuId;
	}

	public Long getCreatedBy() { return createdBy; }

	public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }

	public Long getCreatedTime() { return createdTime; }

	public void setCreatedTime(Long createdTime) { this.createdTime = createdTime; }

	public Long getUpdatedBy() { return updatedBy; }

	public void setUpdatedBy(Long updatedBy) { this.updatedBy = updatedBy; }

	public Long getUpdatedTime() { return updatedTime; }

	public void setUpdatedTime(Long updatedTime) { this.updatedTime = updatedTime; }

	public Integer getDeleted() { return deleted; }

	public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
