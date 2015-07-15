package org.nlpcn.commons.lang.tire.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个小树,和Forest的区别是.这个在首字也是用二分查找,意味着,更节省内存.但是在构造和查找的时候都慢一点,一般应用在.词少.或者临时词典中.
 * 在ansj分词中这个应用是在自适应分词
 * 一个参数可以递增的树
 *
 * @author ansj
 */
public class AppendForest<P> extends AbstractWood<List<P>, AppendForest<P>> {

    // root
    public AppendForest() {
    }

    // for search
    private AppendForest(final char c) {
        this.c = c;
    }

    public AppendForest(char c, int status, List<P> param) {
        this.c = c;
        this.status = (byte) status;
        this.param = new ArrayList<>();
        this.param.addAll(param);
    }

    public boolean contains(char c) {
        return this.containsBinarySearch(c);
    }

    public void addParam(P p) {
        this.param.add(p);
    }

    /**
     * 根据一个词获得所取的参数,没有就返回null
     *
     * @param keyWord
     */
    public AppendForest<P> getBranch(final String keyWord) {
        return this.getBranchOfSameType(keyWord.toCharArray(), null);
    }

    @Override
    public AppendForest<P> getBranch(final char c) {
        return this.getBranch(c, null);
    }

    /**
     * 增加新词
     */
    public void append(final String keyWord, final P param) {
        final List<P> params = new ArrayList<>();
        params.add(param);
        this.addBranchOfSameType((Class<AppendForest<P>>) this.getClass(), keyWord, params, null, true);
    }

    /**
     * 增加新词
     */
    public void addBranch(final String keyWord, final P param) {
        final List<P> params = new ArrayList<>();
        params.add(param);
        this.addBranchOfSameType((Class<AppendForest<P>>) this.getClass(), keyWord, params, null, false);
    }

    @Override
    public AppendForest<P> addBranch(final AppendForest<P> b) {
        return this.addBranch((Class<AppendForest<P>>) this.getClass(), b, null, false);
    }

    @Override
    protected int getBranchIndex(char c, Integer maxSize) {
        return this.getBranchIndexByJdkArrays(c, maxSize);
    }

    @Override
    protected AppendForest<P> forSearch(char c) {
        return new AppendForest<>(c);
    }

    @Override
    protected void onNatureIdentified(final List<P> param, final boolean append) {
        if (append) {
            this.branch.getParam().addAll(param);
        } else {
            this.branch.setParam(param);
        }
    }

    @Override
    protected AppendForest<P> newBranch(final char c, final int status, final List<P> param) {
        return new AppendForest<>(c, status, param);
    }

    public static void main(final String[] args) {
        final AppendForest<Integer> sf = new AppendForest<>();
        sf.append("java", 1);
        sf.append("java", 2);
        sf.append("java", 3);
        sf.append("php", 2);
        sf.append("python", 3);
        sf.append("ruby", 4);
        sf.append(".net", 5);

        AppendForest<Integer> branch2 = sf.getBranch("java");
        System.out.println(branch2.getParam());

        sf.addBranch("java", 1);
        branch2 = sf.getBranch("java");
        System.out.println(branch2.getParam());
    }
}
