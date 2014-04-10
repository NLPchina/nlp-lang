package org.nlpcn.commons.lang.occurrence;

import org.nlpcn.commons.lang.util.MapCount;

import java.io.*;
import java.util.*;

/**
 * 词语共现计算工具,愚人节快乐
 * Created by ansj on 4/1/14.
 */
public class Occurrence implements Serializable {

	public static void main(String[] args) throws Exception {
//		Occurrence occ = new Occurrence();
//		Set<String> all = new HashSet<String>();
//		all.add("ansj");
//		all.add("sun");
//		all.add("cq");
//		occ.addWords(all);
//
//		occ.saveModel("aaa");

		Occurrence occ = loadModel("aaa");
		System.out.println(occ.distance("ansj", "aa"));
	}


	private int seqId = 0;

	private Map<String, Count> word2Mc = new HashMap<String, Count>();

	private Map<Integer, String> idWordMap = new HashMap<Integer, String>();

	private MapCount<String> ww2Mc = new MapCount<String>();

	private static final String CONN = "\u0000";

	public void addWords(Collection<String> words) {
		List<Element> all = new ArrayList<Element>(words.size());
		for (String word : words) {
			all.add(new Element(word));
		}
		add(all);
	}

	public void add(List<Element> words) {
		Count count = null;
		for (Element word : words) {
			if ((count = word2Mc.get(word.getName())) != null) {
				count.upCount();
			} else {
				count = new Count(word.getNature(), seqId++);
				word2Mc.put(word.getName(), count);
				idWordMap.put(count.id, word.getName());
			}

		}

		Element e1 = null;
		Element e2 = null;
		Count count1 = null;
		Count count2 = null;
		for (int i = 0; i < words.size() - 1; i++) {
			e1 = words.get(i);
			count1 = word2Mc.get(e1.getName());
			for (int j = i + 1; j < words.size(); j++) {
				e2 = words.get(j);
				count2 = word2Mc.get(e2.getName());
				if (count1.id == count2.id) {
					continue;
				}

				if (count1.id < count2.id) {
					ww2Mc.add(e1.getName() + CONN + e2.getName());
				} else {
					ww2Mc.add(e2.getName() + CONN + e2.getName());
				}
				count1.upRelation(count2.id);
				count2.upRelation(count1.id);
			}
		}
	}


	/**
	 * 得到两个词的距离
	 *
	 * @return
	 */
	private Integer distance(String word1, String word2) {
		Integer distance = null;
		if ((distance = ww2Mc.get().get(word1 + CONN + word2)) != null) {
			return distance;
		}

		if ((distance = ww2Mc.get().get(word2 + CONN + word1)) != null) {
			return distance;
		}
		return 0;
	}

	/**
	 * 保存模型
	 */
	public void saveModel(String filePath) throws IOException {
		ObjectOutput oot = new ObjectOutputStream(new FileOutputStream(filePath));
		oot.writeObject(this);
	}


	/**
	 * 读取模型
	 */
	public static Occurrence loadModel(String filePath) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
		return (Occurrence) ois.readObject();

	}

}
