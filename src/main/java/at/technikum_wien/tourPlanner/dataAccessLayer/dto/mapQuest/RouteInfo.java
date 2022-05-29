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

    public String[] getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "RouteInfo{" +
                "statusCode='" + statusCode + '\'' +
                '}';
    }
}
