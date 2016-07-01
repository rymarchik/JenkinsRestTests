package com.rymarchik.tests;

import java.io.File;
import java.util.UUID;

import javax.ws.rs.client.Entity;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rymarchik.objects.Job;
import com.rymarchik.utils.ConfigProperties;

public class JobTests extends BasicTest {
	
	@BeforeMethod
	public void setUp() {
		id = UUID.randomUUID();
		String jobName = id.toString().replaceAll("-","");
		job = new Job(jobName);
		config = new File("config.xml");
		job.setCreatePath();
		client.target(serverName + job.getPath()).queryParam("name", job.getName()).request().post(Entity.xml(config));
	}
	
	@AfterMethod
	public void tearDown() {
		job.setDeletePath();
		client.target(serverName + job.getPath()).request().post(null);
		if (jobNew != null)
			client.target(serverName + jobNew.getPath()).request().post(null);
		if (jobCopy != null)
			client.target(serverName + jobCopy.getPath()).request().post(null);
	}
	
	@Test
	public void createJobTest() throws Exception {
		config = new File("config.xml");
		job.setCreatePath();
		response = webTarget.queryParam("name", job.getName() + "NEW").request().post(Entity.xml(config));
		assertEquals(response.getStatus(), 200);
		jobNew = new Job(job.getName() + "NEW");
		jobNew.setDeletePath();
	}
	
	@Test
	public void getJobDescriptionTest() throws Exception {
		job.setDescriptionPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().get();
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void updateJobDescriptionTest() throws Exception {
		job.setDescriptionPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.queryParam("description", ConfigProperties.getProperty("newDescription")).request().post(null);
		assertEquals(response.getStatus(), 204);
	}
	
	@Test
	public void getJobConfigurationTest() throws Exception {
		job.setConfigurationPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().get();
		String output = response.readEntity(String.class);
		assertEquals(response.getStatus(), 200);
		assertEquals(output.substring(2, 5), "xml");
	}
	
	@Test
	public void updateJobConfigurationTest() throws Exception {
		config = new File("updated_config.xml");
		job.setConfigurationPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().post(Entity.xml(config));
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void enableJobTest() throws Exception {
		job.setEnablePath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().post(null);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void disableJobTest() throws Exception {
		job.setDisablePath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().post(null);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void buildTest() throws Exception {
		job.setBuildPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().post(null);
		assertEquals(response.getStatus(), 201);
	}
	
	@Test
	public void buildWithParamsTest() throws Exception {
		job.setBuildWithParamsPath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.queryParam("id", "12321").request().post(null);
		assertEquals(response.getStatus(), 500);
	}
	
	@Test
	public void copyJobTest() throws Exception {
		job.setCreatePath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.queryParam("name", job.getName() + "COPY").queryParam("mode", "copy").
				queryParam("from", job.getName()).request().post(null);
		assertEquals(response.getStatus(), 403);
		jobCopy = new Job(job.getName() + "COPY");
		jobCopy.setDeletePath();
	}

	@Test
	public void deleteJobTest() throws Exception {
		job.setDeletePath();
		webTarget = client.target(serverName + job.getPath());
		response = webTarget.request().post(null);
		assertEquals(response.getStatus(), 200);
	}
}
