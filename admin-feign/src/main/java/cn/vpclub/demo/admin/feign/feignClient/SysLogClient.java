package cn.vpclub.demo.admin.feign.feignClient;

import cn.vpclub.demo.admin.feign.domain.entity.SysLogEntity;
import cn.vpclub.demo.admin.feign.model.request.SysLogParam;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by chentao on 2018/5/31.
 */
@RequestMapping("/sysLogClient")
public interface SysLogClient {
    @RequestMapping(value = "queryPage",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    PageResponse queryPage(@RequestBody SysLogParam params);

    @RequestMapping(value = "insert",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    void insert(SysLogEntity sysLog);
}
