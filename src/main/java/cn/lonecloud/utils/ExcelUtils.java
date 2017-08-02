package cn.lonecloud.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lonecloud on 17/6/11.
 * @author lonecloud
 * Excel的工具类
 */
public class ExcelUtils {
    /**
     * 生成Excel的工具类
     * @param datas 数据集
     * @param titleDatas 标题栏
     * @param filePath 导出的文件路径
     * @param excludeFields 排除的字段名
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static String generatorExcel(List datas,String[] titleDatas,String filePath,String...excludeFields) throws IOException, IllegalAccessException {
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        setTitle(sheet.createRow(0),titleDatas);
        for (int i = 1; i < datas.size(); i++) {
            //创建HSSFRow对象
            HSSFRow row = sheet.createRow(i);
            covernData(row,datas.get(i),excludeFields);
        }
        //输出Excel文件
        FileOutputStream output = new FileOutputStream(filePath);
        wb.write(output);
        output.flush();
        return filePath;
    }

    /**
     * 设置头部信息
     * @param row
     * @param titleDatas
     */
    private static void setTitle(HSSFRow row,String[] titleDatas){
        for (int i = 0; i < titleDatas.length; i++) {
            row.createCell(i).setCellValue(titleDatas[i]);
        }
    }

    /**
     * 将数据转换row中
     * @param row
     * @param obj
     * @throws IllegalAccessException
     */
    private static void covernData(HSSFRow row,Object obj,String... excludeFields) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean isNullField=excludeFields!=null&&excludeFields.length!=0;
        int i=0;
        HSSFCell cell=null;
        for (Field field:fields) {
            String name = field.getName();
            //判断该name是不是在excludeFields里面
            if (ArraysUtils.isContains(name,excludeFields)){
                continue;
            }
            //设置访问权限
            field.setAccessible(true);
            if (field.get(obj)!=null){
                //不为空则设置值
                cell= row.createCell(i++);
                cell.setCellValue(field.get(obj).toString());
            }else{
                //如果是没有数据则设置为空字符串
                cell= row.createCell(i++);
                cell.setCellValue("-");
            }
        }
    }

}
