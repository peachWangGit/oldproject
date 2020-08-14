package com.txdata.contract.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.txdata.common.persistence.proxy.CrudDao;
import com.txdata.contract.domain.ContractRegInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*合同登记信息--基本信息dao*/
public interface ContractRegInfoMapper extends CrudDao<ContractRegInfo> {
    int deleteByPrimaryKey(String cid);//通过主键删除

//    Page<ContractRegInfo> list(Page<ContractRegInfo> page, @Param("entity") Map<String,Object> map);

    int insert(ContractRegInfo contractRegInfo);//合同增加

    ContractRegInfo selectByPrimaryKey(String id);//通过主键查询

    com.github.pagehelper.Page<ContractRegInfo> selectAll();//查询所有

    int updateByPrimaryKey(ContractRegInfo contractRegInfo);//跟新

    String selectRoleByUserId   (String userId);//通过userID查角色

    ContractRegInfo selectContractByInstanceId(String instanceId);//通过流程实例id查合同

    String selectInstanceIdByContractId(String contractId);//通过合同id查流程实例id
}