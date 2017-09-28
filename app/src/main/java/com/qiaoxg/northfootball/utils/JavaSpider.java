package com.qiaoxg.northfootball.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by admin on 2017/8/25.
 */

public class JavaSpider {

    private static final String TAG = "JavaSpider";

    private static String mRequestProperty_title = "User-Agent";
    private static String mRequestProperty_userAgent = "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";

    /**
     * 将抓取的网页变成html文件，保存在本地
     *
     * @param url
     * @param name
     */
    public static void Save_Html(String url, String name) {
        try {
            File dest = new File(FileUtils.getHtmlTempDir() + "/" + name + ".html");
            Log.e(TAG, "Save_Html: dest is " + dest);
            //接收字节输入流
            InputStream is;
            //字节输出流
            FileOutputStream fos = new FileOutputStream(dest);

            URL temp = new URL(url);
            URLConnection uc = temp.openConnection();
            uc.addRequestProperty(mRequestProperty_title, mRequestProperty_userAgent);
            is = temp.openStream();

            //为字节输入流加缓冲
            BufferedInputStream bis = new BufferedInputStream(is);
            //为字节输出流加缓冲
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int length;

            byte[] bytes = new byte[1024 * 20];
            while ((length = bis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
            }

            bos.close();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Save_Html: openStream流错误，跳转get流");
            //如果上面的那种方法解析错误
            //那么就用下面这一种方法解析
            try {
                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)")
                        .timeout(3000)
                        .get();

                File dest = new File(FileUtils.getHtmlTempDir() + "/" + name + ".html");
                if (!dest.exists())
                    dest.createNewFile();
                FileOutputStream out = new FileOutputStream(dest, false);
                out.write(doc.toString().getBytes("utf-8"));
                out.close();

            } catch (IOException E) {
                E.printStackTrace();
                Log.e(TAG, "Save_Html: get流错误，请检查网址是否正确");
            }

        }
    }


    /**
     * 解析本地的html
     *
     * @param path
     */
    public static void Get_LocalHtml(String path) {
        //读取本地html的路径
        File file = new File(path);
        //生成一个数组用来存储这些路径下的文件名
        File[] array = file.listFiles();
        //写个循环读取这些文件的名字

        for (int i = 0; i < array.length; i++) {
            try {
                if (array[i].isFile()) {
                    //文件名字
                    Log.e(TAG, "Get_LocalHtml: 正在解析网址：" + array[i].getName());
                    //文件地址加文件名字
                    //System.out.println("#####" + array[i]);
                    //一样的文件地址加文件名字
                    //System.out.println("*****" + array[i].getPath());

                    //下面开始解析本地的html
                    Document doc = Jsoup.parse(array[i], "UTF-8");
                    //得到html的所有东西
                    Element content = doc.getElementById("content");
                    //分离出html下<a>...</a>之间的所有东西
                    Elements links = content.getElementsByTag("p");
                    //Elements links = doc.select("a[href]");
                    // 扩展名为.png的图片
                    Elements pngs = doc.select("img[src$=.png]");
                    // class等于masthead的div标签
                    Element masthead = doc.select("div.masthead").first();

                    for (Element link : links) {
                        //得到<a>...</a>里面的网址
                        String linkHref = link.attr("href");
                        //得到<a>...</a>里面的汉字
                        String linkText = link.text();
                        Log.e(TAG, "Get_LocalHtml: linkText is " + linkText);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Get_LocalHtml: 网址：" + array[i].getName() + "解析出错");
                Log.e(TAG, "Get_LocalHtml: error is " + e.getMessage() );
                e.printStackTrace();
                continue;
            }
        }
    }
}
