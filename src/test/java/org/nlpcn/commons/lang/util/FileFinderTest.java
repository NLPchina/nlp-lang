package org.nlpcn.commons.lang.util;

import org.junit.Test;

import java.io.File;

public class FileFinderTest {

	@Test
	public void test() {
		System.out.println(FileFinder.find("javac.exe"));

		System.out.println(FileFinder.findByFile(new File(""),"java.exe"));
	}

}
