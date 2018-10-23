/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cn.vpclub.demo.admin.api.web;


import cn.vpclub.demo.admin.feign.model.request.SysRegisterForm;
import cn.vpclub.demo.admin.api.service.SysRefindPasswordService;

import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/refindPassword")
public class SysRefindPasswordController {

    @Autowired
    private SysRefindPasswordService sysRefindPasswordService;

///*
//    @Autowired
//    SendSms sendSms;
//
//    @GetMapping("mapper.sys/refindStep1")
//    public Map<String, Object> refindStep1(@RequestParam("mobile") String mobile) {
//        try {
//            //判断手机号是否填写正确
//            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
//            if (!pattern.matcher(mobile).matches() && mobile.length() != 11) {
//                return BaseResponse.error("该手机号不存在");
//            }
//            //判断是否存在该用户
//            SysUserEntity conditionUser = new SysUserEntity();
//            conditionUser.setMobile(mobile);
//            EntityWrapper<SysUserEntity> condition = new EntityWrapper<>(conditionUser);
//            SysUserEntity userEntity = sysUserService.selectOne(condition);
//            if (null == userEntity) {
//                return BaseResponse.error("该用户不存在");
//            }
//            // 发验证码
//            int captcha = (int) ((Math.random() * 9 + 1) * 100000);
//            sendSms.sendCodeSms(mobile, String.valueOf(captcha), false);
//            cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.set(mobile + "RefindCaptcha", captcha, 60 * 10);
//            return BaseResponse.success();
//        } catch (Exception e) {
//            return BaseResponse.error();
//        }
//    }
//
//    @GetMapping("mapper.sys/refindStep2")
//    public Map<String, Object> refindStep2(@RequestParam("mobile") String mobile, @RequestParam("msgCaptcha") String msgCaptcha) {
//        try {
//            //从redis获取验证码进行验证
//            String validateCaptcha = cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.get(mobile + "RefindCaptcha");
//
//            if (null == validateCaptcha || validateCaptcha.equals("")) {
//                return BaseResponse.error("验证码失效");
//            }
//            if (!msgCaptcha.equals(validateCaptcha)) {
//                return BaseResponse.error("验证码不正确");
//            }
//
//            //设置用户找回密码的当前状态
//            cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.set(mobile + "RefindStatus", "validateCaptcha", 60 * 10);
//            return BaseResponse.success();
//        } catch (Exception e) {
//            return BaseResponse.error();
//        }
//    }*/
//
    @PostMapping("/refindStep3")
    public BaseResponse refindStep3(@RequestBody SysRegisterForm form) {
/*        try {

            //判断当前找回密码的状态
           *//* if (!cn.vpclub.autoconfiguration.cn.vpclub.wuhan.redis.get(form.getMobile() + "RefindStatus").equals("validateCaptcha")) {
                return BaseResponse.error("你越界了哦！");
            }*//*
            String salt = RandomStringUtils.randomAlphanumeric(20);
            SysUserEntity sysUserEntity = new SysUserEntity();
            sysUserEntity.setMobile(form.getMobile());
            EntityWrapper<SysUserEntity> condition = new EntityWrapper<>(sysUserEntity);

            //查询出该用户信息
            sysUserEntity = sysUserService.selectOne(condition);

            //修改密码和盐
            sysUserEntity.setPassword(new Sha256Hash(form.getPassword(), salt).toHex());
            sysUserEntity.setSalt(salt);
            sysUserService.updateById(sysUserEntity);

            return R.success();
        } catch (Exception e) {
            return R.error();
        }*/
        return sysRefindPasswordService.refindStep3(form);
    }
}
