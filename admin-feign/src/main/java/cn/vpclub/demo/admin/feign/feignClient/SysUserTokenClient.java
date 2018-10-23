package cn.vpclub.demo.admin.feign.feignClient;


import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by chentao on 2018/5/31.
 */
@RequestMapping(value = "/sysUserTokenClient")
public interface SysUserTokenClient {
    /**
     * 生成token
     * @param userId  用户ID
     */
    @RequestMapping(value = "createToken")
    BaseResponse createToken(@RequestParam("userId") long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    @RequestMapping(value = "logout")
    BaseResponse logout(@RequestParam("userId") long userId);
}
