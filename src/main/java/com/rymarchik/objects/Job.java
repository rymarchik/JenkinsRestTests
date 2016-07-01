package com.rymarchik.objects;

public class Job {

	private String name;
	private String description;
	private String path;
	
	public Job(String name) {
		this.name = name;
	}

	public void setCreatePath() {
		path = "/createItem";
	}
	
	public void setDescriptionPath() {
		path = "/job/" + name + "/description";
	}
	
	public void setConfigurationPath() {
		path = "/job/" + name + "/config.xml";
	}
	
	public void setEnablePath() {
		path = "/job/" + name + "/enable";
	}
	
	public void setDisablePath() {
		path = "/job/" + name + "/disable";
	}
	
	public void setBuildPath() {
		path = "/job/" + name + "/build";
	}
	
	public void setBuildWithParamsPath() {
		path = "/job/" + name + "/buildWithParameters";
	}
	
	public void setDeleteLastBuildPath() {
		path = "/job/" + name + "/lastBuild/doDelete";
	}
	
	public void setDeletePath() {
		path = "/job/" + name + "/doDelete";
	}
	
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
