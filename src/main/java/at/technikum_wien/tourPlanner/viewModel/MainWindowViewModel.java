package at.technikum_wien.tourPlanner.viewModel;

public class MainWindowViewModel {
    private SearchBarViewModel searchBarViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel) {
        this.searchBarViewModel = searchBarViewModel;

    }


    // TODO: this.searchBarViewModel.addSearchListener(searchString->searchTours(searchString));
    // instead of the lambda-expression from above, you also can use the following "classical" event-handler implementation with anonymous inner classes
//        this.searchBarViewModel.addSearchListener(new SearchBarViewModel.SearchListener() {
//            @Override
//            public void search(String searchString) {
//                var tours = BL.getInstance().findMatchingTours( searchString );
//                toursOverviewViewModel.setTours(tours);
//            }
//        });
}
