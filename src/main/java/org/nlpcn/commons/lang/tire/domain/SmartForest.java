package org.nlpcn.commons.lang.tire.domain;

import org.nlpcn.commons.lang.tire.SmartGetWord;


/**
 * 一个小树,和Forest的区别是.这个在首字也是用二分查找,做过一次优化.达到到达一定量级自动扩展为hash定位 在ansj分词中这个应用是在自适应分词
 *
 * @author ansj
 */
public class SmartForest<P> extends AbstractWood<P, SmartForest<P>> {

    private double rate = 0.9;

    // root
    public SmartForest() {
    }

    // 首位直接数组定位
    @SuppressWarnings("unchecked")
    public SmartForest(final double rate) {
        this.branches = new SmartForest[MAX_SIZE];
        this.rate = rate;
    }

    public SmartForest(final char c, final int status, final P param) {
        this.c = c;
        this.status = (byte) status;
        this.param = param;
    }

    public boolean contains(final char c) {
        return this.containsBinarySearch(c);
    }

    public SmartGetWord<P> getWord(final char[] chars) {
        return new SmartGetWord<>(this, chars);
    }

    public SmartForest<P> getBranch(final char[] chars) {
        return this.getBranchOfSameType(chars, MAX_SIZE);
    }

    public SmartForest<P> getBranch(final char c) {
        return this.getBranch(c, MAX_SIZE);
    }

    public SmartForest<P> getBranch(final String keyWord) {
        return getBranch(keyWord.toCharArray());
    }

    public void addBranch(final String keyWord, final P param) {
        this.addBranchOfSameType((Class<SmartForest<P>>) this.getClass(), keyWord, param, MAX_SIZE, false);
    }

    @Override
    public SmartForest<P> addBranch(final SmartForest<P> branch) {
        if (branches == null) {
            branches = new SmartForest[0];
        }
        int idx = getBranchIndex(branch.getC(), MAX_SIZE);
        if (idx >= 0) {
            if (this.branches[idx] == null) {
                this.branches[idx] = branch;
            }
            return this.onAddBranchThatExists(idx, false);
        } else {
            return this.onAddBranchThatNotExists(idx);
        }
    }

    @SuppressWarnings("unchecked")
    protected SmartForest<P> onAddBranchThatNotExists(final int idx) {
        // 如果数组内元素接近于最大值直接数组定位，rate是内存和速度的一个平衡
        if (this.branches != null && this.branches.length >= MAX_SIZE * rate) {
            SmartForest<P>[] tempBranches = new SmartForest[MAX_SIZE];
            for (final SmartForest<P> b : this.branches) {
                tempBranches[b.getC()] = b;
            }
            tempBranches[this.branch.getC()] = this.branch;
            this.branches = null;
            this.branches = tempBranches;
        } else {
            SmartForest<P>[] newBranches = new SmartForest[this.branches.length + 1];
            int insert = -(idx + 1);
            System.arraycopy(this.branches, 0, newBranches, 0, insert);
            System.arraycopy(this.branches, insert, newBranches, insert + 1, this.branches.length - insert);
            newBranches[insert] = this.branch;
            this.branches = newBranches;
        }
        return this.branch;
    }

    @Override
    protected void onNatureIdentified(final P param, final boolean append) {
        this.branch.setParam(param);
    }

    @Override
    protected SmartForest<P> newBranch(char c, int status, P param) {
        return new SmartForest<>(c, status, param);
    }
}
