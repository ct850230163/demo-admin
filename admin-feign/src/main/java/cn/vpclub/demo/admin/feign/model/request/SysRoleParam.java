package cn.vpclub.demo.admin.feign.model.request;

import cn.vpclub.demo.common.model.core.model.request.PageBaseSearchParam;
import lombok.Data;

import java.util.List;

/**
 * Created by chentao on 2018/6/4.
 */
@Data
public class SysRoleParam extends PageBaseSearchParam {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 菜单ID集合
     */
    private List<Long> menuIdList;

    /**
     * 创建时间
     */
    private Long createdTime;

    /**
     * 角色ID集合
     */
    private List<Long> roleIds;
}
