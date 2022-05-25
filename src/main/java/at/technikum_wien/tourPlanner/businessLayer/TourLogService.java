package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourLogRepository;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;

    public TourLogService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
        this.logs = getLogs();
        this.tours = getTours();
    }

    public void addTourLog(TourLog l) {
        // TODO: implement add tour log through gui
        try {
            tourLogRepository.addLog(l);
            l.setUid(tourLogRepository.getNextTourId());
            logs.add(l);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*// update tour with new log
        Tour tour = tours.stream().filter(t -> t.getUid() == log.getTourID()).findFirst().get();
        int index = tours.indexOf(tour);
        tour.insertLog(log);
        tours.set(index, tour);*///TODO: Was ist das
    }

    public List<TourLog> getLogsByTourId(int id) {
        return logs.stream().filter(log -> log.getTourID() == id).collect(Collectors.toList());
    }

    public ObservableList<TourLog> getLogs() {
        return tourLogRepository.getObservableLogList();
    }

    public ObservableList<Tour> getTours() {
        return tourRepository.getObservableTourList();
    }

    public void importLogsByTourId(int id, List<TourLog> importedLogs) {
        for (TourLog log : importedLogs) {
            log.setTourID(id);
            // TODO: uncomment next line once addLog() has been implemented
            // tourLogRepository.addLog(log);
            logs.add(log);
        }
    }

    public ObservableList<TourLog> getObservableLogList() {
        return this.logs;
    }

    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
    }

}
