package org.nlpcn.commons.lang.tire.domain;


import org.nlpcn.commons.lang.util.AnsjArrays;

public class Branch implements WoodInterface {
	/**
	 * status 此字的状态1，继续 2，是个词语但是还可以继续 ,3确定 nature 词语性质
	 */
	WoodInterface[] branches = null;
	// 单独查找出来的对象
	WoodInterface branch = null;
	private char c;
	// 状态
	private byte status = 1;
	// 词典后的参数
	private String[] param = null;

	public Branch(char c, int status, String[] param) {
		this.c = c;
		this.status = (byte) status;
		this.param = param;
	}

	public WoodInterface add(WoodInterface branch) {
		if (branches == null) {
			branches = new WoodInterface[0];
		}
		int bs = AnsjArrays.binarySearch(branches, branch.getC());
		if (bs >= 0) {
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
					this.branch.setParam(branch.getParams());
			}
			return this.branch;
		} else {
			WoodInterface[] newBranches = new WoodInterface[branches.length + 1];
			int insert = -(bs + 1);
			System.arraycopy(branches, 0, newBranches, 0, insert);
			System.arraycopy(branches, insert, newBranches, insert + 1, branches.length - insert);
			newBranches[insert] = branch;
			branches = newBranches;
			return branch;
		}
	}

	public WoodInterface get(char c) {
		if (this.branches == null) {
			return null;
		}
		int i = AnsjArrays.binarySearch(this.branches, c);
		if (i < 0) {
			return null;
		}
		return this.branches[i];
	}

	public boolean contains(char c) {
		if (this.branches == null) {
			return false;
		}
		return AnsjArrays.binarySearch(this.branches, c) > -1;
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

	public String[] getParams() {
		return this.param;
	}

	/**
	 * 得道第几个参数
	 *
	 * @param i
	 * @return
	 */
	public String getParam(int i) {
		if (param != null && param.length > i) {
			return param[i];
		}
		return null;
	}

	public void setParam(String[] param) {
		this.param = param;
	}
}
