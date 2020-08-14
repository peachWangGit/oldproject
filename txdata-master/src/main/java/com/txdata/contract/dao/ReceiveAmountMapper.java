package com.txdata.contract.dao;

import com.txdata.contract.domain.ReceiveAmount;
import java.util.List;
/*合同回款信息--已收款项dao*/
public interface ReceiveAmountMapper {
    int deleteByPrimaryKey(String id);//物理删除

    int deleteByCid(String cid);//通过cid删除

    int insert(ReceiveAmount record);//新增

    ReceiveAmount selectByPrimaryKey(String id);//通过主键查

    List<ReceiveAmount> selectAll();//查所有

    int updateByPrimaryKey(ReceiveAmount record);//更新

    List<ReceiveAmount> selectByCid(String id);//通过cid查询回款集合
}