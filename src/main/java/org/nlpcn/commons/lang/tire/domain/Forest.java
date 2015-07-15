package org.nlpcn.commons.lang.tire.domain;

import org.nlpcn.commons.lang.tire.GetWord;

public class Forest extends AbstractWood<String[], Branch> {

    public Forest() {
        this.branches = new Branch[MAX_SIZE];
    }

    public int getNature() {
        return 0;
    }

    public void setNature(final int nature) {
    }

    /**
     * 得到一个分词对象
     */
    public GetWord getWord(final String content) {
        return new GetWord(this, content);
    }

    /**
     * 得到一个分词对象
     */
    public GetWord getWord(char[] chars) {
        return new GetWord(this, chars);
    }

    /**
     * 清空树释放内存
     */
    public void clear() {
        this.branches = new Branch[MAX_SIZE];
    }

    @Override
    public boolean contains(final char c) {
        return this.branches[c] != null;
    }

    @Override
    public int compareTo(final char c) {
        return 0;
    }

    @Override
    public boolean equals(final char c) {
        return false;
    }

    @Override
    public void setParam(final String[] param) {
    }

    @Override
    public Branch getBranch(final char c) {
        return c < MAX_SIZE ? this.branches[c] : null;
    }

    @Override
    public Branch addBranch(final Branch branch) {
        WoodInterface<String[], Branch> temp = this.branches[branch.getC()];
        if (temp == null)
            this.branches[branch.getC()] = branch;
        else {
            switch (branch.getStatus()) {
                case 1:
                    if (temp.getStatus() == 3) {
                        temp.setStatus(2);
                    }
                    break;
                case 3:
                    if (temp.getStatus() == 1) {
                        temp.setStatus(2);
                    }
                    temp.setParam(branch.getParam());
                    break;
            }
        }

        return this.branches[branch.getC()];
    }

    @Override
    protected int getBranchIndex(char c, Integer maxSize) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Branch forSearch(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void onNatureIdentified(final String[] param, final boolean append) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Branch newBranch(final char c, final int status, final String[] param) {
        throw new UnsupportedOperationException();
    }
}