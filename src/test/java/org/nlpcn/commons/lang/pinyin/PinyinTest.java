package org.nlpcn.commons.lang.pinyin;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PinyinTest {

	String str = "码完代码，他起身关上电脑，用滚烫的开水为自己泡制一碗腾着热气的老坛酸菜面。中国的程序员更偏爱拉上窗帘，在黑暗中享受这独特的美食。这是现代工业给一天辛苦劳作的人最好的馈赠。南方一带生长的程序员虽然在京城多年，但仍口味清淡，他们往往不加料包，由脸颊自然淌下的热泪补充恰当的盐分。他们相信，用这种方式，能够抹平思考着现在是不是过去想要的未来而带来的大部分忧伤…小李的父亲在年轻的时候也是从爷爷手里接收了祖传的代码，不过令人惊讶的是，到了小李这一代，很多东西都遗失了，但是程序员苦逼的味道保存的是如此的完整。 就在24小时之前，最新的需求从PM处传来，为了得到这份自然的馈赠，码农们开机、写码、调试、重构，四季轮回的等待换来这难得的丰收时刻。码农知道，需求的保鲜期只有短短的两天，码农们要以最快的速度对代码进行精致的加工，任何一个需求都可能在24小时之后失去原本的活力，变成一文不值的垃圾创意。";

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
