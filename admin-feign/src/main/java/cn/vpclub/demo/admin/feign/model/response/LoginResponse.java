package cn.vpclub.demo.admin.feign.model.response;

import cn.vpclub.demo.admin.feign.domain.entity.SysUserEntity;
import lombok.Data;

import java.util.Set;
@Data
public class LoginResponse {
    private SysUserEntity user;
    private String token;
    private Set<String> permissions;
}
