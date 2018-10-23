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

package cn.vpclub.demo.admin.server.service.impl;


import cn.vpclub.demo.admin.feign.model.request.SysLogParam;
import cn.vpclub.demo.admin.server.dao.SysLogDao;
import cn.vpclub.demo.admin.feign.domain.entity.SysLogEntity;
import cn.vpclub.demo.admin.server.service.SysLogService;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import cn.vpclub.demo.common.model.utils.common.JsonUtil;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service("sysLogService")
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageResponse queryPageResponse(SysLogParam params) {
        log.info("sysLogService 日志分页 request：{}", JsonUtil.objectToJson(params));
        PageResponse pageResponse = BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1000.getCode());
        Page<SysLogEntity> page = new Page<SysLogEntity>();
        if (StringUtil.isNotEmpty(params)){
            page.setCurrent(params.getPageNumber());
            page.setSize(params.getPageSize());
        }
        EntityWrapper<SysLogEntity> ew = new EntityWrapper<SysLogEntity>();
        ew.like(StringUtil.isNotEmpty(params.getUsername()),"username",params.getUsername());

        Page<SysLogEntity> selectPage = this.selectPage(page, ew);
        BeanUtils.copyProperties(selectPage, pageResponse);
        return pageResponse;
    }
}
