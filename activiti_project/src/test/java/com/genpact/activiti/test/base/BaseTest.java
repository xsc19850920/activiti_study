package com.genpact.activiti.test.base;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-main.xml", "classpath*:spring-mvc.xml","classpath*:spring-actitivi.xml" }) 
public class BaseTest {
	@Autowired
	public  ProcessEngine processEngine;
	
	@Autowired
	public TaskService taskService;
	
	@Autowired 
	public RepositoryService repositoryService;
	
	@Autowired 
	public RuntimeService runtimeService;
	
	@Autowired 
	public HistoryService historyService;
	
	@Autowired 
	public FormService formService;
	
	@Autowired 
	public ManagementService managementService;
	
	@Autowired 
	public IdentityService identityService;
	
}
