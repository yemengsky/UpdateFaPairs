package com.yemeng;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yemeng on 16/6/23.
 */
public class UpdateFaPairs {

    public static void main(String[] argv) throws IOException {
        Map<String, String> picMap = new HashMap<>();
        String url = "http://fontawesome.io/cheatsheet/";
        Document doc = Jsoup.connect(url).get();
        Elements picElements = doc.select("div.col-md-4.col-sm-6.col-lg-3");
        System.out.println(picElements.size());
        for (Element element : picElements) {
            if (!element.ownText().isEmpty()) {
                picMap.put(element.ownText(), string2Unicode(element.getElementsByTag("i").get(0).ownText()));
                System.out.println(element.ownText() + "---->" + string2Unicode(element.getElementsByTag("i").get(0).ownText()));
            }
        }
        saveToFile("pic.xml", picMap);
    }

    private static void saveToFile(String fileName, Map<String, String> pairs) throws IOException {

        File file = new File(fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        //use FileWriter to write file
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), false);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write("<resources>\n");
        for (String key : pairs.keySet()) {
            bw.write("    <string name=\"" + key + "\">" + pairs.get(key) + "</string>\n");
        }

        bw.write("    <string name=\"pic_version\">" + System.currentTimeMillis() + "</string>\n");
        bw.write("</resources>");

        bw.close();
        System.out.println("Done");
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("&#x" + Integer.toHexString(c));
        }

        return unicode.toString();
    }
}
