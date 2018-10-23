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


import cn.vpclub.demo.admin.feign.domain.entity.SysConfigEntity;
import cn.vpclub.demo.admin.feign.model.request.SysConfigParam;
import cn.vpclub.demo.admin.server.dao.SysConfigDao;
import cn.vpclub.demo.admin.server.redis.SysConfigRedis;
import cn.vpclub.demo.admin.server.service.SysConfigService;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import cn.vpclub.demo.common.model.utils.common.JsonUtil;
import cn.vpclub.demo.common.model.utils.common.StringUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("sysConfigService")
@Slf4j
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;

	@Override
	public PageResponse queryPage(SysConfigParam params) {
		log.info("sysConfigService 日志分页 request:{}", JsonUtil.objectToJson(params));
		PageResponse pageResponse= BackResponseUtil.getPageResponse(ReturnCodeEnum.CODE_1000.getCode());
		Page<SysConfigEntity> page=new Page<SysConfigEntity>();
		if(StringUtil.isNotEmpty(params)){
			page.setCurrent(params.getPageNumber());
			page.setSize(params.getPageSize());
		}
		EntityWrapper<SysConfigEntity> ew=new EntityWrapper<SysConfigEntity>();
		ew.like(StringUtil.isNotEmpty(params.getParamKey()),"param_key",params.getParamKey());
//		ew.eq(StringUtil.isNotEmpty(params.getDeleted()),"deleted",params.getDeleted());
		ew.eq("deleted",1);
		Page<SysConfigEntity> selectPage=this.selectPage(page,ew);
		try {
			BeanUtils.copyProperties(selectPage, pageResponse);
		}catch (Exception e){
			log.info("BeanUtils.copyProperties异常,exception:{}",e);
		}
		return pageResponse;
	}

	@Override
	public void save(SysConfigEntity config) {
		long currentTime=System.currentTimeMillis();
		config.setCreatedTime(currentTime);
		config.setUpdatedTime(currentTime);
		this.insert(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		long currentTime=System.currentTimeMillis();
		config.setUpdatedTime(currentTime);
		this.updateById(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)

	public void deleteBatch(SysConfigParam params) {
		for (Long id : params.getIds()) {
			SysConfigEntity tempConfig = this.selectById(id);
			sysConfigRedis.delete(tempConfig.getParamKey());
			tempConfig.setDeleted(2);
			tempConfig.setUpdatedBy(params.getUserId());
			tempConfig.setUpdatedTime(System.currentTimeMillis());
			this.updateById(tempConfig);

		}
	}
	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return JSON.parseObject(key,clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			log.info("获取参数失败,exception:{}",e);
		}
		return null;
	}
}
