package org.nlpcn.commons.lang.dic;

import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.IOUtil;

import java.io.*;
import java.util.HashMap;

/**
 * Created by ansj on 4/1/14.
 */
public class DicManager {

	private static final HashMap<String, Forest> forestMap = new HashMap<String, Forest>();

	/**
	 * 违禁词辞典
	 */
	private static Forest f2jForest = null;

	private static Forest j2fForest = null;

	private static SmartForest<String[]> pinyinForest = null;
	
	

	private static SmartForest<String[]> initPinyin() {
		BufferedReader reader = null;
		try {
			reader = IOUtil.getReader(DicManager.class.getResourceAsStream("/pinyin.dic"), IOUtil.UTF8);
			SmartForest<String[]> forest = new SmartForest<String[]>();
			String temp = null;
			String[] strs = null;
			while ((temp = reader.readLine()) != null) {
				strs = temp.split("\t");
				if (strs.length != 2) {
					throw new RuntimeException("error arg by init pinyin \t" + strs.length);
				}
				forest.add(strs[0], strs[1].split(" "));
			}
			return forest;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static Forest initRev(String dicName, InputStream is) {
		BufferedReader reader = null;
		try {
			reader = IOUtil.getReader(is, IOUtil.UTF8);
			Forest forest = new Forest();
			String temp = null;
			String[] strs = null;
			while ((temp = reader.readLine()) != null) {
				strs = temp.trim().split("\t");
				if (strs.length != 2) {
					throw new RuntimeException("error arg by init " + dicName + "\t" + strs.length);
				}
				Library.insertWord(forest, new Value(strs[1], strs[0]));
			}
			return forest;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private static Forest init(String dicName, InputStream is) {
		return init(dicName, new BufferedReader(new InputStreamReader(is)));
	}

	private static Forest init(String dicName, BufferedReader br) {
		try {
			return makeForest(dicName, br);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 构建一个tire书辞典
	 * 
	 * @param dicName
	 * @param filePath
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

	/**
	 * 得到繁体转简体词典
	 * 
	 * @return
	 */
	public static Forest getF2jForest() {
		if (f2jForest == null) {
			f2jForest = init(null, DicManager.class.getResourceAsStream("/fan2jian.dic"));
		}
		return f2jForest;
	}

	/**
	 * 得到简体转繁体词典
	 * 
	 * @return
	 */
	public static Forest getJ2fForest() {
		if(j2fForest==null){
			j2fForest = initRev(null, DicManager.class.getResourceAsStream("/fan2jian.dic")) ;
		}
		return j2fForest;
	}
	
	/**
	 * 得到拼音词典
	 * @return
	 */
	public static SmartForest<String[]> getPinyinForest(){
		if(pinyinForest==null){
			pinyinForest = initPinyin() ;
		}
		return pinyinForest ;
	}

}
