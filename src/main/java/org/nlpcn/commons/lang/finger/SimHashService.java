package org.nlpcn.commons.lang.finger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.nlpcn.commons.lang.tire.GetWord;

public class SimHashService extends AbsService {

	private static final int BYTE_LEN = 32;

	private static final int[] BITS = new int[32];

	static {
		for (int i = 0; i < BITS.length - 1; i++) {
			StringBuilder sb = new StringBuilder("1");

			for (int j = 0; j < i; j++) {
				sb.append("0");
			}

			BITS[i] = Integer.parseInt(sb.toString(), 2);
		}

		BITS[31] = Integer.MIN_VALUE;
	}

	/**
	 * 比较 ab 的汉明距离
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public int hmDistance(int a, int b) {
		a = a ^ b;
		b = 0;
		for (int i = 0; i < BYTE_LEN; i++) {
			if ((a & BITS[i]) != 0) {
				b++;
			}
		}
		return b;
	}

	/**
	 * 传入两个文章进行汉明距离比较
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	public int hmDistance(String c1, String c2) {
		return hmDistance(fingerprint(c1), fingerprint(c2));
	}

	/**
	 * 获得simhash的指纹
	 * 
	 * @param content
	 * @return
	 */
	public int fingerprint(String content) {
		int[] values = new int[BYTE_LEN];

		for (String word : analysis(content)) {
			int hashCode = hashCode(word);
			for (int i = 0; i < BYTE_LEN; i++) {
				if ((hashCode & BITS[i]) != 0) {
					values[31 - i]++;
				} else {
					values[31 - i]--;
				}
			}
		}

		int result = 0;

		for (int i = 0; i < BYTE_LEN; i++) {
			if (values[i] > 0) {
				result = result | BITS[31 - i];
			}
		}

		return result;
	}

	/**
	 * 调用分词器，如果你想用自己的分词器。需要覆盖这个方法
	 * 
	 * @return
	 */
	public List<String> analysis(String content) {

		GetWord word = forest.getWord(content);

		String temp = null;

		List<String> all = new ArrayList<String>();

		while ((temp = word.getFrontWords()) != null) {
			all.add(temp);
		}

		return all;
	}

	/**
	 * hash 方法生成hashcode ， 默认采用java string的hashcode，如果需要则覆盖这个方法
	 * 
	 * @param word
	 * @return
	 */
	public int hashCode(String word) {
		return word.hashCode();
	}

}
