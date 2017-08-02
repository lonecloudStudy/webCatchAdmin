package cn.lonecloud.tools;

import cn.lonecloud.bean.CompanyInfo;
import cn.lonecloud.tools.itf.AbstractWebReptile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于对黄页88页面抓取用户工具http://b2b.huangye88.com/beijing/shenghuo/
 *
 * @author lonecloud
 */
public class CatchPageTool extends AbstractWebReptile<CompanyInfo> {

    @Override
    protected List<CompanyInfo> analyzeHTMLByString(String html) {
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
    private CompanyInfo getContactData(String url) {
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
                    if (child.children().size()>1&&child.child(1) != null){
                        info.setContactName(child.child(1).html());
                        continue;
                    }
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
                    } else {
                        sb.append(majors[i]).append(",");
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                majorStr = sb.toString();
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
    private boolean isNotNullElems(Elements elements) {
        return elements != null && elements.size() > 0;
    }

    /**
     * 截取字符串
     *
     * @param src
     * @param regex
     * @return
     */
    private String subExcludeString(String src, String regex) {
        return src.substring(src.lastIndexOf(regex) + 1);
    }

    private String subExcludeString(String src, String begin, String end) {
        return src.substring(src.indexOf(">") + 1, src.lastIndexOf("<"));
    }

}
