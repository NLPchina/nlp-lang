package org.nlpcn.commons.lang.tire.domain;

import java.util.Arrays;

public class Value {

    private static final String TAB = "\t";

    private String   keyword;
    private String[] paramers;

    public Value(final String keyword, final String... paramers) {
        this.keyword = keyword;
        if (paramers != null) {
            this.paramers = paramers;
        }
    }

    /**
     * @param value keyword以及每个param之间用TAB分隔的字符串
     */
    public Value(final String value) {
        String[] strs = value.split(TAB);
        this.keyword = strs[0];
        if (strs.length > 1) {
            this.paramers = Arrays.copyOfRange(strs, 1, strs.length);
        } else {
            this.paramers = new String[0];
        }
    }

    public String getKeyword() {
        return keyword;
    }


    public String[] getParamers() {
        return paramers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(keyword);
        for (final String paramer : paramers) {
            sb.append(TAB);
            sb.append(paramer);
        }
        return sb.toString();
    }
}
