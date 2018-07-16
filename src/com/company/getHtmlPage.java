package com.company;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getHtmlPage {

    //获取网页
    public static Document getHtml(String url) {
        try {
            @SuppressWarnings("resource")
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(10000);
            String htmlString = page.asXml();
            return Jsoup.parse(htmlString);

        } catch (Exception e) {
            // TODO: handle exception
            //e.printStackTrace();
        }
        return null;
    }
    public static String getQueryCode(String query){
        try {
            @SuppressWarnings("resource")
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            HtmlPage page = webClient.getPage("http://weixin.sogou.com");
            webClient.waitForBackgroundJavaScript(10000);
            final HtmlForm form = page.getFormByName("searchForm");
            final HtmlTextInput textInput = form.getInputByName("query");
            textInput.setValueAttribute(query);
            final HtmlButtonInput button = form.getInputByValue("搜文章");
            final HtmlPage nextPage = button.click();
            String htmlString = nextPage.asXml();
            Document html = Jsoup.parse(htmlString);
            Element link = html.select("#sogou_page_2").first();
            Matcher matcher = Pattern.compile("%E.+?&").matcher(link.toString());
            String string = "";
            while(matcher.find())
                string = matcher.group().substring(0,matcher.group().length()-1);
            return string;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return null;
    }
}
