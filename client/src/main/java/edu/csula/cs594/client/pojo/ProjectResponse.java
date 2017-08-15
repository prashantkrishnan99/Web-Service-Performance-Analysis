package edu.csula.cs594.client.pojo;

public class ProjectResponse {
    private String uri;    
    private String projectname;
    private String status;
    private int requestCount;
    private int projectid;
    private String date;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
    
    public String getDate() {
    	return date;
    }
    
    public void setDate(String date) {
    	this.date = date;
    }
}
