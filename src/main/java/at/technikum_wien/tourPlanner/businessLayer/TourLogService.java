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
        try {
            l.setUid(tourLogRepository.getNextTourId());
            tourLogRepository.addLog(l);
            logs.add(l);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editTourLog(TourLog updatedLog) {
        try {
            tourLogRepository.editLog(updatedLog);
            deleteLogFromList(updatedLog);
            logs.add(updatedLog);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLog(TourLog log) {
        try {
            tourLogRepository.deleteLog(log.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        deleteLogFromList(log);
    }

    private void deleteLogFromList(TourLog updatedLog){
        for(TourLog oldLog: logs){
            if(updatedLog.getUid()== oldLog.getUid()){
                logs.remove(oldLog);
            }
        }
        logs.add(updatedLog);
        System.out.println(logs.toString());
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
