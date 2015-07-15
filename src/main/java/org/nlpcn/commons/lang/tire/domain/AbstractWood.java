package org.nlpcn.commons.lang.tire.domain;

import lombok.Getter;
import org.nlpcn.commons.lang.util.AnsjArrays;

import java.lang.reflect.Array;

public abstract class AbstractWood<P, B extends AbstractWood<P, B>> implements WoodInterface<P, B> {

    protected static final Integer MAX_SIZE = 65536;

    @Getter
    protected B[] branches = null;
    // 单独查找出来的对象
    protected B branch = null;

    public int getSize() {
        return this.branches != null ? this.branches.length : 0;
    }

    /**
     * 二分查找是否包含
     *
     * @param c c
     * @return contains
     */
    protected boolean containsBinarySearch(final Character c) {
        //Arrays.binarySearch(this.branches, c) > -1;
        return this.branches != null && AnsjArrays.binarySearch(this.branches, c) > -1;
    }

    protected B getBranch(char c, Integer maxSize) {
        if (this.branches == null) {
            return null;
        }
        final int idx = this.getBranchIndex(c, maxSize);
        return idx > 0 ? this.branches[idx] : null;
    }

    /**
     * 根据一个词获得所取的参数,没有就返回null
     *
     * @param chars chars
     */
    @SuppressWarnings("unchecked")
    protected B getBranchOfSameType(final char[] chars, final Integer maxSize) {
        B b = (B) this;
        for (char c : chars) {
            final int idx = b.getBranchIndex(c, maxSize);
            if (idx < 0 || (b = b.branches[idx]) == null) {
                return null;
            }
        }
        return b;
    }

    protected int getBranchIndex(final char c, final Integer maxSize) {
        if (this.branches == null) {
            return -1;
        } else if (maxSize != null && this.branches.length == maxSize) {
            return c;
        } else {
            return AnsjArrays.binarySearch(this.branches, c);
        }
    }

    /**
     * 增加子页节点
     */
    protected B addBranch(final Class<B> branchType, final B branch, final boolean append) {
        if (this.branches == null) {
            this.branches = newArray(branchType, 0);
        }
        int idx = getBranchIndex(branch.getC(), null);
        if (idx >= 0) {
            return this.onAddBranchThatExists(idx, append);
        } else {
            return this.onAddBranchThatNotExists(branchType, idx);
        }
    }

    protected B onAddBranchThatExists(final int idx, final boolean append) {
        this.branch = this.branches[idx];
        switch (this.branch.getStatus()) {
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
                this.onNatureIdentified(this.branch.getParam(), append);
        }
        return this.branch;
    }

    abstract protected void onNatureIdentified(final P param, final boolean append);

    protected B onAddBranchThatNotExists(final Class<B> branchType, final int idx) {
        B[] newBranches = newArray(branchType, this.branches.length + 1);
        int insert = -(idx + 1);
        System.arraycopy(this.branches, 0, newBranches, 0, insert);
        System.arraycopy(this.branches, insert, newBranches, insert + 1, this.branches.length - insert);
        newBranches[insert] = this.branch;
        this.branches = newBranches;
        return branch;
    }

    /**
     * 增加新词
     */
    @SuppressWarnings("unchecked")
    protected void addBranchOfSameType(final Class<B> branchType, final String keyWord, final P param, final Integer maxSize, final boolean append) {
        B b = (B) this;
        for (int i = 0; i < keyWord.length(); i++) {
            if (keyWord.length() != i + 1) {
                b.addBranch(branchType, this.newBranch(keyWord.charAt(i), 1, null), append);
            } else {
                b.addBranch(branchType, this.newBranch(keyWord.charAt(i), 3, param), append);
            }
            b = b.branches[b.getBranchIndex(keyWord.charAt(i), maxSize)];
        }
    }

    abstract protected B newBranch(final char c, final int status, final P param);

    //

    protected char c = '\000';

    @Override
    public char getC() {
        return this.c;
    }

//    @Override
//    public char getC() {
//        return '\000';
//    }

    public int compareTo(final char c) {
        if (this.c > c)
            return 1;
        if (this.c < c) {
            return -1;
        }
        return 0;
    }

    public int compareTo(final B o) {
        return compareTo(o.getC());
    }

    @Override
    public boolean equals(final char c) {
        return this.c == c;
    }

    @Override
    public int hashCode() {
        return this.c;
    }

    /**
     * 状态
     * status 此字的状态1，继续 2，是个词语但是还可以继续 ,3确定 nature 词语性质
     */
    protected byte status = 1;

    @Override
    public byte getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(final int status) {
        this.status = (byte) status;
    }

    // 词典后的参数
    protected P param = null;

    public void setParam(final P param) {
        this.param = param;
    }

    public P getParam() {
        return this.param;
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] newArray(final Class<T> componentType, final int length) {
        return (T[]) Array.newInstance(componentType, length);
    }
}
