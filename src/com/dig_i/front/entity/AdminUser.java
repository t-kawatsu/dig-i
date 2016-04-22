package com.dig_i.front.entity;

import java.util.EnumSet;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Cacheable(true)
@Entity
@Table(name = "admin_users")
public class AdminUser extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String password;
	private EnumSet<AdminUserRole> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Type(type = "com.dig_i.front.hibernate.type.AdminUserRoleType")
	public EnumSet<AdminUserRole> getRoles() {
		return roles;
	}

	public void setRoles(EnumSet<AdminUserRole> roles) {
		this.roles = roles;
	}

}
