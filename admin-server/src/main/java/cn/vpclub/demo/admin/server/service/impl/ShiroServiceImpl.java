package cn.vpclub.demo.admin.server.service.impl;


import cn.vpclub.demo.admin.server.dao.SysMenuDao;
import cn.vpclub.demo.admin.server.dao.SysUserDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import cn.vpclub.demo.admin.server.dao.SysUserTokenDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysMenuEntity;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserTokenEntity;
import cn.vpclub.demo.admin.server.service.ShiroService;
import cn.vpclub.demo.admin.feign.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
