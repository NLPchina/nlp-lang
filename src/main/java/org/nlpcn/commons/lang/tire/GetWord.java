package org.nlpcn.commons.lang.tire;

import org.nlpcn.commons.lang.tire.domain.Branch;
import org.nlpcn.commons.lang.tire.domain.Forest;

public class GetWord extends AbstractGetWord<String[], Branch> {

    public GetWord(final Forest forest, final char[] chars) {
        super(forest, chars);
    }

    public GetWord(final Forest forest, final String content) {
        super(forest, content);
    }

    public String getParam(final int i) {
        final String[] param = this.getParam();
        if (param == null || i >= param.length) {
            return null;
        } else {
            return param[i];
        }
    }
}