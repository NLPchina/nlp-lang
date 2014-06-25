package org.nlpcn.commons.lang.dat;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

	public int arrayLength;

	private DoubleArrayTire() {
	}

	public static DoubleArrayTire load(String filePath) throws FileNotFoundException, IOException, ClassNotFoundException {
		DoubleArrayTire obj = new DoubleArrayTire();
		ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		try {
			obj.dat = new Item[ois.readInt()];

			obj.arrayLength = ois.readInt();
			Item item = null;
			for (int i = 0; i < obj.arrayLength; i++) {
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
		return loadText(IOUtil.getInputStream(filePath), cla);
	}

	/**
	 * 从文本中加载模型
	 * 
	 * @param filePath
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static DoubleArrayTire loadText(InputStream is, Class<? extends Item> cla) throws InstantiationException, IllegalAccessException {
		DoubleArrayTire obj = new DoubleArrayTire();
		FileIterator it = IOUtil.instanceFileIterator(is, IOUtil.UTF8);
		String temp = it.next();
		obj.arrayLength = Integer.parseInt(temp);
		obj.dat = new Item[obj.arrayLength];
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
		Item item = getItem(str) ;
		if(item==null){
			return 0 ;
		}else{
			return item.index ;
		}
	}

	/**
	 * 获得一个词语的item
	 * 
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Item> T getItem(String str) {
		if (StringUtil.isBlank(str)) {
			return null;
		}
		if (str.length() == 1) {
			return (T) dat[str.charAt(0)];
		}

		int checkValue = 0;
		Item item = dat[str.charAt(0)];
		if(item==null){
			return null ;
		}
		for (int i = 1; i < str.length(); i++) {
			checkValue = item.index;
			if (item.base + str.charAt(i) > dat.length - 1)
				return null;
			
			item = dat[item.base + str.charAt(i)];
			if (item == null) {
				return null;
			}
			if (item.check != -1 && item.check != checkValue) {
				return null;
			}
		}
		return (T) item;
	}

	@SuppressWarnings("unchecked")
	public <T extends Item> T getItem(int id) {
		return (T) dat[id];
	}

}
