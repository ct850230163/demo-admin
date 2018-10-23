package cn.vpclub.demo.admin.api.web;


import cn.vpclub.demo.admin.api.annotation.SysLog;
import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.admin.api.common.validator.ValidatorUtils;
import cn.vpclub.demo.admin.feign.domain.entity.SysRoleEntity;
import cn.vpclub.demo.admin.api.service.SysRoleMenuService;
import cn.vpclub.demo.admin.api.service.SysRoleService;
import cn.vpclub.demo.admin.feign.utils.Constant;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/***
 * <p>
 *  系统角色管理 前端控制器
 * </p>
 * @author  yk
 * @since  2018-06-01
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysRoleMenuService sysRoleMenuService;


	/**
	 * 角色列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:role:list")
	public PageResponse list(@RequestBody SysRoleParam params){
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.setCreatedBy(getUserId());
		}
		PageResponse pageResponse = sysRoleService.queryPage(params);
		pageResponse.setMessage("成功");
		return pageResponse;
	}

	/**
	 * 角色列表
	 */
	@PostMapping("/select")
	@RequiresPermissions("sys:role:select")
	public BaseResponse select(){

		SysRoleEntity entity = new SysRoleEntity();
		BaseResponse baseResponse = new BaseResponse();
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(getUserId() != Constant.SUPER_ADMIN){
			entity.setCreatedBy(getUserId());
		}
		List<SysRoleEntity> list = sysRoleService.selectByParam(entity);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
		baseResponse.setDataInfo(list);
		return baseResponse;
	}

	/**
	 * 角色信息
	 */
	@PostMapping ("/info")
	@RequiresPermissions("sys:role:info")
	public BaseResponse info(@RequestBody SysRoleParam params){
		BaseResponse baseResponse = new BaseResponse();
		if(StringUtil.isEmpty(params.getRoleId())){
			baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
			baseResponse.setMessage("参数不能为空");
			return baseResponse;
		}
		SysRoleEntity role = sysRoleService.selectById(params.getRoleId());
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(params.getRoleId());
		role.setMenuIdList(menuIdList);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage("成功");
		baseResponse.setDataInfo(role);
		return baseResponse;
	}

	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public BaseResponse save(@RequestBody SysRoleEntity role){
		BaseResponse baseResponse = new BaseResponse ();
		ValidatorUtils.validateEntity(role);
		role.setCreatedBy(getUserId());
		role.setUpdatedBy(getUserId());
		sysRoleService.save(role);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage("成功");
		return baseResponse;
	}

	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public BaseResponse update(@RequestBody SysRoleEntity role){
		BaseResponse baseResponse = new BaseResponse ();
		ValidatorUtils.validateEntity(role);
		role.setUpdatedBy(getUserId());
		sysRoleService.update(role);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage("成功");
		return baseResponse;
	}

	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public BaseResponse delete(@RequestBody SysRoleParam params){
		BaseResponse baseResponse = new BaseResponse ();
		sysRoleService.deleteBatch(params);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage("成功");
		return baseResponse;
	}

}


