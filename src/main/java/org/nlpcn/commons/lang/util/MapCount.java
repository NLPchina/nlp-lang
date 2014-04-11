package org.nlpcn.commons.lang.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 用map做的计数器.
 *
 * @param <T>
 * @author ansj
 */
public class MapCount<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<T, Double> hm = null;

	public MapCount() {
		hm = new HashMap<T, Double>();
	}
	
	public MapCount(HashMap<T, Double> hm) {
		this.hm = hm;
	}

	public MapCount(int initialCapacity) {
		hm = new HashMap<T, Double>(initialCapacity);
	}

	public static void main(String[] args) {
		System.out.println(Long.MAX_VALUE);
	}

	/**
	 * 增加一个元素
	 *
	 * @param t
	 * @param n
	 */
	public void add(T t, int n) {
		Double value = null;
		if ((value = hm.get(t)) != null) {
			hm.put(t, value + n);
		} else {
			hm.put(t, Double.valueOf(n));
		}
	}

	/**
	 * 计数增加.默认为1
	 *
	 * @param t
	 */
	public void add(T t) {
		this.add(t, 1);
	}

	/**
	 * map的大小
	 *
	 * @return
	 */
	public int size() {
		return hm.size();
	}

	/**
	 * 删除一个元素
	 *
	 * @param t
	 */
	public void remove(T t) {
		hm.remove(t);
	}

	/**
	 * 得道内部的map
	 *
	 * @return
	 */
	public HashMap<T, Double> get() {
		return this.hm;
	}

	/**
	 * 将map序列化为词典格式
	 *
	 * @return
	 */
	public String getDic() {
		Iterator<Entry<T, Double>> iterator = this.hm.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		Entry<T, Double> next = null;
		while (iterator.hasNext()) {
			next = iterator.next();
			sb.append(next.getKey());
			sb.append("\t");
			sb.append(next.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}
}
