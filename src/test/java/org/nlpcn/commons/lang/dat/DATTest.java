package org.nlpcn.commons.lang.dat;

import org.junit.Test;

public class DATTest {

	@Test
	public void makerTest() throws Exception {
		DATMaker dat = new DATMaker();

		dat.maker("/home/ansj/公共的/pinyin.dic");

		dat.saveText("/home/ansj/公共的/pinyin.dat");

		dat.save("/home/ansj/公共的/pinyin.obj");
	}

	@Test
	public void loadTest() throws Exception {
		long start = System.currentTimeMillis();
		DoubleArrayTire load = DoubleArrayTire.load("/home/ansj/公共的/pinyin.obj");
		System.out.println(load.getItem("龙麝"));
		System.out.println("load obj use time " + (System.currentTimeMillis() - start));
	}

	@Test
	public void loadTextTest() throws Exception {
		long start = System.currentTimeMillis();
		DoubleArrayTire load = DoubleArrayTire.loadText("/home/ansj/公共的/pinyin.dat");
		System.out.println(load.getItem("龙麝"));
		System.out.println("load obj use time " + (System.currentTimeMillis() - start));
	}

}
