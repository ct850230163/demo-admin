package cn.vpclub.demo.admin.server.service.impl;


import cn.vpclub.demo.admin.server.dao.SysUserTokenDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysUserTokenEntity;
import cn.vpclub.demo.admin.server.service.SysUserTokenService;
import cn.vpclub.demo.admin.feign.utils.TokenGenerator;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;

    BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());

    @Override
    public BaseResponse createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date nowTime = new Date();
        //时间类型转换
        Long now = System.currentTimeMillis();
        //过期时间
//		Date expireTime = new Date(nowTime.getTime() + EXPIRE * 1000);

        Long expireTime = (nowTime.getTime() + EXPIRE * 1000);
        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdatedTime(now);
            tokenEntity.setExpireTime(expireTime);
            tokenEntity.setCreatedBy(userId);
            tokenEntity.setCreatedTime(now);
            tokenEntity.setUpdatedBy(userId);

            //保存token
            this.insert(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdatedTime(now);
            tokenEntity.setExpireTime(expireTime);
            tokenEntity.setUpdatedBy(userId);
            //更新token
            this.updateById(tokenEntity);
        }

        baseResponse.setDataInfo(token);
        return baseResponse;
    }

    @Override
    public BaseResponse logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
        BaseResponse baseResponse = BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
        baseResponse.setDataInfo(tokenEntity);
        return baseResponse;
    }
}
