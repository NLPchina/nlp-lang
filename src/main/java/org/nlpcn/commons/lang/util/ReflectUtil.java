package org.nlpcn.commons.lang.util;

public class ReflectUtil {
	/**
	 * 将一个对象转换为对应的类
	 *
	 * @param <T>
	 * @param value
	 * @param c
	 * @return
	 */
	public static Object conversion(String value, Class<?> c) {
		if (String.class.equals(c)) {
			return value;
		} else if (Integer.class.equals(c)) {
			return Integer.parseInt(value);
		} else if (Double.class.equals(c)) {
			return Double.parseDouble(value);
		} else if (Float.class.equals(c)) {
			return Float.parseFloat(value);
		} else if (Long.class.equals(c)) {
			return Long.parseLong(value);
		} else {
			return value;
		}
	}
}
