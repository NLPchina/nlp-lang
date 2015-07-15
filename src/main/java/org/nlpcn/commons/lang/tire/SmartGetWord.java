package org.nlpcn.commons.lang.tire;

import org.nlpcn.commons.lang.tire.domain.SmartForest;







public class SmartGetWord<P> extends AbstractGetWord<P, SmartForest<P>> {

	public SmartGetWord(final SmartForest<P> forest, final char[] chars) {
		super(forest, chars);
	}

	public SmartGetWord(final SmartForest<P> forest, final String content) {
        super(forest, content);
	}
}
