

package cn.vpclub.demo.admin.api.web;



import cn.vpclub.demo.admin.api.annotation.SysLog;
import cn.vpclub.demo.admin.api.common.exception.RRException;
import cn.vpclub.demo.admin.feign.domain.entity.SysMenuEntity;
import cn.vpclub.demo.admin.feign.model.request.SysMenuParam;
import cn.vpclub.demo.admin.feign.model.response.MenuResponse;
import cn.vpclub.demo.admin.api.service.ShiroService;
import cn.vpclub.demo.admin.api.service.SysMenuService;
import cn.vpclub.demo.admin.feign.utils.Constant;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单 前端控制器
 *
 * @author yk
 * @since 2018-06-01
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private ShiroService shiroService;

	/**
	 * 导航菜单
	 */
	@PostMapping("/nav")
	public BaseResponse nav() {
		BaseResponse baseResponse = new BaseResponse();
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());

		MenuResponse menuResponse = new MenuResponse();
		menuResponse.setMenuList(menuList);
		menuResponse.setPermissions(permissions);

		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
		baseResponse.setDataInfo(menuResponse);
		return baseResponse;
	}

	/**
	 * 所有菜单列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public PageResponse list(){
		PageResponse pageResponse = new PageResponse();
		SysMenuParam param = new SysMenuParam();
		List<SysMenuEntity> menuList = sysMenuService.selectList(param);
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.selectById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getMenuName());
			}
		}
		pageResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		pageResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
		pageResponse.setRecords(menuList);
		return pageResponse;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@PostMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public BaseResponse select() {
		BaseResponse baseResponse = new BaseResponse();
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setMenuName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
		baseResponse.setDataInfo(menuList);
		return baseResponse;
	}

	/**
	 * 菜单信息
	 */

	@PostMapping("/info")
	@RequiresPermissions("sys:menu:info")
	public BaseResponse info(@RequestBody SysMenuParam param){
		BaseResponse baseResponse = new BaseResponse();
		if (StringUtil.isEmpty(param.getMenuId())){
			baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
			baseResponse.setMessage(ReturnCodeEnum.CODE_1006.getValue());
			return baseResponse;
		}else {
			SysMenuEntity menu = sysMenuService.selectById(param.getMenuId());
			baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
			baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
			baseResponse.setDataInfo(menu);
			return baseResponse;
		}
	}

	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public BaseResponse save(@RequestBody SysMenuEntity menu){
		BaseResponse baseResponse = new BaseResponse();
		//数据校验
		verifyForm(menu);
		menu.setCreatedBy(getUserId());
		menu.setUpdatedBy(getUserId());
		baseResponse = sysMenuService.insert(menu);

		return baseResponse;
	}

	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public BaseResponse update(@RequestBody SysMenuEntity menu){
		BaseResponse baseResponse = new BaseResponse();
		//数据校验
		verifyForm(menu);
		menu.setUpdatedBy(getUserId());
		baseResponse = sysMenuService.updateById(menu);
		return baseResponse;
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@PostMapping("/delete")
	@RequiresPermissions("sys:menu:delete")
	public BaseResponse delete(@RequestBody SysMenuParam param) {
		BaseResponse baseResponse = new BaseResponse();
		if (StringUtil.isEmpty(param.getMenuId())){
			baseResponse.setReturnCode(ReturnCodeEnum.CODE_1006.getCode());
			baseResponse.setMessage(ReturnCodeEnum.CODE_1006.getValue());
			return baseResponse;
		}else {
			Long menuId = param.getMenuId();
			if (menuId <= 31) {
				baseResponse.setReturnCode(ReturnCodeEnum.CODE_1003.getCode());
				baseResponse.setMessage("系统菜单，不能删除");
				return baseResponse;
			}

			//判断是否有子菜单或按钮
			List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
			if (menuList.size() > 0) {
				baseResponse.setReturnCode(ReturnCodeEnum.CODE_1003.getCode());
				baseResponse.setMessage("请先删除子菜单或按钮");
				return baseResponse;
			}

			sysMenuService.delete(menuId);

			baseResponse.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
			baseResponse.setMessage(ReturnCodeEnum.CODE_1000.getValue());
			return baseResponse;
		}
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu) {
		if(StringUtils.isBlank(menu.getMenuName())){
			throw new RRException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}

		//菜单
		if(menu.getMenuType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}

		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.selectById(menu.getParentId());
			parentType = parentMenu.getMenuType();
		}

		//目录、菜单
		if(menu.getMenuType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getMenuType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getMenuType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
