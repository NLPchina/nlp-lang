package org.nlpcn.commons.lang.extracting;

import org.nlpcn.commons.lang.extracting.domain.*;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ansj on 29/08/2017.
 */
public class Lexical {

	private int beginIndex;

	private List<Token> tokens = new ArrayList<Token>();

	private List<GroupToken> groups = new ArrayList<GroupToken>();

	private String rule;

	private int len;

	private int index = 0;


	public Lexical(String rule) {
		this.rule = rule;
		this.len = rule.length();
	}

	public List<Token> parse() {
		if (StringUtil.isBlank(rule)) {
			return Collections.emptyList();
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			switch (rule.charAt(i)) {
				case '{':
					makeToken(sb);
					beginIndex = index;
					break;
				case '}':
					groups.add(new GroupToken(beginIndex, index));
					break;
				case '(':
					makeToken(sb);
					i = findEnd(i, ')');
					break;
				case '[':
					makeToken(sb);
					i = findEnd(i, ']');
					break;
				case '\\':
					sb.append(rule.charAt(++i));
					break;
				default:
					sb.append(rule.charAt(i));

			}

		}

		return tokens;
	}

	private void makeToken(StringBuilder sb) {
		if (sb.length() != 0) {
			tokens.add(new StrToken(index, sb.toString()));
			index++;
		}
	}

	private int findEnd(int i, char c) {
		StringBuilder sb = new StringBuilder();
		int j = i + 1;
		for (; j < len; j++) {
			if (rule.charAt(j) == '\\') {
				j++;
			} else if (rule.charAt(j) == c) {
				break;
			}

			sb.append(rule.charAt(j));
		}

		makeToken(sb.toString(), c);


		index++;

		return j;
	}

	private void makeToken(String s, char c) {
		if (c == ')') {
			String[] split = s.split("\\|");
			TermToken termToken = new TermToken();
			termToken.add(split);
			tokens.add(termToken);
			index++;
		} else if (c == ']') {
			String tmp = s.replace("\\s+", "");
			if (tmp.matches("\\d+,\\d+")) {
				String[] split = tmp.split(",");
				tokens.add(new LenToken(index, Integer.parseInt(split[0]), Integer.parseInt(split[1])));
				index++;
			} else if (tmp.matches("\\d+")) {
				String[] split = tmp.split(",");
				tokens.add(new LenToken(index, Integer.parseInt(split[0]), Integer.parseInt(split[0])));
				index++;
			}
		}
	}

}