package cn.lonecloud.utils;

import cn.lonecloud.bean.CompanyInfo;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lonecloud on 17/6/11.
 *
 * @author lonecloud
 *         用于对黄页88页面抓取用户工具http://b2b.huangye88.com/beijing/shenghuo/
 */
@Deprecated
public class WaperUtils {

    private WaperUtils() {

    }

    /**
     * 抓取网页数据
     *
     * @param url
     */
    public static List<CompanyInfo> catchWebByUrl(String url) {
        String html = pickData(url);
        return analyzeHTMLByString(html);
    }

    /**
     * 爬取网页信息
     *
     * @param url
     * @return
     */
    private static String pickData(String url) {
        //我们可以使用一个Builder来设置UA字段，然后再创建HttpClient对象
        HttpClientBuilder builder = HttpClients.custom();
        //对照UA字串的标准格式理解一下每部分的意思
        builder.setUserAgent("Mozilla/5.0(Windows;U;Windows NT 5.1;en-US;rv:0.9.4)");
        CloseableHttpClient httpClient = builder.build();
        try {
            HttpGet httpget = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析第一层页面数据
     *
     * @param html
     * @return
     */
    private static List<CompanyInfo> analyzeHTMLByString(String html) {
        List<CompanyInfo> companyInfoList = new ArrayList<>(10);
        Document document = Jsoup.parse(html);
        //获取form
        Element form = document.getElementById("jubao");
        Elements dts = form.getElementsByTag("dt");

        for (int i = 0; i < dts.size(); i++) {
            Element dt = dts.get(i);
            //获取号码区域
            Elements eleTel = dt.getElementsByAttributeValue("itemprop", "tel");
            if (eleTel.size() != 0) {
                Element element = eleTel.get(0);
                Elements a = element.getElementsByTag("a");
                if (a.size() != 0) {
                    //获取号码区域
                    String html1 = a.get(0).html();
                    //获取每个公司的网址
                    String contactUrl = a.attr("href");
                    CompanyInfo companyInfo = getContactData(contactUrl);
                    if (companyInfo != null) {
                        companyInfoList.add(companyInfo);
                    }
                    System.out.println(html1);
                }
            }
        }
        return companyInfoList;
    }

    /**
     * 解析公司联系人数据
     *
     * @param url
     * @return
     */
    private static CompanyInfo getContactData(String url) {
        CompanyInfo info = new CompanyInfo();
        //设置抓取页面
        //info.setUrl(url);
        //再次pickData联系人页面
        String contactHtml = pickData(url);
        Document document = Jsoup.parse(contactHtml);
        //获取信息
        Elements contact = document.getElementsByClass("con-txt");
        if (isNotNullElems(contact)) {
            Element root = contact.get(0);
            for (Element child : root.children()) {
                String content = child.html();
                if (content.contains("联系人")) {
                    //联系人
                    info.setContactName(child.child(1).html());
                    continue;
                }
                if (content.contains("手机")) {
                    //电话号码
                    info.setTel(subExcludeString(child.html(), ">"));
                    continue;
                }
                if (content.contains("公司名称")) {
                    //公司名称
                    info.setCompanyName(subExcludeString(child.html(), ">"));
                    continue;
                }
//                if (content.contains("公司主页")) {
//                    //公司网址
//                    info.setWebSite(child.child(1).html());
//                    continue;
//                }

            }

        } else {
            return null;
        }
        //获取主营项目
        Elements majorElem = document.getElementsByClass("contro-num");
        if (isNotNullElems(majorElem)) {
            String majorStr = subExcludeString(majorElem.get(0).html(), "：");
            //包含a标签
            if (majorStr.contains("<a")) {
                String[] majors = majorStr.split(",");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < majors.length; i++) {
                    if (majors[i].contains("<a")) {
                        sb.append(subExcludeString(majors[i], ">", "<")).append(",");
                    }else{
                        sb.append(majors[i]).append(",");
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                majorStr=sb.toString();
            }
            info.setMajor(majorStr);
        }
        System.out.println(info.toString());
        return info;
    }

    /**
     * 判断页面中的Elements是不是空
     *
     * @param elements
     * @return
     */
    private static boolean isNotNullElems(Elements elements) {
        return elements != null && elements.size() > 0;
    }

    /**
     * 截取字符串
     *
     * @param src
     * @param regex
     * @return
     */
    private static String subExcludeString(String src, String regex) {
        return src.substring(src.lastIndexOf(regex) + 1);
    }

    private static String subExcludeString(String src, String begin, String end) {
        return src.substring(src.indexOf(">") + 1, src.lastIndexOf("<"));
    }
}
