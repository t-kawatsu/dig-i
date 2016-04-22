package com.dig_i.front.hibernate.type;

import com.dig_i.front.entity.AdminUserRole;

public class AdminUserRoleType extends BitEnumSetUserType<AdminUserRole> {
	public AdminUserRoleType() {
		super(AdminUserRole.class);
	}
}
