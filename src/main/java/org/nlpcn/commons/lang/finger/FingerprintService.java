package org.nlpcn.commons.lang.finger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.nlpcn.commons.lang.dic.DicManager;
import org.nlpcn.commons.lang.finger.pojo.MyFingerprint;
import org.nlpcn.commons.lang.tire.GetWord;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.library.Library;
import org.nlpcn.commons.lang.util.MD5;
import org.nlpcn.commons.lang.util.StringUtil;

public class FingerprintService {

    private static Forest forest = null;
    static {
        try {
            forest = Library.makeForest(DicManager.class.getResourceAsStream("/finger.dic"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据一个 文章的正文.计算文章的指纹
     * 
     * @param content
     * @return
     */
    public String fingerprint(String content) {

        content = StringUtil.rmHtmlTag(content);

        GetWord word = new GetWord(forest, content);

        String temp = null;

        int middleLength = content.length() / 2;

        double factory;

        HashMap<String, MyFingerprint> hm = new HashMap<String, MyFingerprint>();

        MyFingerprint myFingerprint = null;
        while ((temp = word.getFrontWords()) != null) {
            if (temp != null && temp.length() == 0) {
                continue;
            }
            temp = temp.toLowerCase();
            factory = 1 - (Math.abs(middleLength - word.offe) / (double) middleLength);
            if ((myFingerprint = hm.get(temp)) != null) {
                myFingerprint.updateScore(factory);
            } else {
                hm.put(temp, new MyFingerprint(temp, Double.parseDouble(word.getParam(1)), factory));
            }
        }

        Set<MyFingerprint> set = new TreeSet<MyFingerprint>();
        set.addAll(hm.values());

        int size = Math.min(set.size() / 10, 4) + 1;

        Iterator<MyFingerprint> iterator = set.iterator();
        int j = 0;
        HashSet<String> hs = new HashSet<String>();
        for (; j < size && iterator.hasNext(); j++) {
            myFingerprint = iterator.next();
            hs.add(myFingerprint.getName() + " ");
        }
        String finger = MD5.code(hs.toString());

        return finger;
    }

    public static void main(String[] args) {
        String content = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇原标题：卓尔防线继续伤筋动骨队长梅方出场再补漏稿源：中新网作者：";
        String content2 = "卓尔防线继续伤筋动骨 队长梅方出场再补漏说起来卓尔队长梅方本赛季就是个“补漏”的命！在中卫与右边后卫间不停地轮换。如果不出意外，今天与广州恒大一战梅方又要换位置，这也是汉军队长连续三场比赛中的第三次换位。而从梅方的身上也可以看出，本赛季汉军防线如此“折腾”，丢球多也不奇怪了。梅方自2009赛季中乙出道便一直司职中后卫，还曾入选过布拉泽维奇国奥队，也是司职的中卫。上赛季，梅方与忻峰搭档双中卫帮助武汉卓尔队中超成功，但谁知进入本赛季后从第一场比赛开始梅方便不断因为种种“意外”而居无定所。联赛首战江苏舜天时，也是由于登贝莱受伤，朱挺位置前移，梅方临危受命客串右边后卫。第二轮主场与北京国安之战梅方仅仅打了一场中卫，又因为柯钊受罚停赛4轮而不得不再次到边路“补漏”。随着马丁诺维奇被弃用，梅方一度成为中卫首选，在与上海东亚队比赛中，邱添一停赛，梅方与忻峰再度携手，紧接着与申鑫队比赛中移至边路，本轮忻峰又停赛，梅方和邱添一成为中卫线上最后的选择。至于左右边后卫位置，卓尔队方面人选较多，罗毅、周恒、刘尚坤等人均可出战。记者马万勇";

        System.out.println(new FingerprintService().fingerprint(content));
        System.out.println(new FingerprintService().fingerprint(content2));
    }
}
