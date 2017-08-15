package edu.csula.cs594.client.pojo;

public class StatusResponse {
    private String uri;
    private int numberOfCalls;
    private String status;
    private String message;
    private String requestType;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    
}
