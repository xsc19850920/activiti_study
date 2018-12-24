package com.genpact.activiti.test.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import com.genpact.activiti.entity.LeaveBill;
import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.UserService;
import com.genpact.activiti.test.base.BaseTest;
import com.genpact.activiti.utils.Constant;

public class ServiceTest extends BaseTest {
	@Autowired private UserService userService;
	
	@Test
	public void initDB(){
		User wg = new User("wg", "123", null);
		User cg = new User("cg", "123", wg);
		User sxia = new User("sxia", "123", cg);
		
		userService.createUser(wg);
		userService.createUser(cg);
		userService.createUser(sxia);
	}
	
	@Test
	public void view(){
		String taskId = "9";
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		
		//get ProcessDefinitionEntity info （*.bpmn 里面的信息）
		ProcessDefinitionEntity processDefinitionEntity =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).singleResult();
		
		//获取流程实例中的当前活动任务的节点
		String activityId = processInstance.getActivityId();
		//获取当前活动的任务节点对象
		ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
		//获得所有从当前节点出去的连线
		List<String> pvmTransitionNameList = new ArrayList<String>();
		List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			pvmTransitionNameList.add(pvmTransition.getProperty("name").toString());
		}
	}
}
