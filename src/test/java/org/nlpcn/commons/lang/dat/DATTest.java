package org.nlpcn.commons.lang.dat;

import org.junit.Test;
import static org.nlpcn.commons.lang.TestUtils.*;

public class DATTest {

	@Test
	public void testMakeSaveAndLoad() throws Exception {
		DATMaker dat = new DATMaker();
		dat.maker(mainResources("pinyin.txt"));

        dat.saveText("/tmp/test_pinyin.dat");
        long start = System.currentTimeMillis();
        DoubleArrayTrie load = DoubleArrayTrie.loadText("/tmp/test_pinyin.dat");
        System.out.println("" + load.getItem("龙麝"));
        System.out.println("load obj use time " + (System.currentTimeMillis() - start));

        dat.save("/tmp/test_pinyin.obj");
        start = System.currentTimeMillis();
        load = DoubleArrayTrie.load("/tmp/test_pinyin.obj");
        System.out.println("" + load.getItem("龙麝"));
        System.out.println("load obj use time " + (System.currentTimeMillis() - start));
	}
}
