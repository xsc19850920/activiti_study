package com.genpact.activiti.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.genpact.activiti.entity.LeaveBill;
import com.genpact.activiti.entity.User;
import com.genpact.activiti.service.ActivitiService;
import com.genpact.activiti.service.LeaveBillService;
import com.genpact.activiti.service.UserService;
import com.genpact.activiti.utils.Constant;

@Controller
public class ProcessController {
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private LeaveBillService leaveBillService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivitiService activitiService;
	
	
	
	
	
	@RequestMapping(value="process/list",method=RequestMethod.GET)
	public String list(Model model){
		long total = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().count();
		List<Deployment> list = repositoryService.createDeploymentQuery().orderByDeploymenTime().desc().list();
		model.addAttribute("list", list);
		model.addAttribute("total", total);
		return "process/list";
	}
	
	@RequestMapping(value="process/del/{deploymentId}",method=RequestMethod.GET)
	public String del(@PathVariable("deploymentId")String deploymentId){
		repositoryService.deleteDeployment(deploymentId,true);
		return "redirect:../list";
	}
	
	@RequestMapping(value="process/deployment",method=RequestMethod.POST)
	public String deployment(MultipartFile file,String processName){
		try {
			 repositoryService
			.createDeployment()
			.addZipInputStream(new ZipInputStream(file.getInputStream()))
//		.addClasspathResource(Constant.ACTIVITI_DIAGRAM_FOLDER+name+Constant.PROCESS_EXT_BPMN)
//		.addClasspathResource(Constant.ACTIVITI_DIAGRAM_FOLDER+name+Constant.PROCESS_EXT_PNG)
			.name(processName)
			.deploy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:list";
	}
	
	@RequestMapping(value="process/start/{id}",method=RequestMethod.GET)
	public String startProcessInstance(@PathVariable("id")Long leaveBillId,HttpServletRequest request,HashMap<String, Object> variables){
		String businessName = LeaveBill.class.getSimpleName();
		User user = (User)request.getSession().getAttribute(Constant.LOGIN_USER);
		variables.put("userId", user.getId());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(businessName, businessName+Constant.BSINESSKEY_CHAR+leaveBillId, variables);
		
		//change leave bill status
		if(processInstance != null){
			LeaveBill leaveBill = leaveBillService.findById(leaveBillId);
			leaveBill.setState(1);
			leaveBillService.save(leaveBill);
		}
		return "redirect:../../leavebill/list";
	}
	
	
	/**
	 * 察看流程图
	 * @throws IOException 
	 */
	@RequestMapping(value="process/img/{id}",method=RequestMethod.GET)
	public void findPic(@PathVariable("id")Long leaveBillId,HttpServletResponse response) throws IOException {  
		 activitiService.findProcessPic(leaveBillId,response);
    }

	
	
	
	
}
