package com.rymarchik.tests;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import com.rymarchik.utils.ConfigProperties;

public class RestAPITests {
	
	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic
			(ConfigProperties.getProperty("username"), ConfigProperties.getProperty("password"));
	Client client = ClientBuilder.newClient().register(feature);
	
	@Test
	public void createJobTest() throws Exception {
		File config = new File("config.xml");
		Response response = client.target(ConfigProperties.getProperty("createJob.url")).
				request().header("Jenkins-Crumb", "22724f3b0411edceb05b0aae5c1f3276").
				post(Entity.xml(config));
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void getJobDescriptionTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("jobDescription.url")).
				request().get();
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void updateJobDescriptionTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("jobDescription.url")).
				queryParam("description", ConfigProperties.getProperty("newDescription")).
				request().post(null);
		assertEquals(response.getStatus(), 204);
	}
	
	@Test
	public void getJobConfigurationTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("jobConfiguration.url")).
				request().get();
		String output = response.readEntity(String.class);
		assertEquals(response.getStatus(), 200);
		assertEquals(output.substring(2, 5), "xml");
	}
	
	@Test
	public void updateJobConfigurationTest() throws Exception {
		File updatedConfig = new File("updated_config.xml");
		Response response = client.target(ConfigProperties.getProperty("jobConfiguration.url")).
				request().post(Entity.xml(updatedConfig));
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void enableJobTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("enableJob.url")).
				request().post(null);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void disableJobTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("disableJob.url")).
				request().post(null);
		assertEquals(response.getStatus(), 200);
	}
	
	@Test
	public void buildTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("build.url")).
				request().post(null);
		assertEquals(response.getStatus(), 201);
	}
	
	@Test
	public void buildWithParamsTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("buildWithParams.url")).
				queryParam("id", "12321").
				request().post(null);
		assertEquals(response.getStatus(), 500);
	}
	
	@Test
	public void copyJobTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("copyJob.url")).
				request().post(null);
		assertEquals(response.getStatus(), 403);
	}

	@Test(dependsOnMethods={"createJobTest"})
	public void deleteJobTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("deleteJob.url")).
				request().post(null);
		assertEquals(response.getStatus(), 200);
	}
}
