package com.txdata.contract.controller;

import com.txdata.common.utils.R;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import com.txdata.contract.service.PayTypeService;
import com.txdata.contract.service.ReceiveAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/receiveAmount")
public class ReceiveAmountController {
    @Autowired
    ReceiveAmountService receiveAmountService;

    /**
     * 付款方式新增与修改
     * 有id为修改，无id为新增
     * @param receiveAmount
     * @return
     */
    @PostMapping("/save")
    public R save(ReceiveAmount receiveAmount) {
        if (receiveAmountService.save(receiveAmount) > 0) {
            return R.success();
        }
        return R.error();

    }


    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @PostMapping("/remove")
    public R remove(String id) {
        if (receiveAmountService.deleteByPrimaryKey(id) > 0) {
            return R.success();
        }
        return R.error();
    }
}
