package at.technikum_wien.tourPlanner.viewModel;
import at.technikum_wien.tourPlanner.businessLayer.TourService;
import at.technikum_wien.tourPlanner.models.Tour;
import javafx.collections.ObservableList;


public class ListViewModel {

    private ObservableList<Tour> list;
    private TourService tourService;

    // public ListViewModel(DBProxy proxy){
    public ListViewModel(TourService tourService) {
        this.tourService = tourService;
        this.list = tourService.getObservableTourList();
    }

    public ObservableList<Tour> getList() {
        return list;
    }

    public void setList(ObservableList<Tour> list) {
        this.list = list;
    }

    public void addItem(Tour item) {
        list.add(item);
    }
}
