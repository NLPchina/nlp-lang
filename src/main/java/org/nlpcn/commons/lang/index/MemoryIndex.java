package org.nlpcn.commons.lang.index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.nlpcn.commons.lang.pinyin.Pinyin;
import org.nlpcn.commons.lang.pinyin.PinyinWord;
import org.nlpcn.commons.lang.util.StringUtil;

public class MemoryIndex<T> {

	private Map<String, TreeSet<Entry>> index = new HashMap<String, TreeSet<Entry>>();

	private int size = 10;

	private Model model = Model.ALL;

	public static enum Model {
		ALL, PREX
	};

	/**
	 * 倒排hash配置
	 * 
	 * @param size
	 *            返回的条数
	 * @param model
	 *            前缀 或者全索引
	 */
	public MemoryIndex(int size, Model model) {
		this.size = size;
		this.model = model;
	}

	/**
	 * 倒排hash配置
	 * 
	 * @param size
	 *            返回的条数
	 * @param model
	 *            前缀 或者全索引
	 */
	public MemoryIndex() {

	}

	public void addItem(T value, Double score, String... fields) {
		Set<String> result = null;

		if (fields == null || fields.length == 0) {
			fields = new String[] { value.toString() };
		}

		switch (model) {
		case ALL:
			result = getAllSplit(fields);
			break;
		case PREX:
			result = getPrexSplit(fields);
			break;
		}

		TreeSet<Entry> treeSet = null;
		for (String key : result) {
			if (StringUtil.isBlank(key)) {
				continue;
			}
			treeSet = index.get(key);

			if (treeSet == null) {
				treeSet = new TreeSet<Entry>();
				index.put(key, treeSet);
			}
			treeSet.add(new Entry(value, score(value, score)));

			if (treeSet.size() > this.size) {
				treeSet.pollLast();
			}
		}
	}

	public void addItem(T value, String... fields) {
		addItem(value, null, fields);
	}

	private Set<String> getAllSplit(String[] fields) {
		HashSet<String> hs = new HashSet<String>();
		for (String string : fields) {
			if (StringUtil.isBlank(string)) {
				continue;
			}
			string = string.trim();
			for (int i = 0; i < string.length(); i++) {
				for (int j = i + 1; j < string.length() + 1; j++) {
					hs.add(string.substring(i, j));
				}
			}
		}
		return hs;
	}

	private Set<String> getPrexSplit(String[] fields) {
		HashSet<String> hs = new HashSet<String>();
		for (String string : fields) {
			if (StringUtil.isBlank(string)) {
				continue;
			}

			string = string.trim();

			for (int i = 1; i < string.length() + 1; i++) {
				hs.add(string.substring(0, i));
			}
		}

		return hs;
	}

	public double score(T value, Double score) {
		if (score != null) {
			return score;
		}
		double weight = 0;
		if (value instanceof String) {
			weight = Math.log(Math.E / (double) value.toString().length());
		}
		return weight;
	}

	public class Entry implements Comparable<Entry> {
		private double score;
		private T t;

		public Entry(T t, Double score) {
			this.t = t;
			this.score = score;
		}

		@Override
		public int compareTo(Entry o) {
			if (this.t.equals(o.t)) {
				return 0;
			}

			if (this.score > o.score) {
				return -1;
			} else {
				return 1;
			}
		}

		@Override
		public boolean equals(Object o) {
			if ((o instanceof MemoryIndex.Entry)) {
				@SuppressWarnings("all")
				Entry e = (MemoryIndex.Entry) o;
				return e.t.equals(this.t);
			}
			return false;
		}

		public double getScore() {
			return score;
		}

		public T getValue() {
			return t;
		}
		
		@Override
		public String toString(){
			return t.toString() ;
		}

	}
	
	/**
	 * 将字符串转换为全拼
	 * @param str
	 * @return
	 */
	public String str2QP(String str){
		List<PinyinWord> list = Pinyin.str2Pinyin(str);
		StringBuilder sb = new StringBuilder();
		for (PinyinWord pinyinWord : list) {
			sb.append(pinyinWord.py);
		}
		return sb.toString() ;
	}
	

	public TreeSet<Entry> suggest(String key) {
		return index.get(key);
	}

}
