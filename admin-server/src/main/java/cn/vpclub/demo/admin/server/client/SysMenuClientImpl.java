package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.domain.entity.SysMenuEntity;
import cn.vpclub.demo.admin.feign.feignClient.SysMenuClient;
import cn.vpclub.demo.admin.server.service.SysMenuService;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.core.model.response.ResponseConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  系统菜单 服务治理实现类
 * </p>
 * @author yk
 * @since  2018-06-01
 */
@RestController
public class SysMenuClientImpl implements SysMenuClient {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(@RequestBody Long parentId) {
        return sysMenuService.queryListParentId(parentId);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return sysMenuService.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        return sysMenuService.getUserMenuList(userId);
    }

    @Override
    public void delete(@RequestBody Long menuId) {
        sysMenuService.delete(menuId);
    }

    @Override
    public List<SysMenuEntity> selectList(@RequestBody Object o) {
        return sysMenuService.selectList(null);
    }

    @Override
    public SysMenuEntity selectById(@RequestBody Long parentId) {
        return sysMenuService.selectById(parentId);
    }

    @Override
    public BaseResponse insert(@RequestBody SysMenuEntity menu) {
        BaseResponse baseResponse = new BaseResponse();
        long now = new Date().getTime();
        menu.setCreatedTime(now);
        menu.setUpdatedTime(now);
        boolean back = sysMenuService.insert(menu);
        baseResponse= ResponseConvert.convert(back);
        return baseResponse;
    }

    @Override
    public BaseResponse updateById(@RequestBody SysMenuEntity menu) {
        BaseResponse baseResponse = new BaseResponse();
        menu.setUpdatedTime(new Date().getTime());
        boolean back = sysMenuService.updateById(menu);
        baseResponse= ResponseConvert.convert(back);
        return baseResponse;
    }
}
