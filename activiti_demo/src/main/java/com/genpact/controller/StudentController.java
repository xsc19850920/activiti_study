package com.genpact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

	Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	ProcessEngine processEngine;
	@Autowired
	TaskService taskService;
	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	HistoryService historyService;
	@Autowired
	FormService formService;
	@Autowired
	ManagementService managementService;
	@Autowired
	IdentityService identityService;

	/*
	 * 启动流程 启动流程，只考虑首次登录。 首次登录：启动工作流，并且更新/{processDefinitionId}
	 * @RequestMapping(value = "get-form/start/{processDefinitionId}")
	 */
	@RequestMapping(value = "/start/{processDefinitionId}")
	public String start(@PathVariable("processDefinitionId") String processDefinitionId, HttpServletRequest request) throws Exception {

		try {
			// 定义map用于往工作流数据库中传值。
			Map<String, String> formProperties = new HashMap<String, String>();
			formProperties.put("userId", "sxia");
			// 启动流程-processDefinitionId,
			ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
			// 返回到显示用户信息的controller
			logger.debug("start a processinstance: {}", processInstance);
			return "redirect:/get-form/task/" + processInstance.getId();

		} catch (Exception e) {
			throw e;
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

	}

	/**
	 * 读取Task的表单
	 * 
	 * @RequestMapping(value = "get-form/task/{processDefinitionkey}")
	 * @PathVariable("processDefinitionkey") String processDefinitionkey
	 */
	@RequestMapping(value = "/get-form/task/{processInstanceId}")
	@ResponseBody
	public ModelAndView findTaskForm(@PathVariable("processInstanceId") String processInstanceId, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("leave/apply");
		// 获取当前登陆人信息。
		/* User user = UserUtil.getUserFromSession(request.getSession()); */

		TaskQuery taskQuery = taskService.createTaskQuery().processInstanceId(processInstanceId).orderByProcessInstanceId().desc();

		List<Task> tasks = taskQuery.list();
		if (tasks.size() == 0) {
			ModelAndView mav2 = new ModelAndView("leave/finish");
			return mav2;
		}
		Task task = tasks.get(0);
		Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		mav.addObject("renderedTaskForm", renderedTaskForm.toString());
		mav.addObject("taskId", task.getId());
		mav.addObject("processInstanceId", processInstanceId);
		return mav;
	}

	/**
	 * 办理任务，提交task的并保存form
	 */
	@RequestMapping(value = "/task/complete/{taskId}/{processInstanceId}")
	@SuppressWarnings("unchecked")
	public String completeTask(@PathVariable("taskId") String taskId, @PathVariable("processInstanceId") String processInstanceId, RedirectAttributes redirectAttributes, HttpServletRequest request) {

		Map<String, String> formProperties = new HashMap<String, String>();

		// 从request中读取参数然后转换
		Map<String, String[]> parameterMap = request.getParameterMap();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			String key = entry.getKey();

			/*
			 * 参数结构：fq_reason，用_分割 fp的意思是form paremeter 最后一个是属性名称
			 */
			if (StringUtils.defaultString(key).startsWith("fp_")) {
				String[] paramSplit = key.split("_");
				formProperties.put(paramSplit[1], entry.getValue()[0]);
			}
		}

		logger.debug("start form parameters: {}", formProperties);

		try {
			formService.submitTaskFormData(taskId, formProperties);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}

		redirectAttributes.addFlashAttribute("message", "任务完成：taskId=" + taskId);
		return "redirect:/get-form/task/" + processInstanceId;

	}
}
