package com.ysxsoft.common_base.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * create by Sincerly on 2019/5/10 0010
 **/
public class DimenTools {
    public static void gen() {
        //以此文件夹下的dimens.xml文件内容为初始值参照
        getResPath(new File("").getAbsolutePath());
        File file = new File(path+"\\values\\dimens.xml");
        BufferedReader reader = null;
        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder w820 = new StringBuilder();

        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                    sw240.append(start).append(num * 0.75).append(end).append("\r\n");
                    sw480.append(start).append(num * 1.5).append(end).append("\r\n");
                    sw600.append(start).append(num * 1.87).append(end).append("\r\n");
                    sw720.append(start).append(num * 2.25).append(end).append("\r\n");
                    sw800.append(start).append(num * 2.5).append(end).append("\r\n");
                    w820.append(start).append(num * 2.56).append(end).append("\r\n");
                } else {
                    sw240.append(tempString).append("");
                    sw480.append(tempString).append("");
                    sw600.append(tempString).append("");
                    sw720.append(tempString).append("");
                    sw800.append(tempString).append("");
                    w820.append(tempString).append("");
                }
                line++;
            }
            reader.close();
            System.out.println("<!--  sw240 -->");
            System.out.println(sw240);
            System.out.println("<!--  sw480 -->");
            System.out.println(sw480);
            System.out.println("<!--  sw600 -->");
            System.out.println(sw600);
            System.out.println("<!--  sw720 -->");
            System.out.println(sw720);
            System.out.println("<!--  sw800 -->");
            System.out.println(sw800);
            String sw240file = path+"\\values-sw240dp\\dimens.xml";
            String sw480file = path+"\\values-sw480dp\\dimens.xml";
            String sw600file = path+"\\values-sw600dp\\dimens.xml";
            String sw720file = path+"\\values-sw720dp\\dimens.xml";
            String sw800file = path+"\\values-sw800dp\\dimens.xml";
            String w820file = path+"\\values-w820dp\\dimens.xml";
            //将新的内容，写入到指定的文件中去
            writeFile(sw240file, sw240.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw720file, sw720.toString());
            writeFile(sw800file, sw800.toString());
            writeFile(w820file, w820.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入方法
     */

    public static void writeFile(String file, String text) {
        File f=new File(file);
        if(!f.exists()){
            try {
                File f2=f.getParentFile();
                if(!f2.exists()){
                    f2.mkdirs();
                }
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    public static void main(String[] args) {
        gen();
    }

    public static String path="";
    private static void getResPath(String filePath) {
        for (File file : new File(filePath).listFiles()) {
            if (file.isDirectory()) {
                // 递归
                //这里将列出所有的文件夹
                if (file.getAbsolutePath().contains("src") && file.getAbsolutePath().contains("res")&& file.getAbsolutePath().contains("common_base")) {
                    System.out.println("res路径：" + file.getAbsolutePath());
                    path=file.getAbsolutePath();
                    return;
                }
                getResPath(file.getAbsolutePath());
            }
        }
    }

}
