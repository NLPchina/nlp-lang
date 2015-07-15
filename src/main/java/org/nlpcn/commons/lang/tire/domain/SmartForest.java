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

    // for search
    private SmartForest(final char c) {
        this.c = c;
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

    @SuppressWarnings("unchecked")
    public void addBranch(final String keyWord, final P param) {
        this.addBranchOfSameType((Class<SmartForest<P>>) this.getClass(), keyWord, param, MAX_SIZE, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SmartForest<P> addBranch(final SmartForest<P> b) {
        return this.addBranch((Class<SmartForest<P>>) this.getClass(), b, MAX_SIZE, false);
    }

    @Override
    protected SmartForest<P> onAddBranchThatExists(final SmartForest<P> b, final int idx, final boolean append) {
        if (this.branches[idx] == null) {
            this.branches[idx] = b;
        }
        return super.onAddBranchThatExists(b, idx, append);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected SmartForest<P> onAddBranchThatNotExists(final Class<SmartForest<P>> branchType, final SmartForest<P> b, final int idx) {
        // 如果数组内元素接近于最大值直接数组定位，rate是内存和速度的一个平衡
        if (this.branches != null && this.branches.length >= MAX_SIZE * rate) {
            SmartForest<P>[] tempBranches = new SmartForest[MAX_SIZE];
            for (final SmartForest<P> element : this.branches) {
                tempBranches[element.getC()] = element;
            }
            tempBranches[b.getC()] = b;
            this.branches = null;
            this.branches = tempBranches;
        } else {
            SmartForest<P>[] newBranches = new SmartForest[this.branches.length + 1];
            int insert = -(idx + 1);
            System.arraycopy(this.branches, 0, newBranches, 0, insert);
            System.arraycopy(this.branches, insert, newBranches, insert + 1, this.branches.length - insert);
            newBranches[insert] = b;
            this.branches = newBranches;
        }
        return b;
    }

    @Override
    protected int getBranchIndex(final char c, final Integer maxSize) {
        return this.getBranchIndexByJdkArrays(c, maxSize);
    }

    @Override
    protected SmartForest<P> forSearch(final char c) {
        return new SmartForest<>(c);
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
