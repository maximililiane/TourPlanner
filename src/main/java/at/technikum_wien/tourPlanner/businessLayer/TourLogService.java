package at.technikum_wien.tourPlanner.businessLayer;

import at.technikum_wien.tourPlanner.businessLayer.mapQuestApiService.Mapper;
import at.technikum_wien.tourPlanner.dataAccessLayer.database.TableName;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourLogRepository;
import at.technikum_wien.tourPlanner.dataAccessLayer.repositories.TourRepository;
import at.technikum_wien.tourPlanner.logging.LoggerFactory;
import at.technikum_wien.tourPlanner.logging.LoggerWrapper;
import at.technikum_wien.tourPlanner.models.Tour;
import at.technikum_wien.tourPlanner.models.TourLog;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.LinkedList;
import java.util.List;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class TourLogService extends Mapper {

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
        this.tours.addListener(new ListChangeListener<Tour>() {
            @Override
            public void onChanged(Change<? extends Tour> change) {
                while (change.next()) {
                    if (change.wasRemoved()) {
                        deleteTourLogs();
                    } else if (change.wasReplaced()) {
                        tours.forEach(t -> t.setChildFriendly(calculateChildFriendliness(t.getLength(), t.getLogs())));
                    }
                }
            }
        });
    }

    public void setTourLogTableName(TableName tableName) {
        tourLogRepository.setTableName(tableName);
        this.logs = tourLogRepository.getObservableLogList();
        assignLogsToTours();
    }

    public void resetLogTable() {
        try {
            tourLogRepository.resetLogTable();
            this.logs.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTourLog(TourLog l) {
        try {
            l.setUid(tourLogRepository.getNewestLogId());
            System.out.println(l.getUid());
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
        System.out.println(logs);
    }

    public void addLogToTour(int tourId, TourLog log) {
        Tour tour = tours.stream().filter(t -> t.getUid() == tourId).findAny().get();
        int index = tours.indexOf(tour);
        tour.insertLog(log);
        tour.setPopularity();
        tour.setChildFriendly(calculateChildFriendliness(tour.getLength(), tour.getLogs()));
        updateTour(tourId, tour.getPopularity(), tour.getChildFriendly());
        tours.set(index, tour);
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
            TourLog oldLog = logs.stream().filter(l -> l.getUid() == updatedLog.getUid()).findFirst().get();
            int index = logs.indexOf(oldLog);
            updateLogInTour(oldLog.getTourID(), updatedLog);
            logs.set(index, updatedLog);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to edit a log in the database; LogID: " + updatedLog.getUid() + ";\n" + e.getMessage());
        }
        System.out.println(logs);
    }

    public void deleteLog(TourLog log) {
        try {
            tourLogRepository.deleteLog(log.getUid());
            deleteLogFromTour(log.getTourID(), log);
            logs.remove(log);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("An error occurred while trying to delete a log in the database; LogID: " + log.getUid() + ";\n" + e.getMessage());
        }
        System.out.println(logs);
    }


    private void deleteLogFromTour(int tourId, TourLog log) {
        Tour tour = tours.stream().filter(t -> t.getUid() == tourId).findAny().get();
        int index = tours.indexOf(tour);
        tour.deleteLog(log);
        tour.setPopularity();
        tour.setChildFriendly(calculateChildFriendliness(tour.getLength(), tour.getLogs()));
        updateTour(tourId, tour.getPopularity(), tour.getChildFriendly());
        tours.set(index, tour);
    }

    private void updateLogInTour(int tourId, TourLog log) {
        Tour tour = tours.stream().filter(t -> t.getUid() == tourId).findAny().get();
        int index = tours.indexOf(tour);
        tour.updateLog(log);
        tour.setPopularity();
        tour.setChildFriendly(calculateChildFriendliness(tour.getLength(), tour.getLogs()));
        updateTour(tourId, tour.getPopularity(), tour.getChildFriendly());
        tours.set(index, tour);
    }

    private void updateTour(int tourId, int popularity, int childFriendliness) {
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

    public void importLogsByTourId(Tour importedTour, List<TourLog> importedLogs) {
        for (TourLog log : importedLogs) {
            log.setTourID(importedTour.getUid());
            try {
                log.setUid(tourLogRepository.getNewestLogId());
                tourLogRepository.addLog(log);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("An error occurred while trying to add a new log to the database; Log: " + log + ";\n" + e.getMessage());
            }
            logs.add(log);
        }
        int index = tours.indexOf(importedTour);
        tours.set(index, importedTour);
    }

    public ObservableList<TourLog> getObservableLogList() {
        return this.logs;
    }


    private void assignLogsToTours() {
        for (Tour tour : tours) {
            tour.setLogs(new LinkedList<>());
            for (TourLog log : logs) {
                if (tour.getUid() == log.getTourID()) {
                    tour.insertLog(log);
                }
            }
            tour.setPopularity();
            tour.setChildFriendly(calculateChildFriendliness(tour.getLength(), tour.getLogs()));
        }
    }

    private void resetLogsInTours() {
        tours.forEach(tour -> tour.setLogs(new LinkedList<>()));
    }

    // a tour was deleted so the associated tour logs also need to be deleted from the observable log list
    private void deleteTourLogs() {
        List<Integer> tourIds = tours.stream()
                .map(Tour::getUid)
                .collect(Collectors.toList());

        List<TourLog> toDelete = logs.stream().filter(l -> !tourIds.contains(l.getTourID())).collect(Collectors.toList());
        logs.removeAll(toDelete);
    }

    public void addDemoLogs() {
        TourLog tourALog1 = toObject("{\"date\":1653343200000,\"comment\":\"Some light traffic on the way there, but still had a great time.\",\"difficulty\":5,\"totalTime\":{\"totalTime\":\"01:04:00\"},\"rating\":5}", TourLog.class);
        tourALog1.setUid(1);
        tourALog1.setTourID(1);
        TourLog tourALog2 = toObject("{\"date\":1653429600000,\"comment\":\"\",\"difficulty\":2,\"totalTime\":{\"totalTime\":\"00:52:00\"},\"rating\":5}", TourLog.class);
        tourALog2.setUid(2);
        tourALog2.setTourID(1);
        TourLog tourALog3 = toObject("{\"date\":1652220000000,\"comment\":\"There's construction on the highway, took us a lot longer to get there than expected.\",\"difficulty\":15,\"totalTime\":{\"totalTime\":\"01:20:00\"},\"rating\":4}", TourLog.class);
        tourALog3.setUid(3);
        tourALog3.setTourID(1);

        TourLog tourCLog = toObject("{\"date\":1652479200000,\"comment\":\"Exactly what I needed after a long day of sitting at the office.\",\"difficulty\":50,\"totalTime\":{\"totalTime\":\"00:48:00\"},\"rating\":5}", TourLog.class);
        tourCLog.setUid(4);
        tourCLog.setTourID(3);

        TourLog tourDLog1 = toObject("{\"date\":1651788000000,\"comment\":\"The long journey was worth this destination.\",\"difficulty\":40,\"totalTime\":{\"totalTime\":\"07:13:00\"},\"rating\":5}", TourLog.class);
        tourDLog1.setUid(5);
        tourDLog1.setTourID(4);
        TourLog tourDLog2 = toObject("{\"date\":1652997600000,\"comment\":\"There was a lot of traffic, but I also left on a Friday after work so...\",\"difficulty\":37,\"totalTime\":{\"totalTime\":\"07:26:00\"},\"rating\":4}", TourLog.class);
        tourDLog2.setUid(6);
        tourDLog2.setTourID(4);

        TourLog tourELog = toObject("{\"date\":1653688800000,\"comment\":\"I drove my car into the water! Because the GPS told me to!\",\"difficulty\":74,\"totalTime\":{\"totalTime\":\"05:30:00\"},\"rating\":1}", TourLog.class);
        tourELog.setUid(7);
        tourELog.setTourID(5);

        try {
            tourLogRepository.addLog(tourALog1);
            tourLogRepository.addLog(tourALog2);
            tourLogRepository.addLog(tourALog3);
            tourLogRepository.addLog(tourCLog);
            tourLogRepository.addLog(tourDLog1);
            tourLogRepository.addLog(tourDLog2);
            tourLogRepository.addLog(tourELog);

            logs.add(tourALog1);
            logs.add(tourALog2);
            logs.add(tourALog3);
            logs.add(tourCLog);
            logs.add(tourDLog1);
            logs.add(tourDLog2);
            logs.add(tourELog);
            assignLogsToTours();
        } catch (SQLException e) {
            logger.error("Error while saving demo logs");
            e.printStackTrace();
        }


    }

}
