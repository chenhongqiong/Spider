package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Article {

    public static File dir = new File( "f:\\Articles\\" );
    //获取界面中的文章标题
    public static List<sougouWechat> getArctileInfo(String query, int page) {
        query = getHtmlPage.getQueryCode(query);//获取查询码
        List<sougouWechat> sougouWechartList = new ArrayList<>();
        //获取文章标题和连接
        for (int i = 1; i <= page; i++) {
            //定义查询链接
            String url = "http://weixin.sogou.com/weixin?query=" + query + "&_sug_type_=&s_from=input&_sug_=y&type=2&page=" + i + "&ie=utf8";
            Document document = getHtmlPage.getHtml(url); //得到解析的页面内容
            Elements links = document.select(".txt-box h3 a");//选择class 为txt-box下字号为h3中标签a里的内容
            for (Element link : links) {
                sougouWechartList.add(new sougouWechat(link.text(), link.attr("href")));//将获得的标题和保存到List表中
            }
        }
        return sougouWechartList;


    }

    //获取文章
    public static void GetArticle(String query,int page){
        List<sougouWechat> sougouWechats = getArctileInfo(query,page);
        //创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (sougouWechat sougouWechat : sougouWechats) {
            String article = sougouWechat.getArticle().replaceAll(" ","");
            String title = sougouWechat.getTittle();
            String reg = "[^\u4e00-\u9fa5or\\d]";
            title = title.replaceAll(reg, "").trim();
            File file = new File(dir,title + ".txt");
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                }
            try {
                fileWriter.write("题目：" + title + "\n内容:\n" + article);
            } catch (IOException e) {

            }
            try {
                fileWriter.close();
            } catch (IOException e) {

            }
            System.out.println("创建");
        }
        System.out.println("创建成功");
    }


    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String string = new String(in.next());
        GetArticle(string, 1);

    }
}
