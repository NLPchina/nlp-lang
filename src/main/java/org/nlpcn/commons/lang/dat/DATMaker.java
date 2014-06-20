package org.nlpcn.commons.lang.dat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.FileIterator;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;

/**
 * dat maker
 * 
 * @author ansj
 * 
 */
public class DATMaker {

	private static final Logger LOG = Logger.getLogger(DATMaker.class.getName());

	private SmartForest<Item> forest = null;

	// 动态数组扩容
	private int capacity = 100000;

	private Item[] dat = new Item[100000];

	private Class<? extends Item> cla = null;

	/**
	 * 构建默认的DAT
	 * 
	 * @param dicPath
	 * @throws Exception
	 */
	public void maker(String dicPath) throws Exception {
		maker(dicPath, BasicItem.class);

	}

	/**
	 * 构建用户自定义的dat
	 * 
	 * @param dicPath
	 * @param Param
	 * @throws Exception
	 */
	public void maker(String dicPath, Class<? extends Item> cla) throws Exception {

		this.cla = cla;

		long start = System.currentTimeMillis();

		LOG.info("make basic tire begin !");

		forest = new SmartForest<Item>();

		FileIterator iteartor = IOUtil.instanceFileIterator(dicPath, IOUtil.UTF8);

		String[] split = null;

		try {
			String temp = null;

			while (iteartor.hasNext()) {
				temp = iteartor.next();

				if (StringUtil.isBlank(temp)) {
					continue;
				}

				split = temp.split("\t");

				Item item = cla.newInstance();

				item.init(split);

				forest.add(split[0], item);
			}
		} finally {
			if (iteartor != null)
				iteartor.close();
		}

		LOG.info("make basic tire over use time " + (System.currentTimeMillis() - start) + " ms");

		start = System.currentTimeMillis();
		LOG.info("make dat tire begin !");
		makeDAT(tree2List());
		LOG.info("make dat tire over use time " + (System.currentTimeMillis() - start) + " ms! dat len is " + datArrLen() + "! dat size is " + datItemSize());

	}

	private void makeDAT(List<Item> all) throws Exception {
		char[] chars = null;
		int length = 0;
		Item item = null;

		// 用来保留默认前缀
		Item pre = null;
		List<Item> group = new ArrayList<Item>();

		// all 就是tire树中没一个前缀集合
		for (int i = 0; i < all.size(); i++) {
			item = all.get(i);
			// 每个节点中的词.
			chars = item.name.toCharArray();
			length = chars.length;
			// 如果词长度为一.直接放置到ascii码的位置上.并且保证此字的值大于65536
			if (length == 1) {
				item.check = -1;
				item.index = chars[0];
				dat[item.index] = item;
			} else {
				// 得道除了尾字啊外的位置,比如 "中国人" 此时返回的是"中国"的Item
				// 前缀是否相同,如果相同保存在临时map中.直到不同
				pre = getPre(item);
				group = findGroup(all, i, pre);
				item2DAT(pre, group);
				i = i + group.size() - 1;
			}
		}
	}

	/**
	 * 找到相同的组
	 * 
	 * @param all
	 * @param i
	 * @param pre
	 * @return
	 */
	private List<Item> findGroup(List<Item> all, int i, Item pre) {
		List<Item> group = new ArrayList<Item>();
		group.add(all.get(i));
		Item temp = null;
		for (int j = i + 1; j < all.size(); j++) {
			temp = all.get(j);
			if (pre == getPre(temp)) {
				group.add(temp);
			} else {
				break;
			}
		}
		return group;
	}

	/**
	 * 将迭代结果存入dat
	 * 
	 * @param pre
	 * @param samePreGroup
	 */
	private void item2DAT(Item pre, List<Item> samePreGroup) {
		updateBaseValue(samePreGroup, pre);
		// 处理完冲突后将这些值填充到双数组中
		for (Item itemTemp : samePreGroup) {
			itemTemp.index = pre.base + getLastChar(itemTemp.name);
			dat[pre.base + getLastChar(itemTemp.name)] = itemTemp;
			itemTemp.check = pre.index;
		}
	}

