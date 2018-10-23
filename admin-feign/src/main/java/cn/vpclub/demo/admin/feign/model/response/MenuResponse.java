package cn.vpclub.demo.admin.feign.model.response;

import cn.vpclub.demo.admin.feign.domain.entity.SysMenuEntity;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MenuResponse {
    private List<SysMenuEntity> menuList;
    private Set<String> permissions;
}
