package org.nlpcn.commons.lang.dat;

import org.junit.Test;

import java.io.FileNotFoundException;

public class DATMakerTest {

	@Test(expected = FileNotFoundException.class)
	public void test() throws Exception {
		//构建词典列表
		//加载词典路径。一行一个词。后面参数用\t隔开。
		DATMaker datM = new DATMaker();

		//进行词典构造参数所构成的特殊结构可以重写item进行定制
		datM.maker("train_file/library.txt", BasicItem.class);

		//生成的双数组
		//item 每个元素会包含check 和 base 的值
		Item[] dat = datM.getDAT();
		
		//以二进制方式保存
		datM.save("保存路径");
		
		//以文本可读方式保存
		datM.saveText("保存路径");
		
	}
	
	@Test(expected = FileNotFoundException.class)
	public void loadTest() throws Exception {
		DoubleArrayTire load = DoubleArrayTire.load("生成模型的路径"); 
		Item item = load.getItem("中国"); //相当于每一个词语都在数组中又个id。可以通过这个id快速的获取词典
		Item item2 = load.getItem(5); //相当于每一个词语都在数组中又个id。可以通过这个id快速的获取词典
		
//		item2.base
//		item2.check
//		item2.status
	}
}
