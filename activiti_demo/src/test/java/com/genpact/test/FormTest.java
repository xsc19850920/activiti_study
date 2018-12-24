package com.genpact.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.junit.Test;

public class FormTest extends BaseTest {
	
	@Test
	public void processDef() throws IOException{
		String folder = "activiti/form/";
		String fileName = "form.zip";
		ZipInputStream input = new ZipInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource(folder).getPath()+fileName)));
		repositoryService.createDeployment()
						 .addZipInputStream(input)
						 .deploy();
	}
}
