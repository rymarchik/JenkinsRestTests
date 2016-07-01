package com.rymarchik.tests;

import java.io.File;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.rymarchik.entities.User;
import com.rymarchik.objects.Job;
import com.rymarchik.utils.ConfigProperties;

public class BasicTest {

	String serverName = "http://localhost:8080";
	Job job, jobNew, jobCopy;
	UUID id;
	File config;
	WebTarget webTarget;
	Response response;
	
	User admin = new User(ConfigProperties.getProperty("username"), ConfigProperties.getProperty("password"));

	HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(admin.getLogin(), admin.getPassword());
	
	Client client = ClientBuilder.newClient().register(feature);
}