package at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RouteResponse {
    @JsonAlias({"route"})
    private Route route;

    public RouteResponse() {
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "RouteResponse{" +
                "route=" + route +
                '}';
    }
}
