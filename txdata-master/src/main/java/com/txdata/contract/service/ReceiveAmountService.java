package com.txdata.contract.service;

import com.txdata.common.utils.KeyUtil;
import com.txdata.common.utils.StringUtils;
import com.txdata.contract.dao.ReceiveAmountMapper;
import com.txdata.contract.domain.PayType;
import com.txdata.contract.domain.ReceiveAmount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReceiveAmountService{
    @Autowired
    private ReceiveAmountMapper receiveAmountMapper;

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(String id) {
        ReceiveAmount receiveAmount = receiveAmountMapper.selectByPrimaryKey(id);
        receiveAmount.setDelFlag("1");//设置删除状态为1
        return receiveAmountMapper.updateByPrimaryKey(receiveAmount);
    }

    /**
     * 有id为修改，无id为新增
     * @param receiveAmount
     * @return
     */
    public int save(ReceiveAmount receiveAmount) {
        String id = receiveAmount.getId();
        String cid = receiveAmount.getCid();
        if (StringUtils.isBlank(id)) {//新增
            receiveAmount.setId(KeyUtil.genUniqueKey());//若有多个付款，id也不一样
            receiveAmount.setCid(cid);//cid一致
            receiveAmount.setDelFlag("0");
            return receiveAmountMapper.insert(receiveAmount);//付款方式添加
        } else {//修改
            return receiveAmountMapper.updateByPrimaryKey(receiveAmount);
        }
    }
}
