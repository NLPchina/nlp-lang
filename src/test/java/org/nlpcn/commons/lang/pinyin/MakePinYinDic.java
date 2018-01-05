package org.nlpcn.commons.lang.pinyin;

import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ansj on 03/01/2018.
 */
public class MakePinYinDic {

	public static void main(String[] args) throws IOException {
		List<String> strings = IOUtil.readFile2List("../nlp-lang/src/test/resources/pinyin.txt", "utf-8");

		Map<String,String[]> map = new HashMap<>() ;

		strings.stream().forEach((l)->{
			String[] split = l.split("=");

			if(split.length==2){
				String word = split[0] ;
				split = split[1].split(",");
				map.put(word,split) ;
			}
		});



		FileOutputStream fos = new FileOutputStream(new File("../nlp-lang/src/main/resources/polyphone.txt")) ;


		IOUtil.readFile2List("../nlp-lang/src/test/resources/polyphone_all.txt", "utf-8").stream().forEach(s->{
			String[] split = s.split("=");
			String word = split[0] ;
			String[] py = split[1].split(" ") ;

			int len = word.length() ;

			String[] wordPy = new String[len] ;

			for (int i = 0; i < len; i++) {
				char c = word.charAt(i) ;

				wordPy[i] = map.get(String.valueOf(c))[0];
			}

			boolean flag = true ;
			for (int i = 0; i < len; i++) {
				String p = py[i] ;
				String wp = wordPy[i] ;
				if(!p.equals(wp) && !wp.replaceAll("\\d","").equals(p)){
					flag = false ;
					break ;
				}
			}



			if(!flag){
				System.out.println(s);
				for (int i = 0; i < len; i++) {
					char c = word.charAt(i) ;
					String p = py[i] ;
					String[] str = map.get(String.valueOf(c));
					for (String wp : str) {
						if(p.equals(wp) || wp.replaceAll("\\d","").equals(p)){
							py[i] = wp ;
						}
					}
				}

				try {
					fos.write((word+"="+ StringUtil.joiner(py," ")+"\n").getBytes("utf-8"));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		});


		fos.flush();
		fos.close();


	}
	
}
