package org.nlpcn.commons.lang.extracting;

import org.junit.Test;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.WordAlert;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Ansj on 29/08/2017.
 */
public class LexicalTest {
	@Test
	public void test() throws IOException {
		BufferedReader br = IOUtil.getReader(LexicalTest.class.getResourceAsStream("/rule.txt"), "utf-8");
		String temp = null ;
		while((temp=br.readLine())!=null){
			System.out.println(new Lexical(temp).parse()); ;
		}


	}
}
