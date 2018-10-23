package cn.vpclub.demo.admin.server.service.impl;



import cn.vpclub.demo.admin.server.dao.SysRoleDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysRoleEntity;
import cn.vpclub.demo.admin.feign.model.request.SysRoleParam;
import cn.vpclub.demo.admin.server.service.SysRoleMenuService;
import cn.vpclub.demo.admin.server.service.SysRoleService;
import cn.vpclub.demo.admin.server.service.SysUserRoleService;
import cn.vpclub.demo.admin.server.service.SysUserService;
import cn.vpclub.demo.admin.feign.utils.Constant;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import cn.vpclub.demo.common.model.utils.common.JsonUtil;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * 系统角色 服务实现类
 *
 * @author yk
 * @since 2018-06-01
 */

@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {


	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

	@Override
	public PageResponse queryPage(SysRoleParam params) {
		log.info("系统角色列表分页 request: {}", JsonUtil.objectToJson(params));
		PageResponse pageResponse = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1000.getCode());
		Page<SysRoleEntity> page= new Page<>();
		if(StringUtil.isNotEmpty(params)){
			page.setCurrent(params.getPageNumber());
			page.setSize(params.getPageSize());
		}
		EntityWrapper<SysRoleEntity> ew = new EntityWrapper<>();
		ew.like(StringUtil.isNotEmpty(params.getRoleName()),"role_name",params.getRoleName()).eq(StringUtil.isNotEmpty(params.getCreatedBy()),"created_by",params.getCreatedBy());
		Page<SysRoleEntity> selectPage=this.selectPage(page,ew);
		BeanUtils.copyProperties(selectPage,pageResponse);
		return pageResponse;
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleEntity role) {
		//当前时间
		Date nowTime =new Date();
		//时间戳转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(nowTime);
		try {
			role.setCreatedTime(Long.valueOf(sdf.parse(format).getTime()));
			role.setUpdatedTime(Long.valueOf(sdf.parse(format).getTime()));
		}catch (Exception e){
		}
        this.insert(role);
        //检查权限是否越权
        checkPrems(role);
        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList(),role.getCreatedBy(),role.getUpdatedBy(),role.getCreatedTime(),role.getUpdatedTime());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
		//当前时间
		Date nowTime =new Date();
		//时间戳转换
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(nowTime);
		try {
			role.setCreatedTime(Long.valueOf(sdf.parse(format).getTime()));
			role.setUpdatedTime(Long.valueOf(sdf.parse(format).getTime()));
		}catch (Exception e){
		}
        this.updateById(role);
        //检查权限是否越权
        checkPrems(role);
        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList(),role.getCreatedBy(),role.getUpdatedBy(),role.getCreatedTime(),role.getUpdatedTime());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.deleteBatchIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreatedBy() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreatedBy());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			log.error("新增角色的权限，已超出你的权限范围");
			return;
		}
	}

}