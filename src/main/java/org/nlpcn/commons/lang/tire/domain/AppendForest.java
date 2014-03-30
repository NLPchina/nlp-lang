package org.nlpcn.commons.lang.tire.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一个小树,和Forest的区别是.这个在首字也是用二分查找,意味着,更节省内存.但是在构造和查找的时候都慢一点,一般应用在.词少.或者临时词典中.
 * 在ansj分词中这个应用是在自适应分词
 * 一个参数可以递增的树
 *
 * @author ansj
 */
public class AppendForest<T> implements Comparable<AppendForest<T>>, WoodInterface {

	/**
	 * status 此字的状态1，继续 2，是个词语但是还可以继续 ,3确定 nature 词语性质
	 */
	public AppendForest<T>[] branches = null;
	// 单独查找出来的对象
	AppendForest<T> branch = null;
	private char c;
	// 状态
	private byte status = 1;
	// 词典后的参数
	private List<T> param = null;

	// root
	public AppendForest() {
	}

	// temp branch
	private AppendForest(char c) {
		this.c = c;
	}

	public AppendForest(char c, int status, T t) {
		this.c = c;
		this.status = (byte) status;
		this.param = new ArrayList<T>();
		this.param.add(t);
	}

	public static void main(String[] args) {
		AppendForest<Integer> sf = new AppendForest<Integer>();
		sf.append("java", 1);
		sf.append("java", 2);
		sf.append("java", 3);
		sf.append("php", 2);
		sf.append("python", 3);
		sf.append("ruby", 4);
		sf.append(".net", 5);

		AppendForest<Integer> branch2 = sf.getBranch("java");
		System.out.println(branch2.getParam());

		sf.add("java", 1);
		branch2 = sf.getBranch("java");
		System.out.println(branch2.getParam());

	}

	/**
	 * 增加子页节点
	 *
	 * @param branch2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AppendForest<T> addOrAppend(WoodInterface branch2, boolean append) {
		AppendForest<T> branch = (AppendForest<T>) branch2;
		if (branches == null) {
			branches = new AppendForest[0];
		}
		int bs = search(branch.getC());
		if (bs > -1) {
			this.branch = this.branches[bs];
			switch (branch.getStatus()) {
				case -1:
					this.branch.setStatus(1);
					break;
				case 1:
					if (this.branch.getStatus() == 3) {
						this.branch.setStatus(2);
					}
					break;
				case 3:
					if (this.branch.getStatus() != 3) {
						this.branch.setStatus(2);
					}
					if (append) {
						this.branch.param.addAll(branch.getParam());
					} else {
						this.branch.param = branch.getParam();
					}
			}
			return this.branch;
		}

		if (bs < 0) {
			AppendForest<T>[] newBranches = new AppendForest[branches.length + 1];
			int insert = -(bs + 1);
			System.arraycopy(this.branches, 0, newBranches, 0, insert);
			System.arraycopy(branches, insert, newBranches, insert + 1, branches.length - insert);
			newBranches[insert] = branch;
			this.branches = newBranches;
		}
		return branch;
	}

	public int search(char c) {
		if (branches == null)
			return -1;
		int i = Arrays.binarySearch(this.branches, new AppendForest<T>(c));
		return i;
	}

	/**
	 * 二分查找是否包含
	 *
	 * @param c
	 * @return
	 */
	public boolean contains(char c) {
		if (this.branches == null) {
			return false;
		}
		return Arrays.binarySearch(this.branches, c) > -1;
	}

	public int compareTo(char c) {
		if (this.c > c)
			return 1;
		if (this.c < c) {
			return -1;
		}
		return 0;
	}

	public boolean equals(char c) {
		return this.c == c;
	}

	@Override
	public int hashCode() {
		return this.c;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = (byte) status;
	}

	public char getC() {
		return this.c;
	}

	public List<T> getParam() {
		return this.param;
	}

	@Override
	public void setParam(String[] paramArrayOfString) {
		// TODO Auto-generated method stub

	}

	public void setParam(T t) {
		this.param.add(t);
	}

	/**
	 * 增加新词
	 *
	 * @param value
	 */
	public void add(String keyWord, T t) {
		AppendForest<T> tempBranch = this;
		for (int i = 0; i < keyWord.length(); i++) {
			if (keyWord.length() == i + 1) {
				tempBranch.addOrAppend(new AppendForest<T>(keyWord.charAt(i), 3, t), false);
			} else {
				tempBranch.addOrAppend(new AppendForest<T>(keyWord.charAt(i), 1, null), false);
			}
			tempBranch = tempBranch.branches[tempBranch.search(keyWord.charAt(i))];
		}
	}

	/**
	 * 增加新词
	 *
	 * @param value
	 */
	public void append(String keyWord, T t) {
		AppendForest<T> tempBranch = this;
		for (int i = 0; i < keyWord.length(); i++) {
			if (keyWord.length() == i + 1) {
				tempBranch.addOrAppend(new AppendForest<T>(keyWord.charAt(i), 3, t), true);
			} else {
				tempBranch.addOrAppend(new AppendForest<T>(keyWord.charAt(i), 1, null), true);
			}
			tempBranch = tempBranch.branches[tempBranch.search(keyWord.charAt(i))];
		}
	}

	public int compareTo(AppendForest<T> o) {
		// TODO Auto-generated method stub
		if (this.c > o.c)
			return 1;
		if (this.c < o.c) {
			return -1;
		}
		return 0;
	}

	/**
	 * 根据一个词获得所取的参数,没有就返回null
	 *
	 * @param keyWord
	 */
	public AppendForest<T> getBranch(String keyWord) {
		AppendForest<T> tempBranch = this;
		int index = 0;
		for (int j = 0; j < keyWord.length(); j++) {
			index = tempBranch.search(keyWord.charAt(j));
			if (index < 0) {
				return null;
			}
			tempBranch = tempBranch.branches[index];
		}
		return tempBranch;
	}

	@Override
	public WoodInterface add(WoodInterface branch) {
		// TODO Auto-generated method stub
		return this.addOrAppend(branch, false);
	}

	@Override
	public String[] getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WoodInterface get(char paramChar) {
		// TODO Auto-generated method stub
		return this.branches[this.search(paramChar)];
	}


}
