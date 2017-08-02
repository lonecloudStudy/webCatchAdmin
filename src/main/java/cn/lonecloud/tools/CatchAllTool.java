package cn.lonecloud.tools;

import cn.lonecloud.bean.CompanyInfo;
import cn.lonecloud.bean.UserData;
import cn.lonecloud.cfg.CompanyInfoContants;
import cn.lonecloud.dao.UserDataMapper;
import cn.lonecloud.utils.ExcelUtils;
import cn.lonecloud.utils.FilePathUtils;
import cn.lonecloud.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lonecloud on 17/6/14.
 */
public class CatchAllTool implements Runnable {

    //网址
    private String url;
    //抓取次数默认100
    private int size=100;

    private final CatchPageTool catchPageTool = new CatchPageTool();
    //判断是否结束
    private boolean isSuccess;
    //进度
    private int present =0;
    //数据总集
    private List<CompanyInfo> companyInfos = new ArrayList<>();

    private String path;

    /**
     * 返回数据集
     * @return
     */
    public List<CompanyInfo> getAllData() {
        return companyInfos;
    }

    public CatchAllTool(String url) {
        this.url = url;
    }

    public CatchAllTool(String url, int size) {
        this.url = url;
        this.size = size;
    }

    @Override
    public void run() {
        //返回公司信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
        for (int i = 1; i <= size; i++) {
            String pnUrl=getUrl()+"/pn"+i;
            try {
                //抓取数据
                companyInfos.addAll(catchPageTool.catchWebByUrl(pnUrl));
            }catch (Exception e){
                e.printStackTrace();
            }
            //计算进度
            present =i/size;
        }
        //将数据转换成文件
        try {
            path = ExcelUtils.generatorExcel(companyInfos, new String[]{"公司名称", "联系人名称", "电话号码", "主营"}, FilePathUtils.getTmpPath() + sdf.format(new Date()), CompanyInfoContants.URL, CompanyInfoContants.WEBSITE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //存储文件
        //RequestUtils.getRequest().getSession().setAttribute("path", path);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
