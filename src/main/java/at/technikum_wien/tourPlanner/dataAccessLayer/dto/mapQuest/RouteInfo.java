package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonAlias;


public class RouteInfo {

    @JsonAlias({"statuscode"})
    private String statusCode;

    public RouteInfo() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return "RouteInfo{" +
                "statusCode='" + statusCode + '\'' +
                '}';
    }
}
