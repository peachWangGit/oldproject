package com.txdata.contract.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelBackVo {
    //已收款项id
    private String id;
    //回款时间
    private Date paybackTime;
    //已收金额（回款金额）
    private BigDecimal receiveMoney;
}
