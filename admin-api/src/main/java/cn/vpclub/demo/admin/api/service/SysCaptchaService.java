package cn.vpclub.demo.admin.api.service;

import cn.vpclub.demo.admin.feign.feignClient.SysCaptchaClient;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @program: admin
 * @description:
 * @author: chentao
 * @create: 2018-10-17 19:18
 **/
@FeignClient("${feign-client.admin-server}")
public interface SysCaptchaService extends SysCaptchaClient{
}
