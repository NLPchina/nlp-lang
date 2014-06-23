package org.nlpcn.commons.lang.dat;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.nlpcn.commons.lang.util.FileIterator;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;

/**
 * 双数组使用
 * 
 * @author ansj
 * 
 */
public class DoubleArrayTire {

	private Item[] dat = null;

	private DoubleArrayTire() {
	}

	public static DoubleArrayTire load(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		DoubleArrayTire obj = new DoubleArrayTire();
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		try {
			obj.dat = new Item[ois.readInt()];

			int len = ois.readInt();
			Item item = null;
			for (int i = 0; i < len; i++) {
				item = (Item) ois.readObject();
				obj.dat[item.index] = item;
			}
		} finally {
			if (ois != null)
				ois.close();
		}
		return obj;
	}

	/**
	 * 从文本中加载模型
	 * 
	 * @param filePath
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static DoubleArrayTire loadText(String filePath, Class<? extends Item> cla) throws InstantiationException, IllegalAccessException {
		DoubleArrayTire obj = new DoubleArrayTire();
		FileIterator it = IOUtil.instanceFileIterator(filePath, IOUtil.UTF8);
		String temp = it.next();
		obj.dat = new Item[Integer.parseInt(temp)];
		Item item = null;
		while (it.hasNext()) {
			temp = it.next();
			item = cla.newInstance();
			item.initValue(temp.split("\t"));
			obj.dat[item.index] = item;
		}
		return obj;
	}

	/**
	 * 从文本中加载模型
	 * 
	 * @param filePath
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static DoubleArrayTire loadText(String filePath) throws InstantiationException, IllegalAccessException {
		return loadText(filePath, BasicItem.class);
	}

	/**
	 * 获得dat数组
	 * 
	 * @return
	 */
	public Item[] getDAT() {
		return dat;
	}

	/**
	 * 一个词在词典中的id
	 * 
	 * @param str
	 * @return
	 */
	public int getId(String str) {
		if (StringUtil.isBlank(str)) {
			return 0;
		}
		int baseValue = str.charAt(0);
		int checkValue = 0;
		Item item = null;
		for (int i = 1; i < str.length(); i++) {
			checkValue = baseValue;
			item = dat[baseValue];
			baseValue = item.base + str.charAt(i);
			if (baseValue > dat.length - 1)
				return 0;
			if (item.check != -1 && item.check != checkValue) {
				return 0;
			}
		}
		return baseValue;
	}

	/**
	 * 获得一个词语的item
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Item> T getItem(String str) {
		int id = getId(str);
		if (id <= 0) {
			return null;
		}
		return (T) dat[id];
	}
}
