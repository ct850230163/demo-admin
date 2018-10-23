package cn.vpclub.demo.admin.feign.feignClient;

import cn.vpclub.demo.admin.feign.model.request.SysRegisterForm;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/sysRegisterClient")
public interface SysRegisterClient {
    @RequestMapping(value = "register",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse register(@RequestBody SysRegisterForm params);
}
