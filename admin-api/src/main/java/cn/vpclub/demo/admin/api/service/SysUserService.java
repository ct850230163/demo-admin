package cn.vpclub.demo.admin.api.service;

import cn.vpclub.demo.admin.feign.feignClient.SysUserClient;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by chentao on 2018/5/31.
 */
@FeignClient("${feign-client.admin-server}")
public interface SysUserService  extends SysUserClient {
}
