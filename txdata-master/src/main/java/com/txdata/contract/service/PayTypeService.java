package com.txdata.contract.service;

import com.txdata.common.utils.KeyUtil;
import com.txdata.common.utils.StringUtils;
import com.txdata.contract.dao.PayTypeMapper;
import com.txdata.contract.domain.PayType;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PayTypeService{
    @Autowired
    private PayTypeMapper payTypeMapper;

    /**
     * 逻辑删除
     * @param id payTypeId
     * @return
     */
    public int deleteByPrimaryKey(String id) {
        PayType payType = payTypeMapper.selectByPrimaryKey(id);
        payType.setDelFlag("1");//默认为0，改为1为删除状态
        return payTypeMapper.updateByPrimaryKey(payType);
    }

    /**
     * 有id为修改，无id为新增
     * @param payType
     * @return
     */

    public int save(PayType payType) {
        String id = payType.getId();//付款id
        String cid = payType.getCid();//回款ID
        if (StringUtils.isBlank(id)) {//新增
            payType.setId(KeyUtil.genUniqueKey());//若有多个付款，id也不一样
            payType.setCid(cid);//cid一致
            payType.setDelFlag("0");
            return payTypeMapper.insert(payType);//付款方式添加
        } else {//修改
            return payTypeMapper.updateByPrimaryKey(payType);
        }
    }
}