	/**
	 * 更新上一级的base值，直到冲突解决
	 * 
	 * @param samePreGroup
	 * @param pre
	 */
	private void updateBaseValue(List<Item> samePreGroup, Item pre) {
		Iterator<Item> iterator = samePreGroup.iterator();
		Item item = null;
		while (iterator.hasNext()) {
			item = iterator.next();

			checkLength(pre.base + getLastChar(item.name));

			if (dat[pre.base + getLastChar(item.name)] != null) {
				pre.base++;
				iterator = samePreGroup.iterator();
			}
		}
	}

	/**
	 * 检查数组长度并且扩容
	 * 
	 * @param i
	 */
	private void checkLength(int len) {
		if (len >= dat.length) {
			dat = Arrays.copyOf(dat, len + capacity);
		}
	}

	/**
	 * 获得一个词语的最后一个字符
	 * 
	 * @param word
	 * @return
	 */
	public char getLastChar(String word) {
		return word.charAt(word.length() - 1);
	}

	/**
	 * 找到该字符串上一个的位置字符串上一个的位置
	 * 
	 * @param chars
	 *            传入的字符串char数组
	 * @return
	 */
	public Item getPre(Item item) {
		char[] chars = item.name.toCharArray();
		int tempBase = 0;
		if (chars.length == 2) {
			return dat[chars[0]];
		}
		for (int i = 0; i < chars.length - 1; i++) {
			if (i == 0) {
				tempBase += chars[i];
			} else {
				tempBase = dat[tempBase].base + chars[i];
			}
		}
		return dat[tempBase];
	}

	/**
	 * 将tire树 广度遍历为List
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private List<Item> tree2List() throws InstantiationException, IllegalAccessException {
		List<Item> all = new ArrayList<Item>();
		treeToLibrary(all, forest, "");
		return all;
	}

	/**
	 * 广度遍历
	 * 
	 * @param all
	 * @param sf
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void treeToLibrary(List<Item> all, SmartForest<Item> sf, String preStr) throws InstantiationException, IllegalAccessException {
		SmartForest<Item>[] branches = sf.branches;

		if (branches == null) {
			return;
		}

		for (int j = 0; j < branches.length; j++) {
			if (branches[j] == null) {
				continue;
			}
			// 将branch的状态赋予
			Item param = branches[j].getParam();
			if (param == null) {
				param = cla.newInstance();
				param.name = preStr + (branches[j].getC());
				param.status = 1;
			} else {
				param.status = branches[j].getStatus();
			}
			all.add(param);
		}

		for (int j = 0; j < branches.length; j++) {
			if (branches[j] == null) {
				continue;
			}
			treeToLibrary(all, branches[j], preStr + (branches[j].getC()));
		}

	}

	/**
	 * 序列化dat对象
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void save(String path) throws FileNotFoundException, IOException {
		ObjectOutput writer = new ObjectOutputStream(new FileOutputStream(path));
		try {
			writer.writeInt(datArrLen());
			writer.writeInt(datItemSize());
			for (Item item : dat) {
				if (item == null) {
					continue;
				}
				writer.writeObject(item);
			}
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * 保存到可阅读的文本.需要重写这个类
	 * 
	 * @throws IOException
	 */
	public void saveText(String path) throws IOException {
		Writer writer = new FileWriter(new File(path));
		try {
			writer.write(String.valueOf(datArrLen()));
			writer.write('\n');
			for (Item item : dat) {
				if (item == null) {
					continue;
				}
				writer.write(item.toString());
				writer.write('\n');
			}
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * 取得数组的真实长度
	 * 
	 * @return
	 */
	private int datArrLen() {
		for (int i = dat.length - 1; i >= 0; i--) {
			if (dat[i] != null) {
				return i + 1;
			}
		}
		return 0;
	}

	/**
	 * 取得数组的真实长度
	 * 
	 * @return
	 */
	private int datItemSize() {
		int size = 0;
		for (int i = dat.length - 1; i >= 0; i--) {
			if (dat[i] != null) {
				size++;
			}
		}
		return size;
	}

	/**
	 * 获得dat数组
	 * 
	 * @return
	 */
	public Item[] getDAT() {
		return dat;
	}

}
