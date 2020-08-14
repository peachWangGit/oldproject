package com.txdata.modules.reimburse.controller;

import com.txdata.common.utils.DateUtils;
import com.txdata.common.utils.XLSXView;
import com.txdata.modules.reimburse.service.ReimburseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/reimburse")
public class ReimburseController {

    private final ReimburseService reimburseService;

    public ReimburseController(ReimburseService reimburseService) {
        this.reimburseService = reimburseService;
    }

    @GetMapping("/generateReimburseReport")
    public XLSXView generateReimburseReport() throws UnsupportedEncodingException {
        String filename = "报销费用明细表_" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
        return new XLSXView(reimburseService.generateReimburseReport(), filename);
    }
}
