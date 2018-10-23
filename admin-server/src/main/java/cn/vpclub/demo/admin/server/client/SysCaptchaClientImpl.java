package cn.vpclub.demo.admin.server.client;

import cn.vpclub.demo.admin.feign.domain.entity.SysCaptchaEntity;
import cn.vpclub.demo.admin.feign.feignClient.SysCaptchaClient;
import cn.vpclub.demo.admin.feign.model.response.SuccessResponse;
import cn.vpclub.demo.admin.server.service.SysCaptchaService;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

/**
 * @program: admin
 * @description:
 * @author: chentao
 * @create: 2018-10-17 19:23
 **/
@RestController
public class SysCaptchaClientImpl implements SysCaptchaClient{

    @Autowired
    private SysCaptchaService sysCaptchaService;


    @Override
    public boolean validate(@RequestParam("uuid") String uuid, @RequestParam("code") String code) {
        return sysCaptchaService.validate(uuid,code);
    }

    @Override
    public BaseResponse insertCaptcha(@RequestBody SysCaptchaEntity sysCaptchaEntity) {
        return sysCaptchaService.insertCaptcha(sysCaptchaEntity);
    }
}
