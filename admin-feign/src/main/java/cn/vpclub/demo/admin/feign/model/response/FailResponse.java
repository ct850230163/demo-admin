package cn.vpclub.demo.admin.feign.model.response;


import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.utils.common.JsonUtil;
import lombok.ToString;

/**
 * Created by vpyqz on 2017/1/6.
 */

@ToString
public class FailResponse extends BaseResponse {
    public FailResponse(){
        super();
        this.setMessage(ReturnCodeEnum.CODE_1005.getValue());
        this.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
    }

    public FailResponse(String errMsg){
        super();
        this.setMessage(errMsg);
        this.setReturnCode(ReturnCodeEnum.CODE_1005.getCode());
    }

    public FailResponse(ReturnCodeEnum returnCodeEnum){
        super();
        this.setMessage(returnCodeEnum.getValue());
        this.setReturnCode(returnCodeEnum.getCode());
    }

    public FailResponse(ReturnCodeEnum returnCodeEnum, String errMsg){
        this(returnCodeEnum);
        this.setMessage(errMsg);
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
