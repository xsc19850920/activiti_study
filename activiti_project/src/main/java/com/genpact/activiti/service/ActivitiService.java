package com.genpact.activiti.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genpact.activiti.entity.LeaveBill;
import com.genpact.activiti.utils.ActivityUtil;
import com.genpact.activiti.utils.Constant;

@Service
public class ActivitiService {
	Logger log = LoggerFactory.getLogger(ActivitiService.class);
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private LeaveBillService leaveBillService;
	
	
	public boolean processDef(){
		Deployment deployment = repositoryService.createDeployment()
						 .addClasspathResource(Constant.ACTIVITI_DIAGRAM_FOLDER+LeaveBill.class.getSimpleName()+Constant.PROCESS_EXT_BPMN)
						 .addClasspathResource(Constant.ACTIVITI_DIAGRAM_FOLDER+LeaveBill.class.getSimpleName()+Constant.PROCESS_EXT_PNG)
						 .name(Constant.PROCESS_NAME)
						 .deploy();
		if(null != deployment){
			log.info("流程部署成功！ 流程名称： "+deployment.getName() +",流程部署时间：" +deployment.getDeploymentTime());
			return true;
		}else{
			log.info("流程部署失败！");
			return false;
		}
	}
	
	public boolean  processStart(String userId,String reason,int leaveDays){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("reason", reason);
		map.put("leaveDays", leaveDays);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(LeaveBill.class.getSimpleName(),map);
		if (null == processInstance) {
			log.info("流程实例启动失败！");
			return false;
		}else{
			log.info("流程实例启动成功！ 流程实例id："+processInstance.getProcessInstanceId() +",流程实例的名称："+processInstance.getName()+",正在执行的流程实例Id："+processInstance.getId());
			
			Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
			//自动完成登陆人提交的任务
			taskService.complete(task.getId());
			return true;
		}
	}
	
	
	private static void decorate(BufferedImage image, int x, int y, int width, int height) {  
        Graphics2D g = image.createGraphics();  
        try {  
        	Paint originalPaint = g.getPaint();  
            Stroke originalStroke = g.getStroke();  
       
            g.setPaint(Color.RED);  
            g.setStroke(new BasicStroke(3.0f));  
       
            RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);  
            g.draw(rect);  
       
            g.setPaint(originalPaint);  
            g.setStroke(originalStroke); 
        } finally {  
            g.dispose();  
        }  
    } 
	

	public void findProcessPic(Long leaveBillId,HttpServletResponse response) throws IOException {
		HistoricProcessInstance hisProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(LeaveBill.class.getSimpleName()+Constant.BSINESSKEY_CHAR + leaveBillId).singleResult();
		String  processDefinitionId = hisProcessInstance.getProcessDefinitionId();
		
		ProcessDefinitionEntity processDefinitionEntity =  (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);
		
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).singleResult();
		
		String diagramResourceName = processDefinitionEntity.getDiagramResourceName();
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinitionEntity.getDeploymentId(),diagramResourceName);  
        LeaveBill leaveBill = leaveBillService.findById(leaveBillId);
        
        BufferedImage image = ImageIO.read(inputStream);
        String formatName = FilenameUtils.getExtension(diagramResourceName);
      //获取当前活动的任务节点对象
		if (processInstance != null && leaveBill.getState() == 1) {


			List<String> activeActivityIds = ActivityUtil.getActiveActivityIds(runtimeService, processInstance.getId());
			List<ActivityImpl> definitionActivities = ActivityUtil.getFlatAllActivities(processDefinitionEntity);
			for (ActivityImpl activityimpl : definitionActivities) {
				if (activeActivityIds.contains(activityimpl.getId())) {
					decorate(image, activityimpl.getX(), activityimpl.getY(), activityimpl.getWidth(), activityimpl.getHeight());
				}
			}
			
		}
		ImageIO.write(image, formatName, response.getOutputStream());
	}  
	
}
