package org.nlpcn.commons.lang.pinyin;

import org.nlpcn.commons.lang.dic.DicManager;
import org.nlpcn.commons.lang.tire.SmartGetWord;
import org.nlpcn.commons.lang.tire.domain.SmartForest;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Pinyin {

    private static final SmartForest<String[]> PINYIN_FOREST = DicManager.getPinyinForest();

    /**
     * 拼音返回
     *
     * @param str str
     * @return ['zhong3','guo4']
     */
    public static List<String> pinyinStr(final String str) {
        return str2Pinyin(str).stream()
                .map(PinyinWord::toString)
                .collect(Collectors.toList());
    }

    /**
     * 拼音解析
     *
     * @param str str
     * @return pinyinWords
     */
    public static List<PinyinWord> str2Pinyin(String str) {

        if (StringUtil.isBlank(str)) {
            return Collections.emptyList();
        }

        SmartGetWord<String[]> sgw = new SmartGetWord<>(PINYIN_FOREST, str);

        int beginOffe = 0;
        int wordOffe = 0;
        String word = null;
        String[] strs = null;
        List<PinyinWord> result = new ArrayList<>();

        while ((word = sgw.getFrontWords()) != null) {
            wordOffe = sgw.offe;
            if (beginOffe < wordOffe) {
                for (int i = beginOffe; i < wordOffe; i++) {
                    result.add(new PinyinWord(str.charAt(i)));
                }
            }
            strs = sgw.getParam();
            for (String pStr : strs) {
                result.add(new PinyinWord(pStr));
            }

            beginOffe = wordOffe + word.length();
        }

        if (beginOffe < str.length()) {
            for (int i = beginOffe; i < str.length(); i++) {
                result.add(new PinyinWord(str.charAt(i)));
            }
        }

        return result;

    }

    /**
     * @return 每个字的拼音,不要声调
     */
    public static List<String> pinyinWithoutTone(final String str) {
        return str2Pinyin(str).stream()
                .map(pw -> pw.py)
                .collect(Collectors.toList());
    }

    /**
     * @param str str
     * @return 取得每个字的首字符
     */
    public static char[] str2FirstCharArr(String str) {
        List<PinyinWord> str2Pinyin = str2Pinyin(str);
        char[] chars = new char[str2Pinyin.size()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = str2Pinyin.get(i).py.charAt(0);
        }
        return chars;
    }

    /**
     * 动态增加到拼音词典中
     *
     * @param word    大长今
     * @param pinyins ['da4', 'chang2' ,'jing1']
     */
    public static void insertPinyin(String word, String[] pinyins) {
        PINYIN_FOREST.addBranch(word, pinyins);
    }
}
