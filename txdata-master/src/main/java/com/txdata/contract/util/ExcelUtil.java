package com.txdata.contract.util;
import com.txdata.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import java.math.BigDecimal;
import java.util.Map;
public class ExcelUtil {

    /**
     * 导出Excel
     *     * @param sheetName   sheet名称
     * <p>
     *     * @param title    标题
     * <p>
     *     * @param content 内容
     * <p>
     *     * @param wb  HSSFWorkbook对象
     * <p>
     *    
     */

    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] content, HSSFWorkbook wb, Map<Integer,Integer> map,Map<String, BigDecimal> moneyMap) {
        //第一步 创建一个HSSFWorkbook, 对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        //第二步, 在workbook中添加一个sheet,对应excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        //第三步,在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);
        //第四步,创建单元格,并设置表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle styleBottom = wb.createCellStyle();//最下面计算总计的几个单元格，需要重新设计

        // 设置单元格的边框为细线
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);

        //合并的单元格样式
        HSSFCellStyle boderStyle = wb.createCellStyle();
        //垂直居中
        boderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        boderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        //设置一个边框
        boderStyle.setBorderTop(HSSFBorderFormatting.BORDER_THICK);

        //为通用单元格创建一个居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        //为最下面的单元格创建一个居中格式
        styleBottom.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        styleBottom.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        //设置字体
        HSSFFont font = wb.createFont();
        //设置为楷体
        font.setFontName("楷体");
       //创建边框对象
        HSSFCellStyle setBorder = wb.createCellStyle();
        //设置自动换行
        setBorder.setWrapText(true);
        //声明列对象
        HSSFCell cell;
        //创建标题
        int maxWidth = 0;//最大宽度
        for (int i = 0; i < title.length; i++) {
            if (maxWidth<title[i].length()){
                maxWidth = title[i].length();
            }
            sheet.setColumnWidth(i, 256*maxWidth+256*7);
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //创建和插入内容
        int cNum = map.size();//合同数
        int rowNum = content.length;//总行数
        for (int i = 0; i < rowNum; i++) {//往每一行插值
            row = sheet.createRow(i + 1);
            for (int j = 0; j <12; j++) {//往每一列的单元格插值
                if (StringUtils.isBlank(content[i][j])) {
                    content[i][j] = "/";
                }
                if (maxWidth < content[i][j].length()) {
                    maxWidth = content[i][j].length();
                }
                sheet.setColumnWidth(i, 256*maxWidth+256*7);
                //将内容按照顺序赋给对应的列对象
                cell = row.createCell(j);
                cell.setCellValue(content[i][j]);
                cell.setCellStyle(style);
            }

        }
        //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)  此方法合并了主表
        // 行和列都是从0开始计数，且起始结束都会合并
        int  start=0;//开始
        int  end=0;
        for (int i = 0; i <map.size();  i++) {//遍历合同
            int times = map.get(i);//每个合同里面最大行数
            end =end+ map.get(i);//结束
            for (int j = 0; j < 8 ; j++) {
                CellRangeAddress region = new CellRangeAddress(start+1, end, j, j);
                sheet.addMergedRegion(region);
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
                style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
                // 设置单元格的边框为细线
                style.setBorderBottom(CellStyle.BORDER_THIN);
                style.setBorderTop(CellStyle.BORDER_THIN);
                style.setBorderLeft(CellStyle.BORDER_THIN);
                style.setBorderRight(CellStyle.BORDER_THIN);
                }
            start = end ;
            }
        //合并单元格(4个参数，分别为起始行，结束行，起始列，结束列)  此方法合并了从表重复的斜线

        //最后一行合计的三个单元格赋值。
        row = sheet.createRow(end + 1);
        cell = row.createCell(6);
        cell.setCellValue("合计");
        cell.setCellStyle(styleBottom);
        cell = row.createCell(7);
        cell.setCellValue(String.valueOf(moneyMap.get("payMoney")));
        cell.setCellStyle(styleBottom);
        cell = row.createCell(11);
        cell.setCellValue(String.valueOf(moneyMap.get("receiveMoney")));
        cell.setCellStyle(styleBottom);
        return wb;
    }

}

