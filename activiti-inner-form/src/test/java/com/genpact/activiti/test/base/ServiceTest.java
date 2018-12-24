package com.genpact.activiti.test.base;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Test;

public class ServiceTest extends BaseTest {

	@Test
	@Deployment(resources = { "diagram/leave.bpmn", "diagram/leave.png" })
	public void deployment() {

		long count = repositoryService.createProcessDefinitionQuery().count();
		assertEquals(1, count);
		// 设置当前登录的入户
		String authenticatedUserId = "kermit";
		identityService.setAuthenticatedUserId(authenticatedUserId);

		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave").singleResult();
		Map<String, String> properties = new HashMap<String, String>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		String startDate = sdf.format(ca.getTime());
		ca.add(Calendar.DAY_OF_MONTH, 2);
		String endDate = sdf.format(ca.getTime());
		properties.put("startDate", startDate);
		properties.put("endDate", endDate);
		properties.put("reason", "公休");

		// start process with formservice
		ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), properties);

		// query task CandidateGroup is depLeader
		Task task = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
		// search form data
		Object o = formService.getRenderedTaskForm(task.getId());
		System.out.println(o);
		taskService.claim(task.getId(), "kermit");
		properties = new HashMap<String, String>();
		properties.put("deptLeaderApproved", "true");
		formService.submitTaskFormData(task.getId(), properties);

		// query task CandidateGroup is hrLeader
		task = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
		o = formService.getRenderedTaskForm(task.getId());
		System.out.println(o);
		taskService.claim(task.getId(), "jenny");
		properties = new HashMap<String, String>();
		properties.put("hrApproved", "true");
		formService.submitTaskFormData(task.getId(), properties);

		task = taskService.createTaskQuery().taskAssignee(authenticatedUserId).singleResult();
		properties = new HashMap<String, String>();
		properties.put("reportBackDate", sdf.format(new Date()));
		formService.submitTaskFormData(task.getId(), properties);

		ProcessInstance endProcessInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();
		if (endProcessInstance == null) {
			System.out.println("-----流程结束了");
		}

		// find history variables (有appluUserId)
		List<HistoricVariableInstance> variableList = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).list();
		for (HistoricVariableInstance historicVariableInstance : variableList) {
			System.out.println("--->" + historicVariableInstance.getVariableName() + " " + historicVariableInstance.getValue());
		}
		// find history formProperty(没有appluUserId 因为这个属性是流程变量不是formProperty)
		List<HistoricDetail> historyDetailList = historyService.createHistoricDetailQuery().processInstanceId(processInstance.getId()).list();
		for (HistoricDetail historicDetail : historyDetailList) {
			if (historicDetail instanceof HistoricFormProperty) {
				// 表单中的字段
				HistoricFormProperty field = (HistoricFormProperty) historicDetail;
				System.out.println("form field: taskId=" + field.getTaskId() + ", " + field.getPropertyId() + " = " + field.getPropertyValue());
			} else if (historicDetail instanceof HistoricVariableUpdate) {
				HistoricVariableUpdate variable = (HistoricVariableUpdate) historicDetail;
				System.out.println("variable: " + variable.getVariableName() + " = " + variable.getValue());
			}
		}
		System.out.println(1);
	}

}
