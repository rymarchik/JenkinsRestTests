package com.rymarchik.tests;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.apache.http.client.methods.HttpPost;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppTest {

	// @Test
	// public void test2() throws Exception {
	// final String uri = "http://localhost:8080/user/insurg";
	// final RequestSpecification basicAuth =
	// RestAssured.given().auth().preemptive().basic("insurg", "root");
	// final Response response = basicAuth.accept("application/json").get(uri);
	//
	// Assert.assertEquals(response.getStatusCode(), 200);
	// }

	@Test
	public void test() throws Exception {

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("insurg", "root");
		Client client = ClientBuilder.newClient();
		client.register(feature);

		
		File file = new File("config.xml");


		Response response = client.target("http://localhost:8080/createItem?name=qqq").
				request().accept("application/xml").post(Entity.xml(file));

//		if (response.getStatus() != 200) {
//			throw new RuntimeException("Request failed");
//		}

		Assert.assertEquals(response.getStatus(), 200);
	}

}
