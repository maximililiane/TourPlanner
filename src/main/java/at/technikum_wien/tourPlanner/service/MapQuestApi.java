package at.technikum_wien.tourPlanner.service;


import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class MapQuestApi extends Mapper {

    private final String MKEY = "BaprjXoFO6rXxKBbL6M2F0tAG3oXJJ7Y";

    public MapQuestApi() {
        super();
    }

    // GET route from MapQuest API
    public void getRoute(String from, String to) {
        try {
            String baseUri = "http://www.mapquestapi.com/directions/v2/route?key=" + MKEY;

            // prepare URI
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(baseUri);
            uriBuilder.append("&from=").append(from);
            uriBuilder.append("&to=").append(to);

            URI uri = URI.create(uriBuilder.toString());

            // prepare request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // prepare client
            HttpClient client = HttpClient.newHttpClient();

            // send get request
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // parse get request into RouteResponse
            RouteResponse routeResponse = toObject(response.body(), RouteResponse.class);
            System.out.println(routeResponse);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
