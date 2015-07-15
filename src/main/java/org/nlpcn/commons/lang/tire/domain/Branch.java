package org.nlpcn.commons.lang.tire.domain;

public class Branch extends AbstractWood<String[], Branch> {

    public Branch(final char c, final int status, final String[] param) {
        this.c = c;
        this.status = (byte) status;
        this.param = param;
    }

    public String getParam(final int idx) {
        return this.param != null && this.param.length > idx ? this.param[idx] : null;
    }

    @Override
    public boolean contains(final char c) {
        return this.containsBinarySearch(c);
    }

    @Override
    public Branch getBranch(final char c) {
        return this.getBranch(c, null);
    }

    @Override
    public Branch addBranch(final Branch branch) {
        return this.addBranch(Branch.class, branch, false);
    }

    @Override
    protected void onNatureIdentified(final String[] param, final boolean append) {
        this.branch.setParam(param);
    }

    @Override
    protected Branch newBranch(final char c, final int status, final String[] param) {
        throw new UnsupportedOperationException();
    }
}
