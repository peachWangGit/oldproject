package com.txdata.contract.controller;
import com.txdata.activiti.service.ProcessService;
import com.txdata.common.utils.R;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/process")
@Controller
public class ProcessEngineController {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessService processService;
    @ResponseBody
    @PostMapping("/start")
    public R ProcessStart() {
        //部署流程定义
        String name="合同审批流程";//流程的名称
        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name(name)//声明流程的名称
                .addClasspathResource("processes/diagram.bpmn")//加载资源文件，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());
        Map<String, Object> map = new HashMap<>();
        map.put("部署ID",deployment.getId());
        map.put("部署名",deployment.getName());
        map.put("部署时间",deployment.getDeploymentTime());
        return R.success(map);


    }
    @ResponseBody
    @PostMapping("/delete")
    public R deleteDeploy(){
        Deployment deployment = repositoryService.createDeploymentQuery().singleResult();
        if (StringUtils.isEmpty(deployment)) {
            return R.error("当前无流程，不用删");
        }
        String id = deployment.getId();
        System.out.println(id);
        repositoryService.deleteDeployment(deployment.getId(), true);
        return R.success(deployment.getName() + "已删除成功");
    }

}
