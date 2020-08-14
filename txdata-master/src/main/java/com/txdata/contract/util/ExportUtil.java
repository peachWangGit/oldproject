package com.txdata.contract.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * excel导出
 */
public class ExportUtil {

    public static void exportExcel(HttpServletResponse response, String fileName, HSSFWorkbook wb) throws Exception {
        //下面三行是关键代码，处理乱码问题
        response.setContentType("application/x-download");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;" +
                "filename="+new String(fileName.getBytes("gbk"),
                "iso8859-1")+".xls");

        response.addHeader("Pargam", "no-cache");//清页面缓存，提高性能
        response.addHeader("Cache-Control", "no-cache");//单独使用无效
        OutputStream os = response.getOutputStream();
        wb.write(os);
        os.flush();
        os.close();
    }
}

