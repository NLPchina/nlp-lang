package org.nlpcn.commons.lang.dic;

import java.io.BufferedReader;
import java.util.HashMap;

import org.nlpcn.commons.lang.trie.domain.Forest;
import org.nlpcn.commons.lang.trie.library.Library;

/**
 * Created by ansj on 4/1/14.
 */
public class DicManager {

	private static final HashMap<String, Forest> forestMap = new HashMap<>();

	/**
	 * 构建一个trie书辞典
	 * 
	 * @param dicName
	 * @param dicName
	 * @return
	 * @throws Exception
	 */
	public synchronized static Forest makeForest(String dicName, BufferedReader br) throws Exception {
		Forest forest = null;
		if ((forest = forestMap.get(dicName)) != null) {
			return forest;
		}
		forest = Library.makeForest(br);

		if (dicName != null) {
			forestMap.put(dicName, forest);
		}

		return forest;
	}

	/**
	 * 从内存中移除
	 * 
	 * @param dicName
	 * @return
	 */
	public static Forest remove(String dicName) {
		return forestMap.remove(dicName);
	}

	/**
	 * 获得一本辞典
	 * 
	 * @param dicName
	 * @return
	 */
	public static Forest getForest(String dicName) {
		return forestMap.get(dicName);
	}
}
