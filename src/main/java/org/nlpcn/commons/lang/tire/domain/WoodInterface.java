package org.nlpcn.commons.lang.tire.domain;

public interface WoodInterface<P, B> extends Comparable<B> {

    B addBranch(B branch);

    B getBranch(char ch);

    byte getStatus();

    void setStatus(int status);

    char getC();

    boolean contains(char paramChar);

    int compareTo(char paramChar);

    boolean equals(char paramChar);

    P getParam();

    void setParam(P param);
}