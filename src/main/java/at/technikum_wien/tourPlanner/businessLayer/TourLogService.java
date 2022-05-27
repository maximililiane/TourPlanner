package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourLogRepository;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ObservableList;

import java.util.List;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class TourLogService {

    private final TourLogRepository tourLogRepository;
    private final TourRepository tourRepository;
    private ObservableList<TourLog> logs;
    private ObservableList<Tour> tours;

    private final LoggerWrapper logger = LoggerFactory.getLogger();

    public TourLogService(TourRepository tourRepository, TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
        this.tourRepository = tourRepository;
        this.logs = getLogs();
        this.tours = getTours();
    }

    public void setTourLogTableName(TableName tableName) {
        tourLogRepository.setTableName(tableName);
        this.logs = tourLogRepository.getObservableLogList();
    }

    public void deleteAllLogs() {
        try {
            tourLogRepository.deleteAllLogs();
            logs.removeAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTourLog(TourLog l) {
        try {
            l.setUid(tourLogRepository.getNewestLogId());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to retrieve the newest LogID; " + e.getMessage());
        }
        try {
            tourLogRepository.addLog(l);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to add a new log to the database; Log: " + l + ";\n" + e.getMessage());
        }
        addLogToTour(l.getTourID(), l);
        logs.add(l);
    }

    public void updateLogList() throws SQLException {
        logs.removeAll(logs);
        logs.addAll(tourLogRepository.getLogs());
    }

    public void addLogToTour(int tourId, TourLog l) {
        for (Tour t : tours) {
            if (t.getUid() == tourId) {
                t.insertLog(l);
                t.setPopularity();
                t.setChildFriendly(calculateChildFriendliness(t.getLength(), t.getLogs()));
                updateTour(tourId, t.getPopularity(), t.getChildFriendly());
                return;
            }
        }
    }

    private int calculateChildFriendliness(double tourDistance, List<TourLog> tourLogs) {
        if (tourLogs.size() == 0) {
            // there are no tour logs so child friendliness cannot be computed
            return 0;
        }

        int averageDifficulty = 0;
        int averageTimeDifficulty = 0;
        int distanceDifficulty;

        for (TourLog log : tourLogs) {
            // average difficulty
            averageDifficulty += (100 - log.getDifficulty());

            // average time difficulty
            String[] values = log.getTotalTime().toString().split(":");
            int hours = Integer.parseInt(values[0]);

            if (hours < 2) {
                // tour is shorter than two hours -> 100 child friendliness
                averageTimeDifficulty += 100;
            } else if (hours > 24) {
                // tour is longer than 24 hours -> 0 child friendliness
                averageTimeDifficulty += 0;
            } else {
                // after two hours, each tour loses 5 child friendliness points per hour
                averageTimeDifficulty += (110 - (hours * 5));
            }

        }

        // distance difficulty,
        if (tourDistance < 100) {
            distanceDifficulty = 100;
        } else if (tourDistance > 1000) {
            distanceDifficulty = 0;
        } else {
            distanceDifficulty = (int) ((1000 - tourDistance) / 10);
        }

        averageDifficulty = averageDifficulty / tourLogs.size();
        averageTimeDifficulty = averageTimeDifficulty / tourLogs.size();

        return (averageDifficulty + averageTimeDifficulty + distanceDifficulty) / 3;

    }

    public void editTourLog(TourLog updatedLog) {
        try {
            tourLogRepository.editLog(updatedLog);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to edit a log in the database; LogID: " + updatedLog.getUid() + ";\n" + e.getMessage());
        }
        try {
            updateLogList();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while fetching the updated loglist;\n" + e.getMessage());
        }
    }

    public void deleteLog(TourLog log) {
        try {
            tourLogRepository.deleteLog(log.getUid());
            deleteLogFromTour(log.getTourID(), log);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to delete a log in the database; LogID: " + log.getUid() + ";\n" + e.getMessage());

        }
        try {
            updateLogList();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while fetching the updated loglist;\n" + e.getMessage());
        }
    }


    public void deleteLogFromTour(int tourId, TourLog l) {
        for (Tour t : tours) {
            if (t.getUid() == tourId) {
                for (TourLog log : t.getLogs()) {
                    if (log.getUid() == l.getUid()) {
                        t.deleteLog(log);
                        t.setPopularity();
                        t.setChildFriendly(calculateChildFriendliness(t.getLength(), t.getLogs()));
                        updateTour(tourId, t.getPopularity(), t.getChildFriendly());
                        return;
                    }
                }
                return;
            }
        }
    }

    public void updateTour(int tourId, int popularity, int childFriendliness) {
        try {
            tourRepository.updatePopularity(tourId, popularity);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to update the popularity of a tour in the database; TourID: " + tourId + ";\n" + e.getMessage());


        }
        try {
            tourRepository.updateChildFriendliness(tourId, childFriendliness);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to update the child friendliness of a tour in the database; TourID: " + tourId + ";\n" + e.getMessage());

        }
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
            try {
                tourLogRepository.addLog(log);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("An error occurred while trying to add a new log to the database; Log: " + log + ";\n" + e.getMessage());
            }
            logs.add(log);
        }
    }

    public ObservableList<TourLog> getObservableLogList() {
        return this.logs;
    }

    public ObservableList<Tour> getObservableTourList() {
        return this.tours;
    }

    public int getTourId(String tourName) {
        return tours.stream().filter(tour -> tour.getName().equals(tourName)).findAny().get().getUid();
    }

}
