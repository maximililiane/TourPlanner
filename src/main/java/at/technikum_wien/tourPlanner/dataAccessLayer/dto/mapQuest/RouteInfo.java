package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonAlias;


public class RouteInfo {

    @JsonAlias({"statuscode"})
    private String statusCode;
    @JsonAlias({"messages"})
    private String[] messages;

    public RouteInfo() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "statusCode='" + statusCode + '\'' +
                '}';
    }
}
