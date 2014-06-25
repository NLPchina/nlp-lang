package org.nlpcn.commons.lang.dat;

import java.io.Serializable;

/**
 * 如果你的dat需要.有参数需要继承并且重写这个类的构造方法
 * 
 * @author ansj
 * 
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int index;
	public String name;
	public int base = 65536;
	public int check;
	public byte status;

	/**
	 * 从词典中加载如果又特殊需求可重写此构造方法
	 * 
	 * @param split
	 * @return
	 */
	public abstract void init(String[] split);

	/**
	 * 从生成的词典中加载。应该和toText方法对应
	 * 
	 * @param split
	 * @return
	 */

	public abstract void initValue(String[] split);

	/**
	 * 以文本格式序列化的显示
	 * 
	 * @return
	 */
	public abstract String toText();

	@Override
	public String toString() {
		return toText();
	}
}
