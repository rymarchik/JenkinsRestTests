package com.rymarchik.tests;

import java.io.File;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.crypto.dsig.XMLObject;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.json.JSONObject;
import org.json.XML;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

import com.rymarchik.utils.ConfigProperties;
//import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestAPITests {
	
	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic
			(ConfigProperties.getProperty("username"), ConfigProperties.getProperty("password"));
	Client client = ClientBuilder.newClient().register(feature);
	
	@Test
	public void createJobTest() throws Exception {
		
//		Client client = Client.create();
//
//		WebResource webResource = client.resource("http://localhost:8080/createItem?name=qqq11");
//
		File config = new File("config.xml");
//		
//		ClientResponse response = webResource.type("application/xml").post(ClientResponse.class, file);	
		
//		ClientResponse response = webResource.type("application/xml").header("Content-Type", "application/xml").
//		header("crumb","7fd39d64929bbec79d90651dcf90488f").
//		header("crumbRequestField","Jenkins-Crumb").post(ClientResponse.class, file);

		Response response = client.target(ConfigProperties.getProperty("createJob.url")).
				request().post(Entity.xml(config));
		
		assertEquals(response.getStatus(), 200);
//		Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
	}
	
	@Test
	public void getJobConfigurationTest() throws Exception {
		Response response = client.target(ConfigProperties.getProperty("getJob.url")).
				request().get();
		String output = response.readEntity(String.class);
		assertEquals(response.getStatus(), 200);
		assertEquals(output.substring(2, 5), "xml");
	}
	
	@Test
	public void updateJobTest() throws Exception {
		File updatedConfig = new File("updated_config.xml");
		Response response = client.target(ConfigProperties.getProperty("getJob.url")).
				request().post(Entity.xml(updatedConfig));
		assertEquals(response.getStatus(), 200);
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
