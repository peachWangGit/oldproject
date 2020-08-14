package com.txdata.contract.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExcelPayVo {
    //付款方式id
    private String id;
    //付款条件
    private String payCondition;
    //付款金额（期款金额）
    private BigDecimal payMoney;
}
