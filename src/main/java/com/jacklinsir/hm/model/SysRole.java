package com.jacklinsir.hm.model;

import com.jacklinsir.hm.model.base.BaseEntity;
import lombok.Data;

/**
 * 角色对象
 * @author linSir
 */
@Data
public class SysRole extends BaseEntity<Integer> {
	private static final long serialVersionUID = -6525908145032868837L;

	private String name;
	private String description;

	@Override
	public String toString() {
		return "SysRole{" +
				"name='" + name + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
