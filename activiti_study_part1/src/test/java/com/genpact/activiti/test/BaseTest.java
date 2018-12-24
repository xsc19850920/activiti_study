package com.genpact.activiti.test;

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
@ContextConfiguration(value={"classpath:spring-actitivi.xml","classpath:spring-mvc.xml","classpath:spring-main.xml"})
public class BaseTest {
	@Autowired ProcessEngine processEngine;
	@Autowired TaskService taskService;
	@Autowired RepositoryService repositoryService;
	@Autowired RuntimeService runtimeService;
	@Autowired HistoryService historyService;
	@Autowired FormService formService;
	@Autowired ManagementService managementService;
	@Autowired IdentityService identityService;
	
}
