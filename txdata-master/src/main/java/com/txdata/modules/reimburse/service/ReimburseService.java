package com.txdata.modules.reimburse.service;

import com.txdata.common.utils.JSONToXLSXConverter;
import com.txdata.modules.reimburse.dao.ReimburseDAO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class ReimburseService {

    private final ReimburseDAO reimburseDAO;

    public ReimburseService(ReimburseDAO reimburseDAO) {
        this.reimburseDAO = reimburseDAO;
    }

    public Workbook generateReimburseReport() {
        return JSONToXLSXConverter.convert(reimburseDAO.generateReimburseReport());
    }
}
