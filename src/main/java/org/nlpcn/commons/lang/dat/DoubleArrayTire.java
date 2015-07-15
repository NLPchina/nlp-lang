package org.nlpcn.commons.lang.dat;

import lombok.SneakyThrows;
import org.nlpcn.commons.lang.util.FileIterator;
import org.nlpcn.commons.lang.util.IOUtil;
import org.nlpcn.commons.lang.util.StringUtil;

import java.io.*;

/**
 * 双数组使用
 *
 * @author ansj
 */
public class DoubleArrayTire {

    private Item[] dat;

    public int arrayLength;

    private DoubleArrayTire() {
    }

    @SneakyThrows
    public static DoubleArrayTire load(final String filePath) {
        try (final ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            final DoubleArrayTire obj = new DoubleArrayTire();
            obj.dat = new Item[ois.readInt()];
            obj.arrayLength = ois.readInt();
            for (int i = 0; i < obj.arrayLength; i++) {
                final Item item = (Item) ois.readObject();
                obj.dat[item.index] = item;
            }
            return obj;
        }
    }

    /**
     * 从文本中加载模型
     */
    public static DoubleArrayTire loadText(String filePath, Class<? extends Item> cla) {
        return loadText(IOUtil.getInputStream(filePath), cla);
    }

    /**
     * 从文本中加载模型
     */
    @SneakyThrows
    public static DoubleArrayTire loadText(InputStream is, Class<? extends Item> cla) {
        final DoubleArrayTire obj = new DoubleArrayTire();
        final FileIterator it = IOUtil.instanceFileIterator(is, IOUtil.UTF8);
        if(it == null) {
            throw new FileNotFoundException();
        }
        String temp = it.next();
        obj.arrayLength = Integer.parseInt(temp);
        obj.dat = new Item[obj.arrayLength];
        while (it.hasNext()) {
            temp = it.next();
            final Item item = cla.newInstance();
            item.initValue(temp.split("\t"));
            obj.dat[item.index] = item;
        }
        return obj;
    }

    /**
     * 从文本中加载模型
     */
    public static DoubleArrayTire loadText(String filePath) {
        return loadText(filePath, BasicItem.class);
    }

    /**
     * 获得dat数组
     */
    public Item[] getDAT() {
        return dat;
    }

    /**
     * 一个词在词典中的id
     */
    public int getId(String str) {
        final Item item = getItem(str);
        return item != null ? item.index : 0;
    }

    /**
     * 获得一个词语的item
     */
    @SuppressWarnings("unchecked")
    public <T extends Item> T getItem(String str) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        if (str.length() == 1) {
            return (T) dat[str.charAt(0)];
        }

        Item item = dat[str.charAt(0)];
        if (item == null) {
            return null;
        }
        for (int i = 1; i < str.length(); i++) {
            final int checkValue = item.index;
            if (item.base + str.charAt(i) > dat.length - 1)
                return null;

            item = dat[item.base + str.charAt(i)];
            if (item == null) {
                return null;
            }
            if (item.check != -1 && item.check != checkValue) {
                return null;
            }
        }
        return (T) item;
    }

    @SuppressWarnings("unchecked")
    public <T extends Item> T getItem(int id) {
        return (T) dat[id];
    }
}
