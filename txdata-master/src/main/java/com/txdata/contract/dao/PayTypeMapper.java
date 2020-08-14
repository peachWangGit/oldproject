package com.txdata.contract.dao;

import com.txdata.contract.domain.PayType;
import java.util.List;
/*合同登记信息--付款方式dao*/
public interface PayTypeMapper {
    int deleteByPrimaryKey(String id);//主键物理删除

    int deleteByCid(String cid);//通过cid删除

    int insert(PayType record);//新增付款方式

    PayType selectByPrimaryKey(String id);//通过主键查询

    List<PayType> selectByCid(String cid);//通过cid查询付款记录集合

    List<PayType> selectAll();//查询所有

    int updateByPrimaryKey(PayType record);//通过主键修改
}