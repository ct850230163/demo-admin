package cn.vpclub.demo.admin.feign.domain.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;


/**
 * 系统用户Token
 */
@TableName("sys_user_token")
public class SysUserTokenEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//用户ID
	@TableId(type = IdType.INPUT)
	private Long userId;
	//token
	private String token;
	//过期时间
	private Long expireTime;
	/**
	 * 创建人
	 */
	private Long createdBy;

	/**
	 * 创建时间
	 */
	private Long createdTime;

	/**
	 * 更新人
	 */
	private Long updatedBy;

	/**
	 * 更新时间
	 */
	private Long updatedTime;

	/**
	 * 删除标识(1:在线; 2:删除)
	 */
	private Integer deleted;

	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取：token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
	/**
	 * 获取：过期时间
	 */
	public Long getExpireTime() {
		return expireTime;
	}

	/**
	 * 获取：创建人
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * 设置：创建人
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * 获取：创建时间
	 */
	public Long getCreatedTime() {
		return createdTime;
	}

	/**
	 * 设置：创建时间
	 */
	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * 获取：更新人
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * 设置：更新人
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * 获取：更新时间
	 */
	public Long getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * 设置：更新时间
	 */
	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * 获取：删除标识(1:在线; 2:删除)
	 */
	public Integer getDeleted() {
		return deleted;
	}

	/**
	 * 设置：删除标识(1:在线; 2:删除)
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
}
