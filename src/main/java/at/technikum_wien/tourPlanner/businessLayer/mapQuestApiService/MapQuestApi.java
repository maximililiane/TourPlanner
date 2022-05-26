package at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService;


import at.technikum_wien.tourPlanner.Injector;
import at.technikum_wien.tourPlanner.configuration.Configuration;
import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.BoundingBox;
import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.TransportMode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;


public class MapQuestApi extends Mapper {

    private final String API_KEY;

    private final LoggerWrapper logger= LoggerFactory.getLogger();

    public MapQuestApi() {
        super();
        Configuration configuration = Injector.getConfig("app.properties");
        this.API_KEY = configuration.get("mapQuest.key");
    }

    // TODO: add threading
    // GET route from MapQuest API
    public RouteResponse getRoute(String from, String to, TransportMode transportMode) {
        try {
            String baseUri = "http://www.mapquestapi.com/directions/v2/route?key=" + API_KEY;

            // prepare URI
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(baseUri);
            uriBuilder.append("&from=").append(URLEncoder.encode(from, StandardCharsets.UTF_8));
            uriBuilder.append("&to=").append(URLEncoder.encode(to, StandardCharsets.UTF_8));
            uriBuilder.append("&routeType=").append(transportMode.name());

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

            return routeResponse;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.error("An error occured while retrieving a route;\n" + e.getMessage());

        }
        return null;
    }

    // Get map jpg from MapQuest API
    public BufferedImage getMap(BoundingBox boundingBox, String sessionId) {
        try {
            String baseUri = "http://www.mapquestapi.com/staticmap/v5/map?key=" + API_KEY;

            //prepare URI
            StringBuilder uriBuilder = new StringBuilder();
            uriBuilder.append(baseUri);
            // TODO: change size?
            uriBuilder.append("&size=640,480");
            uriBuilder.append("&defaultMarker=none");
            uriBuilder.append("&zoom=11");
            uriBuilder.append("&rand=737758036");
            uriBuilder.append("&session=").append(sessionId);
            uriBuilder.append("&").append(boundingBox);

            URI uri = URI.create(uriBuilder.toString());

            // prepare request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // prepare client
            HttpClient client = HttpClient.newHttpClient();

            // send get request
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            // jpeg image is saved as byte[] in the response, get this
            byte[] byteArray = response.body();

            // byte[] -> BufferedImage
            return ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            logger.error("An error occured while retrieving a map;\n" + e.getMessage());

        }
        return null;
    }


}
