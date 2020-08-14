package com.txdata.contract.controller;

import com.txdata.common.utils.R;
import com.txdata.contract.domain.ContractRegInfo;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.service.ContractRegInfoService;
import com.txdata.contract.service.PayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/payType")
public class PayTypeController {
    @Autowired
    PayTypeService payTypeService;

    /**
     * 付款方式新增与修改
     * 有id为修改，无id为新增
     * @param payType
     * @return
     */
    @PostMapping("/save")
    public R save(PayType payType) {
        if (payTypeService.save(payType) > 0) {
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
        if (payTypeService.deleteByPrimaryKey(id) > 0) {
            return R.success();
        }
        return R.error();
    }
}
