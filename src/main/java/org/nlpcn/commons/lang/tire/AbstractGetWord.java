package org.nlpcn.commons.lang.tire;

import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.WoodInterface;

public abstract class AbstractGetWord<P, B extends WoodInterface<P, B>> {

	private static final String EMPTYSTRING = "";

	private final B forest;
	private char[] chars;

	public int offe;
	byte status;
	int root;
	int i;
	boolean isBack;
	private String str;
	private int tempOffe;
	private P param;
	private B branch;

	public AbstractGetWord(final B forest, final char[] chars) {
		this.chars = chars;
		this.forest = forest;
		this.reset();
	}

	public AbstractGetWord(final B forest, final String content) {
		this(forest, content.toCharArray());
	}

	@SuppressWarnings("unchecked")
	public AbstractGetWord(final Forest forest, final char[] chars) {
		this((B) forest, chars);
	}

	public AbstractGetWord(final Forest forest, final String content) {
		this(forest, content.toCharArray());
	}

	private void reset() {
		this.offe = 0;
		this.status = 0;
		this.root = 0;
		this.i = this.root;
		this.isBack = false;
		this.tempOffe = 0;
		this.branch = this.forest;
	}

	public void reset(final String content) {
		this.reset();
		this.chars = content.toCharArray();
	}

	public String getAllWords() {
		String temp = this.allWords();
		while (EMPTYSTRING.equals(temp)) {
			temp = this.allWords();
		}
		return temp;
	}

	public String getFrontWords() {
		String temp = this.frontWords();
		while (EMPTYSTRING.equals(temp)) {
			temp = this.frontWords();
		}
		return temp;
	}

	private String allWords() {
		if ((!this.isBack) || (this.i == this.chars.length - 1)) {
			this.i = (this.root - 1);
		}
		for (this.i += 1; this.i < this.chars.length; this.i = (this.i + 1)) {
			this.branch = this.branch.getBranch(this.chars[this.i]);
			if (this.branch == null) {
				this.root += 1;
				this.branch = this.forest;
				this.i = (this.root - 1);
				this.isBack = false;
			} else {
				switch (this.branch.getStatus()) {
				case 2:
					this.isBack = true;
					this.offe = (this.tempOffe + this.root);
					this.param = this.branch.getParam();
					return new String(this.chars, this.root, this.i - this.root + 1);
				case 3:
					this.offe = (this.tempOffe + this.root);
					this.str = new String(this.chars, this.root, this.i - this.root + 1);
					this.param = this.branch.getParam();
					this.branch = this.forest;
					this.isBack = false;
					this.root += 1;
					return this.str;
				}
			}
		}
		this.tempOffe += this.chars.length;
		return null;
	}

	private String frontWords() {
		for (; this.i < this.chars.length + 1; this.i++) {
			if (i == chars.length) {
				this.branch = null;
			} else {
				this.branch = this.branch.getBranch(this.chars[this.i]);
			}
			if (this.branch == null) {
				this.branch = this.forest;
				if (this.isBack) {
					this.offe = this.root;
					this.str = new String(this.chars, this.root, this.tempOffe);

					if ((this.root > 0) && (isE(this.chars[(this.root - 1)])) && (isE(this.str.charAt(0)))) {
						this.str = EMPTYSTRING;
					}

					if ((this.str.length() != 0) && (this.root + this.tempOffe < this.chars.length) && (isE(this.str.charAt(this.str.length() - 1)))
							&& (isE(this.chars[(this.root + this.tempOffe)]))) {
						this.str = EMPTYSTRING;
					}
					if (this.str.length() == 0) {
						this.root += 1;
						this.i = this.root;
					} else {
						this.i = (this.root + this.tempOffe);
						this.root = this.i;
					}
					this.isBack = false;

					if (EMPTYSTRING.equals(this.str)) {
						return EMPTYSTRING;
					}
					return this.str;
				}
				this.i = this.root;
				this.root += 1;
			} else {
				switch (this.branch.getStatus()) {
				case 2:
					this.isBack = true;
					this.tempOffe = (this.i - this.root + 1);
					this.param = this.branch.getParam();
					break;
				case 3:
					this.offe = this.root;
					this.str = new String(this.chars, this.root, this.i - this.root + 1);
					String temp = this.str;

					if ((this.root > 0) && (isE(this.chars[(this.root - 1)])) && (isE(this.str.charAt(0)))) {
						this.str = EMPTYSTRING;
					}

					if ((this.str.length() != 0) && (this.i + 1 < this.chars.length) && (isE(this.str.charAt(this.str.length() - 1)))
							&& (isE(this.chars[(this.i + 1)]))) {
						this.str = EMPTYSTRING;
					}
					this.param = this.branch.getParam();
					this.branch = this.forest;
					this.isBack = false;
					if (temp.length() > 0) {
						this.i += 1;
						this.root = this.i;
					} else {
						this.i = (this.root + 1);
					}
					if (EMPTYSTRING.equals(this.str)) {
						return EMPTYSTRING;
					}
					return this.str;
				}
			}
		}
		this.tempOffe += this.chars.length;
		return null;
	}

	public boolean isE(char c) {
		return c == '.' || ((c >= 'a') && (c <= 'z'));
	}

	/**
	 * 参数
	 *
	 * @return 全部参数
	 */
	public P getParam() {
		return this.param;
	}
}
