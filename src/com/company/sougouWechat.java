package com.company;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class sougouWechat {
    private String tittle;
    private String url;

    public sougouWechat() {
    }
    public sougouWechat(String tittle, String url) {
        this.tittle = tittle;
        this.url = url;
    }
    //获取
    public String getArticle(){
        Document htmlDocument = getHtmlPage.getHtml(url);
        Elements link = htmlDocument.select(".rich_media_content#js_content p");
        StringBuffer buffer = new StringBuffer();
        for (Element element : link) {
            buffer.append(element.text());
            buffer.append("\n");
        }
        return buffer.toString();
    }


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}