package com.dig_i.front.hibernate.type;

import com.dig_i.front.entity.UserAccountType;

public class UserAccountTypeUserType extends
		BitEnumSetUserType<UserAccountType> {
	public UserAccountTypeUserType() {
		super(UserAccountType.class);
	}
}
