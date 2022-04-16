package at.technikum_wien.tourPlanner.view;

import at.technikum_wien.tourPlanner.dataAccessLayer.dto.mapQuest.RouteResponse;
import at.technikum_wien.tourPlanner.service.MapQuestApi;
import at.technikum_wien.tourPlanner.viewModel.DescriptionViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class DescriptionWindowController implements Initializable {

    private final DescriptionViewModel descriptionViewModel;
    @FXML
    public ImageView mapImage;

    public DescriptionWindowController(DescriptionViewModel descriptionViewModel) {
        this.descriptionViewModel = descriptionViewModel;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TODO: dynamic image
        MapQuestApi mapQuestApi = new MapQuestApi();
        RouteResponse response = mapQuestApi.getRoute("Wien", "Zeiselmauer,%20Austria");
        byte[] buffer = mapQuestApi.getMap(response);

        mapImage.setImage(new Image(new ByteArrayInputStream(buffer)));
    }
}
