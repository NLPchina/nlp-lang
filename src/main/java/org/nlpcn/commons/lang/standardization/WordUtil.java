package org.nlpcn.commons.lang.standardization;

/**
 * 句子标准化
 * 英文。数字全角转半角。。
 * 所有数字，EN合并
 * @author ansj
 */

public class WordUtil {
	
	/**
	 * 用这个值替换所有数字，如果为null就不替换
	 */
	private String num2Value ;
	
	/*
	 * 用这个值替换所有英文，如果为null就不替换
	 */
	private String en2Value ;
	
	/**
	 * 如果不想被替换清保留null
	 * @param num2Value
	 * @param enValue
	 */
	public WordUtil(String num2Value , String enValue){
		this.num2Value = num2Value ;
		this.en2Value = enValue ;
	}
	
	
}
