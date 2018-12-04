package com.github.vole.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

/**
 * JDK deflate ——这是JDK中的又一个算法（zip文件用的就是这一算法）。
 * 它与gzip的不同之处在于，你可以指定算法的压缩级别，这样你可以在压缩时间和输出文件大小上进行平衡。
 * 可选的级别有0（不压缩），以及1(快速压缩)到9（慢速压缩）。它的实现是java.util.zip.DeflaterOutputStream / InflaterInputStream
 */
public class ZIPCompress {

    /**
     * 压缩
     * @param str 原始字符串
     * @return
     */
    public static String compress(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String res = null;
        try {
            // 压缩后写到out流中
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // m_lvl:   {"1", "2", "3", "4", "5", "6", "7", "8", "9"}
            int m_lvl = 6;
            final Deflater deflater = new Deflater( m_lvl, true );
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out, deflater, true);
            System.out.println("before compress:" + str.getBytes().length);
            // 待压缩数据为str字符串得到的字节数组
            deflaterOutputStream.write(str.getBytes());
            //必须要关闭，让最后一次未缓冲满的数据也写入。
            deflaterOutputStream.close();
            byte[] compressed = out.toByteArray();
            out.close();
            System.out.println("after compress:" + compressed.length);
//            System.out.println("after compress:" + compressed);
            res = new String(compressed, "ISO-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 解压缩
     * @param compressed 压缩字符串
     * @return
     * @throws IOException
     */
    public static String  deCompress(String compressed) {
//        System.out.println("before decompress:" + compressed);
        if (StringUtils.isBlank(compressed)) {
            return null;
        }
        String decompressed = null;
        try {
            // 压缩后写到out流中
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Inflater inf = new Inflater(true);
            InflaterOutputStream inflaterOutputStream=new InflaterOutputStream(out, inf);
            inflaterOutputStream.write(compressed.getBytes("ISO-8859-1"));
            inflaterOutputStream.close();
//        System.out.println("after decompress:" + out.toByteArray().length);
            decompressed =new String(out.toByteArray()) ;
//        System.out.println(decompressed.substring(0, 7));
//        System.out.println(decompressed);
        } catch (IOException e) {
//            LOGGER.error("解压缩失败", e.getMessage());
            e.printStackTrace();
            // 返回原来的字符串
            decompressed = compressed;
        }
        return decompressed;
    }

    /*public static void  main(String[] args) throws IOException{
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++)
            sb.append("abcdefgni你好#￥%{'\"123\"'}");
        String str = sb.toString();
        deCompress(compress(str).toString());
    }*/
}
