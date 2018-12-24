package com.genpact.activiti.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import org.apache.tools.ant.Project;
//import org.apache.tools.ant.taskdefs.SQLExec;

import org.activiti.engine.ProcessEngineConfiguration;

/**
 */
public class InitEngineeDatabase {

	public static void main(String[] args) { 
		//activiti init
	  ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault().buildProcessEngine();
	 }
	/*
	 * public static void main(String[] args) throws IOException { //
	 * InputStream input = //
	 * Thread.currentThread().getClass().getClassLoader().
	 * getResourceAsStream("application.properties"); InputStream inStream =
	 * InitEngineeDatabase
	 * .class.getClassLoader().getResourceAsStream("application.properties");
	 * Properties properties = new Properties(); properties.load(inStream);
	 * 
	 * String sqlFile = System.getProperty("user.dir") + File.separatorChar +
	 * "db" + File.separatorChar + "create.sql";
	 * 
	 * SQLExec sqlExec = new SQLExec(); // 设置数据库参数
	 * sqlExec.setDriver(properties.getProperty("jdbc.driver"));
	 * sqlExec.setUrl(properties.getProperty("jdbc.url"));
	 * sqlExec.setUserid(properties.getProperty("jdbc.username"));
	 * sqlExec.setPassword(properties.getProperty("jdbc.password"));
	 * sqlExec.setSrc(new File(sqlFile)); //
	 * sqlExec.setOnerror((SQLExec.OnError)
	 * (EnumeratedAttribute.getInstance(SQLExec // .OnError.class, //
	 * "abort"))); sqlExec.setPrint(true); // 设置是否输出 // 输出到文件 sql.out
	 * 中；不设置该属性，默认输出到控制台 // sqlExec.setOutput(new File("d:/script/sql.out"));
	 * sqlExec.setProject(new Project()); // 要指定这个属性，不然会出错 sqlExec.execute();
	 * 
	 * inStream.close();
	 * 
	 * }
	 */

}
