package org.nlpcn.commons.lang.occurrence;

import org.nlpcn.commons.lang.util.MapCount;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 词语共现计算工具,愚人节快乐
 * Created by ansj on 4/1/14.
 */
public class Occurrence {

	private int seqId = 0;

	private Map<String, Count> word2Mc = new HashMap<String, Count>();

	private Map<Integer, String> idWordMap = new HashMap<Integer, String>();

	private MapCount<String> ww2Mc = new MapCount<String>();

	private static final String CONN = "\u0000";

	public void add(List<Element> words) {
		Count count = null;
		for (Element word : words) {
			if ((count = word2Mc.get(word.getName())) != null) {
				count.upCount();
			} else {
				count = new Count(word.getNature());
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
	 * 保存模型
	 */
	public void saveModel(String filePath) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath))) ;
		writeMap(bw,idWordMap) ;
		writeMap(bw,word2Mc) ;
		writeMap(bw,ww2Mc.get()) ;

	}

	private void writeMap(BufferedWriter bw, Map<?, ?> map) throws IOException {
		bw.write(word2Mc.size());
		bw.newLine();
		for(Map.Entry entry : word2Mc.entrySet()){
			bw.write(entry.getKey()+"\t"+entry.getValue());
			bw.newLine();
		}
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
		String nature;

		Set<Integer> relationSet = new HashSet<Integer>();

		public Count(String nature) {
			this.nature = nature;
			seqId++;
			this.id = seqId;
		}


		public void upCount() {
			this.count++;
		}

		public void upRelation(int rId) {
			this.relationCount++;
			this.relationSet.add(rId);
		}


		@Override
		public String toString() {
			return this.id + "\t" + this.count + "\t" + this.relationCount;
		}
	}
}
