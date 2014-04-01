package org.nlpcn.commons.lang.occurrence;

import org.nlpcn.commons.lang.util.MapCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 词语共现计算工具,愚人节快乐
 * Created by ansj on 4/1/14.
 */
public class Occurrence {

	private int seqId = 0;

	private Map<String, Count> word2Mc = new HashMap<String, Count>();

	private MapCount<String> ww2Mc = new MapCount<String>();

	private static final String CONN = "\u0000";

	public void add(List<String> words) {
		Count count = null;
		for (String word : words) {
			if ((count = word2Mc.get(word)) != null) {
				count.upCount();
			} else {
				word2Mc.put(word, new Count());
			}

		}

		String str1 = null;
		String str2 = null;
		Count count1 = null;
		Count count2 = null;
		for (int i = 0; i < words.size() - 1; i++) {
			str1 = words.get(i);
			count1 = word2Mc.get(st1);
			for (int j = i + 1; j < words.size(); j++) {
				str2 = words.get(j);
				count2 = word2Mc.get(st2);
				if (count1.id == count2.id) {
					continue;
				}

				if (count1.id < count2.id) {
					ww2Mc.add(str1 + CONN + str2);
				} else {
					ww2Mc.add(str2 + CONN + str1);
				}
				count1.upRelation();
				count2.upRelation();
			}
		}
	}

	/**
	 * 保存模型
	 */
	public void saveModel(String filePath) {

	}


	/**
	 * 读取模型
	 */
	public void loadModel(String filePath) {

	}

	class Count {
		int id = 0;
		int count = 1; //这个词
		int relationCount; //这个词和其他词共同出现多少次


		public Count() {
			seqId++;
			this.id = seqId;
		}


		public void upCount() {
			this.count++;
		}

		public void upRelation() {
			this.relationCount++;
		}

		@Override
		public String toString() {
			return this.id + "\t" + this.count + "\t" + this.relationCount;
		}
	}
}
