package org.nlpcn.commons.lang.dat;

/**
 * 如果你的dat需要.有参数需要继承并且重写这个类的构造方法
 * 
 * @author ansj
 * 
 */
public class Item {
	public String name;
	public int base;
	public int check;
	public byte status;

	public Item init(String[] split) {
		name = split[0];
		return this;
	}
}
