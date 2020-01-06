package com.jacklinsir.hm.service;


import com.jacklinsir.hm.dto.BeanField;
import com.jacklinsir.hm.dto.GenerateInput;

import java.util.List;

/**
 * @author linSir
 */
public interface SysGenerateService {

	/**
	 * 获取数据库表信息
	 * 
	 * @param tableName
	 * @return
	 */
	List<BeanField> listBeanField(String tableName);

	/**
	 * 转成驼峰并大写第一个字母
	 * 
	 * @param string
	 * @return
	 */
	String upperFirstChar(String string);

	/**
	 * 生成代码
	 * 
	 * @param input
	 */
	void saveCode(GenerateInput input);
}
