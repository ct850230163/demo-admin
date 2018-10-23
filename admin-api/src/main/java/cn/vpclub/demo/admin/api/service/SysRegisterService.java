package cn.vpclub.demo.admin.api.service;

import cn.vpclub.demo.admin.feign.feignClient.SysRegisterClient;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("${feign-client.admin-server}")
public interface SysRegisterService extends SysRegisterClient {
}
