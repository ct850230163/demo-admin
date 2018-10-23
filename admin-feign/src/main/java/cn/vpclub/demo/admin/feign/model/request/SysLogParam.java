package cn.vpclub.demo.admin.feign.model.request;

import cn.vpclub.demo.common.model.core.model.request.PageBaseSearchParam;
import lombok.Data;

/**
 * Created by chentao on 2018/6/1.
 */
@Data
public class SysLogParam extends PageBaseSearchParam {
    private Long id;
    //用户名
    private String username;
    //用户操作
    private String logOperation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //执行时长(毫秒)
    private Long logTime;
    //IP地址
    private String ip;
    //创建时间
    private Long createDate;
}
