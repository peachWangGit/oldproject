package com.txdata.contract.dao;

import com.txdata.contract.domain.OANotifyRecord;
import java.util.List;

public interface OANotifyRecordMapper {
    int deleteByPrimaryKey(String id);//通过主键删除

    int insert(OANotifyRecord oaNotifyRecord);//新增

    OANotifyRecord selectByPrimaryKey(String id);//通过主键查询

    List<OANotifyRecord> selectAll();//查询所有

    int updateByPrimaryKey(OANotifyRecord oaNotifyRecord);//更新

    //通过通知id和userID，可以查到唯一对应的 通知通告发送记录
    OANotifyRecord selectByNotifyIdAndUserId(String notifyId,String userId);
}