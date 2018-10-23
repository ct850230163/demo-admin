package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.domain.entity.SysLogEntity;
import cn.vpclub.demo.admin.feign.feignClient.SysLogClient;
import cn.vpclub.demo.admin.feign.model.request.SysLogParam;
import cn.vpclub.demo.admin.server.service.SysLogService;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by chentao on 2018/5/31.
 */
@RestController
public class SysLogClientImpl implements SysLogClient {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public PageResponse queryPage(@RequestBody SysLogParam params) {
        return sysLogService.queryPageResponse(params);
    }

    @Override
    public void insert(@RequestBody SysLogEntity sysLog) {
        sysLogService.insert(sysLog);
    }
}
