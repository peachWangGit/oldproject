package com.txdata.contract.controller;
import com.txdata.common.utils.R;
import com.txdata.contract.service.PoiLoadService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/poi")
public class PoiLoadController {
    @Autowired
    private PoiLoadService poiLoadService;
    @RequestMapping("/excel")
    public R getContractExcel(HttpServletResponse response, HttpSession session){
        HSSFWorkbook hssfWorkbook = poiLoadService.selectDetil(response);
        session.setAttribute("hssfWorkbook",hssfWorkbook);
        return R.success();
    }

}
