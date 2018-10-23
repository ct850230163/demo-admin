package cn.vpclub.demo.admin.feign.feignClient;

import cn.vpclub.demo.admin.feign.domain.entity.SysCaptchaEntity;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.image.BufferedImage;

/**
 * @program: admin
 * @description:
 * @author: chentao
 * @create: 2018-10-17 19:21
 **/
@RequestMapping(value = "/sysCaptchaClient")
public interface SysCaptchaClient {


    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    @RequestMapping(value = "validateCaptcha")
    boolean validate(@RequestParam("uuid")String uuid,@RequestParam("code") String code);

    @RequestMapping(value = "insertCaptcha", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    BaseResponse insertCaptcha(SysCaptchaEntity sysCaptchaEntity);
}
