package com.jacklinsir.hm.controller;

import com.jacklinsir.hm.dto.BeanField;
import com.jacklinsir.hm.dto.GenerateDetail;
import com.jacklinsir.hm.dto.GenerateInput;
import com.jacklinsir.hm.service.SysGenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 代码生成接口
 *
 * @author linSir
 */
@Api(tags = "代码生成")
@RestController
@RequestMapping("/generate")
public class SysGenerateController {

	@Autowired
	private SysGenerateService generateService;

	@ApiOperation("根据表名显示表信息")
	@GetMapping(params = { "tableName" })
	public GenerateDetail generateByTableName(String tableName) {
		GenerateDetail detail = new GenerateDetail();
		detail.setBeanName(generateService.upperFirstChar(tableName));
		List<BeanField> fields = generateService.listBeanField(tableName);
		detail.setFields(fields);

		return detail;
	}

	@ApiOperation("生成代码")
	@PostMapping(value = "/save")
	@ResponseBody
	public void save(@RequestBody GenerateInput input) {
		generateService.saveCode(input);
	}

}
