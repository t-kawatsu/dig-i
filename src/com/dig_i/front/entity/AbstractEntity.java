package com.dig_i.front.entity;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract Integer getId();

	public abstract void setId(Integer id);

	public boolean equals(AbstractEntity other) {
		if (this == other)
			return true;

		if (getId() != other.getId()) {
			return false;
		}

		if (getId() == null) {
			return other.getId() == null;
		} else {
			return getId().equals(other.getId());
		}
	}

	public int hashCode() {
		return getId() == null ? 0 : getId().hashCode();
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this).toString();
	}
}
