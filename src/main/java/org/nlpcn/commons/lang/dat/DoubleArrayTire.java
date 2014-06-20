//package org.nlpcn.commons.lang.dat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.nlpcn.commons.lang.tire.domain.SmartForest;
//import org.nlpcn.commons.lang.util.FileIterator;
//import org.nlpcn.commons.lang.util.IOUtil;
//import org.nlpcn.commons.lang.util.StringUtil;
//
///**
// * dat maker
// * 
// * @author ansj
// * 
// */
//public class DoubleArrayTire {
//
//	private SmartForest<Item> forest = null;
//
//	private Item[] itemArr = new Item[100000];
//
//	/**
//	 * 构建默认的DAT
//	 * 
//	 * @param dicPath
//	 * @throws Exception
//	 */
//	public void maker(String dicPath) throws Exception {
//		maker(dicPath, Item.class);
//
//	}
//
//	/**
//	 * 构建用户自定义的dat
//	 * 
//	 * @param dicPath
//	 * @param Param
//	 * @throws Exception
//	 */
//	public void maker(String dicPath, Class<Item> clz) throws Exception {
//
//		forest = new SmartForest<Item>();
//		FileIterator iteartor = IOUtil.instanceFileIterator(dicPath, IOUtil.UTF8);
//
//		String[] split = null;
//
//		try {
//			String temp = null;
//
//			while (iteartor.hasNext()) {
//				temp = iteartor.next();
//
//				if (StringUtil.isBlank(temp)) {
//					continue;
//				}
//
//				split = temp.split("\t");
//
//				Item item = clz.newInstance().init(split);
//
//				forest.add(split[0], item);
//			}
//		} finally {
//			if (iteartor != null)
//				iteartor.close();
//		}
//
//		makeDAT(tree2List());
//	}
//
//	private void makeDAT(List<Item> all) throws Exception {
//		char[] chars = null;
//		int length = 0;
//		Item tempValueResult;
//		int tempBase = 0;
//		String temp = null;
//		Item item = null;
//		// all 就是tire树中没一个前缀集合
//		for (int i = 0; i < all.size(); i++) {
//			item = all.get(i);
//			// 每个节点中的词.
//			temp = item.name;
//			chars = temp.toCharArray();
//			length = chars.length;
//			// 如果词长度为一.直接放置到ascii码的位置上.并且保证此字的值大于65536
//			if (length == 1) {
//				item.base = 65536;
//				item.check = -1;
//				itemArr[chars[0]] = item;
//			} else {
//				// 得道除了尾字啊外的位置,比如 "中国人" 此时返回的是"中国"的base值
//				int previousCheck = getBaseNum(chars);
//
//				// 前缀是否相同,如果相同保存在临时map中.直到不同
//				if (previous == previousCheck) {
//					tempStringMap.put(temp, branch);
//					continue;
//				}
//				if (tempStringMap.size() > 0) {
//					setBaseValue(tempStringMap, previous);
//					it = tempStringMap.values().iterator();
//
//					// 处理完冲突后将这些值填充到双数组中
//					while (it.hasNext()) {
//						tempValueResult = it.next();
//						chars = tempValueResult.getValue().toCharArray();
//						tempBase = base[previous] + chars[chars.length - 1];
//						base[tempBase] = tempBase;
//						check[tempBase] = previous;
//						status[tempBase] = tempValueResult.getStatus();
//						words[tempBase] = tempValueResult.getValue();
//						natures[tempBase] = tempValueResult.getNatures();
//					}
//				}
//
//				// 将处理冲突的归于初始状态
//				previous = previousCheck;
//				tempStringMap = new HashMap<String, Branch>();
//				tempStringMap.put(temp, branch);
//
//			}
//		}
//
//		// 上面循环有可能.也许会漏掉最后一组冲突所以进行一次补录...^_^!
//		if (tempStringMap.size() > 0) {
//			setBaseValue(tempStringMap, previous);
//			it = tempStringMap.values().iterator();
//			while (it.hasNext()) {
//				tempValueResult = it.next();
//				chars = tempValueResult.getValue().toCharArray();
//				tempBase = base[previous] + chars[chars.length - 1];
//				base[tempBase] = tempBase;
//				check[tempBase] = previous;
//				status[tempBase] = tempValueResult.getStatus();
//				words[tempBase] = tempValueResult.getValue();
//				natures[tempBase] = tempValueResult.getNatures();
//			}
//		}
//
//	}
//
//	/**
//	 * 找到该字符串上一个的位置字符串上一个的位置
//	 * 
//	 * @param chars
//	 *            传入的字符串char数组
//	 * @return
//	 */
//	public int getBaseNum(char[] chars) {
//		int tempBase = 0;
//		if (chars.length == 2) {
//			return chars[0];
//		}
//		for (int i = 0; i < chars.length - 1; i++) {
//			if (i == 0) {
//				tempBase += chars[i];
//			} else {
//				tempBase = itemArr[tempBase].base + chars[i];
//			}
//		}
//		return tempBase;
//	}
//
//	/**
//	 * 将tire树 广度遍历为List
//	 * 
//	 * @return
//	 */
//	private List<Item> tree2List() {
//		List<Item> all = new ArrayList<Item>();
//		treeToLibrary(all, forest);
//		return all;
//	}
//
//	/**
//	 * 广度遍历
//	 * 
//	 * @param all
//	 * @param sf
//	 */
//	private static void treeToLibrary(List<Item> all, SmartForest<Item> sf) {
//		SmartForest<Item>[] branches = sf.branches;
//
//		if (branches == null) {
//			return;
//		}
//
//		for (int j = 0; j < branches.length; j++) {
//			if (branches[j] == null || branches[j].getParam() == null) {
//				continue;
//			}
//			all.add(branches[j].getParam());
//		}
//
//		for (int j = 0; j < branches.length; j++) {
//			if (branches[j] == null) {
//				continue;
//			}
//			treeToLibrary(all, branches[j]);
//		}
//	}
//
//	/**
//	 * 序列化dat对象
//	 */
//	public void save() {
//
//	}
//
//	/**
//	 * 保存到可阅读的文本.需要重写这个类
//	 */
//	public void saveText() {
//
//	}
//
//}
