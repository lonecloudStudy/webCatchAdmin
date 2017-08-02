package cn.lonecloud.bean;

/**
 * Created by lonecloud on 17/6/11.
 */
public class CompanyInfo {

    private String companyName;

    private String contactName;

    private String tel;

    private String url;

    private String webSite;

    private String major;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "CompanyInfo{" +
                "companyName='" + companyName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", tel='" + tel + '\'' +
                ", url='" + url + '\'' +
                ", webSite='" + webSite + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
