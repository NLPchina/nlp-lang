package org.nlpcn.commons.lang.pinyin;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PinyinTest {

	String str = "京东商城向您保证所售商品均为正品行货，京东自营商品开具机打发票或电子发票。凭质保证书及京东商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由京东联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。京东商城还为您提供具有竞争力的商品价格和运费政策，请您放心购买";

	// String str = "點下面繁體字按鈕進行在線轉換" ;

	@Test
	public void testStr2Pinyin() {
		List<PinyinWord> parseStr = Pinyin.str2Pinyin(str);
		System.out.println(parseStr);
		Assert.assertEquals(parseStr.size(), str.length());
	}

	/**
	 * 拼音返回
	 * 
	 * @param str
	 * @return ['zhong3','guo4']
	 */
	@Test
	public void testPinyinStr() {
		List<String> result = Pinyin.pinyinStr(str);
		System.out.println(result);
		Assert.assertEquals(result.size(), str.length());

	}

	/**
	 * 取得每个字的拼音,不要声调
	 * 
	 * @return
	 */
	@Test
	public void testPinyinWithoutTone() {
		List<String> result = Pinyin.pinyinWithoutTone(str);
		System.out.println(result);
		Assert.assertEquals(result.size(), str.length());
	}

	/**
	 * 取得每个字的首字符
	 * 
	 * @param str
	 * @return
	 */
	@Test
	public void testStr2FirstCharArr() {
		char[] result = Pinyin.str2FirstCharArr(str);
		System.out.println(Arrays.toString(result));
		Assert.assertEquals(result.length, str.length());
	}
	
	@Test
	public void testInsertPinyin(){
		String str = "正品行货!";
		
		List<String> result1 = (Pinyin.pinyinStr(str));
		Pinyin.insertPinyin("行货", new String[]{"hang2","huo4"});
		List<String> result2 = (Pinyin.pinyinStr(str));
		System.out.println(result1);
		System.out.println(result2);
		Assert.assertNotSame(result1.get(2), result2.get(2));
		
	}

//	/**
//	 * 生成词典只包含多音字
//	 * @param args
//	 * @throws IOException
//	 */
//	public static void main(String[] args) throws IOException {
//		BufferedReader br = IOUtil.getReader("/home/ansj/workspace/pinyin4j/src/main/resources/pinyindb/unicode_to_hanyu_pinyin.txt", "utf-8");
//
//		String temp = null;
//
//		char[] chars = new char[65536];
//		while ((temp = br.readLine()) != null) {
//			String[] split = temp.split(" ");
//			if (split[1].indexOf(',') > -1) {
//				
//				chars[Integer.parseInt(split[0], 16)] = 1; // 多音字
//			} else {
//				chars[Integer.parseInt(split[0], 16)] = 2; // 单音字
//			}
//		}
//
//		StringBuilder sb = new StringBuilder();
//		br = IOUtil.getReader("/home/ansj/workspace/nlp-lang/src/main/java/org/nlpcn/commons/lang/dic/old_pinyin.dic", "utf-8");
//		int ii = 0 ;
//		while ((temp = br.readLine()) != null) {
//			temp = temp.trim();
//			String[] split = temp.split("\t");
//			if(split[0].length()==1){
//				sb.append(temp);
//				sb.append("\n");
//				continue ;
//			}
//			for (int i = 0; i < split[0].length(); i++) {
//				if (chars[split[0].charAt(i)] == 1 || chars[split[0].charAt(i)] == 0) {
//					sb.append(temp);
//					sb.append("\n");
//					break;
//				}
//			}
//		}
//		
//		IOUtil.Writer("/home/ansj/workspace/nlp-lang/src/main/java/org/nlpcn/commons/lang/dic/pinyin.dic", IOUtil.UTF8, sb.toString());
//	}
}
