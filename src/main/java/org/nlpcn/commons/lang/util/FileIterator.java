package org.nlpcn.commons.lang.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 文件迭代器
 *
 * @author ansj
 */
public class FileIterator {
	String temp = null;
	private BufferedReader br = null;

	protected FileIterator(String path, String charEncoding) throws UnsupportedEncodingException,
			FileNotFoundException {
		br = IOUtil.getReader(path, charEncoding);
	}

	public boolean hasNext() {
		if (temp == null) {
			try {
				temp = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (temp == null) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public String readLine() {
		try {
			if (temp == null) {
				temp = br.readLine();
			}
			return temp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			temp = null;
		}
	}

	public void close() {
		if (br != null)
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
