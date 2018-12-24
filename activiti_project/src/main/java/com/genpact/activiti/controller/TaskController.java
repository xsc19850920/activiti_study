package com.genpact.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.genpact.activiti.entity.LeaveBill;
import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.LeaveBillService;
import com.genpact.activiti.utils.Constant;

@Controller
public class TaskController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private LeaveBillService leaveBillService;

	@Autowired
	private FormService formService;

	@RequestMapping(value = "task/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		User user = (User) session.getAttribute(Constant.LOGIN_USER);
		List<Task> list = taskService.createTaskQuery().taskAssignee(user.getId() + "").list();
		model.addAttribute("list", list);
		return "task/list";
	}

	@RequestMapping(value = "task/view/{taskId}", method = RequestMethod.GET)
	public String view(@PathVariable("taskId") String taskId, Model model) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).singleResult();
		// leave bill part
		String bsinessKey = processInstance.getBusinessKey();
		Long id = Long.parseLong(bsinessKey.split(Constant.BSINESSKEY_CHAR)[1]);
		LeaveBill leaveBill = leaveBillService.findById(id);
		model.addAttribute("obj", leaveBill);

		// get ProcessDefinitionEntity info （*.bpmn 里面的信息）
		// 注意这里不要写错
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		// 获取流程实例中的当前活动任务的节点
		String activityId = processInstance.getActivityId();
		// 获取当前活动的任务节点对象
		ActivityImpl activity = processDefinitionEntity.findActivity(activityId);
		// 获得所有从当前节点出去的连线
		List<String> pvmTransitionNameList = new ArrayList<String>();
		List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			if (pvmTransition.getProperty("name") != null && StringUtils.isNotEmpty(pvmTransition.getProperty("name").toString())) {
				pvmTransitionNameList.add(pvmTransition.getProperty("name").toString());
			} else {
				pvmTransitionNameList.add("approve");
			}
		}
		model.addAttribute("taskId", taskId);
		model.addAttribute("list", pvmTransitionNameList);
		// 查询审批记录
		List<Comment> commonList = new ArrayList<Comment>();
		List<HistoricTaskInstance> hisTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstance.getId()).list();
		for (HistoricTaskInstance historicTaskInstance : hisTaskList) {
			commonList.addAll(taskService.getTaskComments(historicTaskInstance.getId()));
		}
		model.addAttribute("commonList", commonList);
		return "task/view";
	}

	@RequestMapping(value = "task/complete", method = RequestMethod.POST)
	public String complete(String taskId, String common, Long leaveBillId, String opt, Model model, HttpSession session) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		// 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
		Authentication.setAuthenticatedUserId(((User) session.getAttribute(Constant.LOGIN_USER)).getName());// 批注人的名称
																											// 一定要写，不然查看的时候不知道人物信息
		taskService.addComment(taskId, task.getProcessInstanceId(), common);
		// set choose line
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("opt", opt);
		taskService.complete(taskId, variables);

		// 查询流程是否结束
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		// 如果流程结束
		if (processInstance == null) {
			LeaveBill leaveBill = leaveBillService.findById(leaveBillId);
			// 审批完成
			leaveBill.setState(2);
			leaveBillService.save(leaveBill);
		}
		return "redirect:list";
	}

}
