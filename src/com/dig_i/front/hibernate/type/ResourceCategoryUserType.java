package com.dig_i.front.hibernate.type;

import com.dig_i.front.entity.ResourceCategory;

public class ResourceCategoryUserType extends
		BitEnumSetUserType<ResourceCategory> {
	public ResourceCategoryUserType() {
		super(ResourceCategory.class);
	}
}
