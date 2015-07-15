package org.nlpcn.commons.lang.dat;

import org.junit.Test;
import static org.nlpcn.commons.lang.TestUtils.*;

public class DATTest {

	@Test
	public void testMakeSaveAndLoad() throws Exception {
		DATMaker dat = new DATMaker();
		dat.maker(mainResources("pinyin.dic"));

        dat.saveText("/tmp/pinyin.dat");
        long start = System.currentTimeMillis();
        DoubleArrayTire load = DoubleArrayTire.loadText("/tmp/pinyin.dat");
        System.out.println(load.getItem("龙麝").toString());
        System.out.println("load obj use time " + (System.currentTimeMillis() - start));

        dat.save("/tmp/pinyin.obj");
        start = System.currentTimeMillis();
        load = DoubleArrayTire.load("/tmp/pinyin.obj");
        System.out.println(load.getItem("龙麝").toString());
        System.out.println("load obj use time " + (System.currentTimeMillis() - start));
	}
}
