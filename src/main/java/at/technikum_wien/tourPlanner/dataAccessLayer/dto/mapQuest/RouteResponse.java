package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RouteResponse {
    @JsonProperty("route")
    private Route route;
    @JsonProperty("info")
    private RouteInfo info;

    public RouteResponse() {
    }

    public Route getRoute() {
        return route;
    }

    public RouteInfo getRouteInfo() {
        return this.info;
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "route=" + route +
                ", info=" + info +
                '}';
    }
}
