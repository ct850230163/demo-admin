package cn.vpclub.demo.admin.feign.domain.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  系统角色 实体
 * </p>
 *
 * @author yk
 * @since 2018-06-01
 *
 */
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	@TableId
	private Long roleId;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

	@TableField(exist=false)
	private List<Long> menuIdList;

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
	 * @param roleId 
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置：角色名称
	 * @param roleName 角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取：角色名称
	 * @return String
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * 设置：备注
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
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
