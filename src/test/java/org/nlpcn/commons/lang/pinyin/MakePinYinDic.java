package org.nlpcn.commons.lang.pinyin;

import org.nlpcn.commons.lang.util.IOUtil;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Ansj on 03/01/2018.
 */
public class MakePinYinDic {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		List<String> strings = IOUtil.readFile2List("../nlp-lang/src/test/resources/pinyin.txt", "utf-8");

		Set<String> set = new HashSet<>() ;

		strings.parallelStream().forEach((l)->{
			String[] split = l.split("=");

			if(split.length==2){
				split = split[1].split(",");
				for (String s : split) {
					System.out.println(s);
					set.add(s) ;
				}
			}
		});

		String[] ts = set.toArray(new String[0]);

		Map<String,Integer> map = new HashMap<>() ;

		for (int i = 0; i < ts.length; i++) {
			map.put(ts[i],i) ;
		}





	}
	
}
