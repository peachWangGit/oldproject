package com.txdata.modules.reimburse.dao;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReimburseDAO {

    JSONArray generateReimburseReport();
}
