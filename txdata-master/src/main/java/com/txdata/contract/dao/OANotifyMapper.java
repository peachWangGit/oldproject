package com.txdata.contract.dao;

import com.txdata.contract.domain.OANotify;
import java.util.List;

/**
 *通知通告
 */
public interface OANotifyMapper {

    int deleteByPrimaryKey(String id);//删除

    int insert(OANotify record);//插入

    OANotify selectByPrimaryKey(String id);//通过主键查

    List<OANotify> selectAll();//查所有

    int updateByPrimaryKey(OANotify record);//更新

    String selectIdByInstanceId(String instanceId);//通过流程实例id查询通知id
}