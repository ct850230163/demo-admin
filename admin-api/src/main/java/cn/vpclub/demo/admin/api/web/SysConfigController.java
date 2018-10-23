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



import cn.vpclub.demo.admin.api.common.validator.ValidatorUtils;

import cn.vpclub.demo.admin.feign.domain.entity.SysConfigEntity;
import cn.vpclub.demo.admin.feign.model.request.SysConfigParam;
import cn.vpclub.demo.admin.api.service.SysConfigService;
import cn.vpclub.demo.common.model.core.enums.ReturnCodeEnum;
import cn.vpclub.demo.common.model.core.model.response.BackResponseUtil;
import cn.vpclub.demo.common.model.core.model.response.BaseResponse;
import cn.vpclub.demo.common.model.core.model.response.PageResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@PostMapping("/list")
	@RequiresPermissions("sys:config:list")
	public PageResponse list(@RequestBody SysConfigParam params){
		PageResponse page = sysConfigService.queryPage(params);
		return page;
	}
	
	
	/**
	 * 配置信息
	 */
	@PostMapping("/info")
	@RequiresPermissions("sys:config:info")
	public BaseResponse info(@RequestBody SysConfigParam params){
		SysConfigEntity config = sysConfigService.selectById(params.getId());
		BaseResponse baseResponse=BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
		baseResponse.setDataInfo(config);
		return baseResponse;
	}
	
	/**
	 * 保存配置
	 */
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public BaseResponse save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		config.setCreatedBy(getUserId());
		config.setUpdatedBy(getUserId());
		sysConfigService.save(config);
		return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
	}
	
	/**
	 * 修改配置
	 */
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public BaseResponse update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		config.setUpdatedBy(getUserId());
		sysConfigService.update(config);
		return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
	}
	
	/**
	 * 删除配置
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public BaseResponse delete(@RequestBody SysConfigParam params){
		params.setUserId(getUserId());
		sysConfigService.deleteBatch(params);
		return BackResponseUtil.getBaseResponse(ReturnCodeEnum.CODE_1000.getCode());
	}

}
