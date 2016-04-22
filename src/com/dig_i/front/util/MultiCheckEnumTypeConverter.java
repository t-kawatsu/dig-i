package com.dig_i.front.util;

import java.util.EnumSet;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.conversion.TypeConversionException;

public class MultiCheckEnumTypeConverter<E extends Enum<E>> extends
		StrutsTypeConverter {

	private Class<E> enumClass;

	protected MultiCheckEnumTypeConverter(Class<E> c) {
		enumClass = c;
	}

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null || toClass != EnumSet.class) {
			return null;
		}
		try {
			EnumSet<E> eSet = EnumSet.noneOf(enumClass);
			for (String v : values) {
				eSet.add(Enum.valueOf(enumClass, v));
			}
			return eSet;
		} catch (Exception e) {
			throw new TypeConversionException(e.getMessage());
		}
	}

	@Override
	public String convertToString(Map context, Object o) {
		return null;
		/*
		 * try { return StringUtils.join((EnumSet<E>) o, ","); } catch
		 * (Exception e) { throw new TypeConversionException(e.getMessage()); }
		 */
	}

}
