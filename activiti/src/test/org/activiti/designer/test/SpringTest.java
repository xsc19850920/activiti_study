package org.activiti.designer.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti.cfg.xml")
public class SpringTest {
	 @Autowired
	  private RuntimeService runtimeService;

	  @Autowired
	  private TaskService taskService;
	  
	  @Autowired
	  private RepositoryService repositoryService;
	  @Autowired
	  private   HistoryService historyService;
	  
	  

	  @Autowired
	  @Rule
	  public ActivitiRule activitiSpringRule;

	  @Test
	  @Deployment
	  public void simpleProcessTest() {
	    runtimeService.startProcessInstanceByKey("applicationForLeave");
	    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("student").list();
	    for (Task task : tasks) {
	    	// System.out.println(task.getName());
	    	 System.out.println(task.getExecutionId());
	    	 task.delegate("temp");
		}
	   
	   // assertEquals(0, runtimeService.createProcessInstanceQuery().count());

	  }
	  
	  
	 // @Test
	  //获得流程图的图片
	  public void showImg(){
		 ProcessDefinition process = repositoryService.createProcessDefinitionQuery().processDefinitionKey("applicationForLeave").singleResult();
		 
		 
		 InputStream input = repositoryService.getResourceAsStream(process.getDeploymentId(), process.getDiagramResourceName());
		 
		 try {
			OutputStream out = new  FileOutputStream(new File("d://a.png"));
			
			int temp = 0;
			byte[] arr = new byte[1024];
			while((temp = input.read(arr)) != -1){
				out.write(arr,0,temp);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  
	  
	  //领取任务
	  @Test
	  public void claim(){
//			List<Task> list = taskService.createTaskQuery().taskCandidateGroup("management").list();
//			
//			for (Task task : list) {
//				taskService.claim(task.getId(), "kermit");
//			}
//			
			
	 }
	  //查看分配给kermit的任务列表
	  @Test
	  public void assignee(){
		  List<Task> taskList = taskService.createTaskQuery().taskAssignee("kermit").list();
			for (Task task : taskList) {
				System.out.println(task.getName());
			}
	  }
	  
	  //完成任务
	  @Test
	  public void complete(){
		  List<Task> taskList = taskService.createTaskQuery().taskAssignee("kermit").list();
			for (Task task : taskList) {
				taskService.complete(task.getId());
			}
	  }
	  
	  
	  //查看历史
	  
	  @Test
	  public void gethis(){
		  List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee("kermit").list();
		  for (HistoricTaskInstance l : list) {
			System.out.println(l.getName());
		}
	  }
	  
	  // and so on...
	  
}
