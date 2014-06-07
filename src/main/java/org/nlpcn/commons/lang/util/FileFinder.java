package org.nlpcn.commons.lang.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 从系统各个环境中找文件
 * 
 * @author ansj
 * 
 */
public class FileFinder {
	/**
	 * 系统路径分隔符
	 */
	private static final String SEPARATOR = System.getProperty("path.separator");
	private static final String[] PATHS_PROPERTIES = new String[] { "java.class.path", "java.library.path", "sun.boot.library.path", "user.dir", "java.endorsed.dirs", "java.io.tmpdir",
			"user.home", "java.home", "java.ext.dirs" };

	private static final String[] DEEP_PATHS_PROPERTIES = new String[] { "java.class.path", "user.dir" };

	private static final List<String> PATHS = new ArrayList<String>();

	private static final List<String> DEEP_PATHS = new ArrayList<String>();

	static {
		for (String pathProperties : PATHS_PROPERTIES) {
			String[] propertyPath = System.getProperty(pathProperties).split(SEPARATOR);
			for (String path : propertyPath) {
				File file = new File(path);
				if (file.exists() && file.canRead()) {
					PATHS.add(file.getAbsolutePath());
				}
			}
		}

		for (String pathProperties : DEEP_PATHS_PROPERTIES) {
			String[] propertyPath = System.getProperty(pathProperties).split(SEPARATOR);
			for (String path : propertyPath) {
				File file = new File(path);
				if (file.exists() && file.canRead()) {
					DEEP_PATHS.add(file.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 输入一个文件名或者文件的最后路径寻找文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static File find(String lastPath) {

		// 先深度查找
		for (String path : DEEP_PATHS) {
			File file = findByFile(new File(path), lastPath);
			if (file != null) {
				return file;
			}
		}

		// 再从基本几个目录中查找
		for (String path : PATHS) {
			File file = new File(path);
			if (file.getAbsolutePath().endsWith(lastPath)) {
				return file;
			}
			if (file.isDirectory()) {
				File[] listFiles = file.listFiles();
				for (File file2 : listFiles) {
					if (file2.canRead() && file.getAbsolutePath().endsWith(lastPath)) {
						return file2;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 根据一个文件深度查找
	 * 
	 * @param file
	 * @param lastPath
	 * @return
	 */
	private static File findByFile(File file, String lastPath) {
		if (!file.exists() || !file.canRead()) {
			return null;
		}
		if (file.getAbsolutePath().endsWith(lastPath)) {
			return file;
		}
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				File temp = findByFile(file2, lastPath);
				if(temp!=null){
					return temp ;
				}
			}
		}
		return null;
	}

	public static void addDeepPath(String path) {
		DEEP_PATHS.add(0,path);
	}
	
	public static void addPath(String path) {
		PATHS.add(0,path);
	}
	
	public static void main(String[] args) {
		System.out.println(FileFinder.find("library"));;
	}
}
