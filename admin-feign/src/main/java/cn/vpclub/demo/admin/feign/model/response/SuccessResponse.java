package cn.vpclub.demo.admin.feign.model.response;


import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.utils.common.JsonUtil;

/**
 * Created by vpyqz on 2017/1/6.
 */
public class SuccessResponse extends BaseResponse {

    public SuccessResponse(){
        super();
        this.setMessage(ReturnCodeEnum.CODE_1000.getValue());
        this.setReturnCode(ReturnCodeEnum.CODE_1000.getCode());
    }

    public SuccessResponse(Object object){
        this();
        this.setDataInfo(object);
    }

    public SuccessResponse(String msg){
        this();
        this.setMessage(msg);
    }

    public SuccessResponse(String msg,Object object){
        this();
        this.setMessage(msg);
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
