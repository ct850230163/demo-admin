package cn.vpclub.demo.admin.api.web;


import cn.vpclub.demo.admin.api.annotation.SysLog;
import cn.vpclub.demo.admin.api.common.validator.Assert;
import cn.vpclub.demo.admin.api.common.validator.ValidatorUtils;
import cn.vpclub.demo.admin.api.common.validator.group.AddGroup;
import cn.vpclub.demo.admin.api.common.validator.group.UpdateGroup;
import cn.vpclub.demo.admin.api.service.SysUserRoleService;
import cn.vpclub.demo.admin.api.service.SysUserService;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import cn.vpclub.demo.admin.feign.model.request.PasswordForm;
import cn.vpclub.demo.admin.feign.model.request.SysUserParam;
import cn.vpclub.demo.admin.feign.model.response.SuccessResponse;
import cn.vpclub.demo.admin.feign.utils.Constant;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 所有用户列表
     */
    @PostMapping("/list")
    @RequiresPermissions("sys:user:list")
    public PageResponse list(@RequestBody SysUserParam params) {
        PageResponse page = new PageResponse();
        //只有超级管理员，才能查看所有管理员列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            page.setMessage("你不是超级管理员，不能查看所有管理员列表");
        }
        page = sysUserService.queryPage(params);
        return page;
    }



    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public BaseResponse password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
            baseResponse.setMessage("原密码不正确");
            return baseResponse;
        }

        return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
    }

    /**
     * 当前登陆用户信息
     */
    @PostMapping("/info")
    public BaseResponse info() {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        try {
            Long userId = this.getUserId();
            SysUserEntity user;
            if (null != userId && userId.intValue() > 0) {

                user = sysUserService.selectById(userId);
                baseResponse.setDataInfo(user);
                return baseResponse;
            } else {
                user = getUser();
                baseResponse.setDataInfo(user);
                return baseResponse;
            }
        } catch (Exception e) {
            log.error("错误日志 ：",e);
            return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
        }
    }

    /**
     * 用户信息
     */
    @PostMapping("/querUserinfo")
    @RequiresPermissions("sys:user:info")
    public BaseResponse info(@RequestBody SysUserParam param){
        BaseResponse baseResponse = null;
        SysUserEntity user = sysUserService.selectById(param.getUserId());

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(param.getUserId());
        user.setRoleIdList(roleIdList);
        baseResponse = new SuccessResponse(ReturnCodeEnum.CODE_1000.getCode());
        baseResponse.setDataInfo(user);
        return baseResponse;
    }

    /**
     * 保存用户
     */
    @PostMapping("/save")
    @SysLog("保存用户")
    @RequiresPermissions("sys:user:save")
    public BaseResponse save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(getUserId());
        user.setCreatedBy(getUserId());
        user.setUpdatedBy(getUserId());
        sysUserService.save(user);

        return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
    }

    /**
     * 修改用户
     */
    @PostMapping("/update")
    @SysLog("修改用户")
    @RequiresPermissions("sys:user:update")
    public BaseResponse update(@RequestBody SysUserEntity user) {
        try {
            ValidatorUtils.validateEntity(user, UpdateGroup.class);
            user.setCreateUserId(getUserId());
            user.setUpdatedBy(getUserId());
            sysUserService.update(user);
            return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        } catch (Exception e) {
            log.error("错误日志 ：",e);
            return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1005.getCode());
        }
    }

    /**
     * 删除用户
     */
    @PostMapping("/delete")
    @SysLog("删除用户")
    @RequiresPermissions("sys:user:delete")
    public BaseResponse delete(@RequestBody SysUserParam param) {
        BaseResponse baseResponse = new BaseResponse();
        List<Long> userIds = param.getIdList();
        if (userIds.contains(1L)) {
            baseResponse.setMessage("系统管理员不能删除");
            return baseResponse;
        }

        if (userIds.contains(getUserId())) {
            baseResponse.setMessage("当前用户不能删除");
            return baseResponse;
        }
        sysUserService.deleteByUserId(param);

        return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
    }

    @PostMapping("/queryByParentIdList")
    public BaseResponse queryByParentIdList() {
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        //获取当前登陆用户信息
        SysUserEntity user = getUser();

        List<SysUserEntity> listParentId = new ArrayList<>();
        //如果当然登陆的用户是主账号，则查找该主账号下面的所有子账号
        if (user.getParentId() != null && user.getParentId() == 0) {
            listParentId = sysUserService.queryByParentId(user.getUserId().toString());
            baseResponse.setDataInfo(listParentId);
            return baseResponse;
        }
        baseResponse.setDataInfo(listParentId);
        return baseResponse;
    }
}
