package at.technikum_wien.tourPlanner.proxyUtils;

import at.technikum_wien.tourPlanner.database.DatabaseConnector;
import at.technikum_wien.tourPlanner.models.TourLog;
import at.technikum_wien.tourPlanner.models.Tour;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.LinkedList;

class DBProxyTest {

    static DBProxy dbProxy;
    static TourSubscriber tourSubscriber;
    static LogSubscriber logSubscriber;
    static LinkedList<Tour> tourList;
    static LinkedList<TourLog> logList;

    @BeforeAll
    public static void initialSetup() {
        tourList = new LinkedList<Tour>();
        tourList.add(Mockito.mock(Tour.class));
        logList = new LinkedList<TourLog>();
        logList.add(Mockito.mock(TourLog.class));
        tourSubscriber = Mockito.mock(TourSubscriber.class);
        logSubscriber = Mockito.mock(LogSubscriber.class);
    }
    @BeforeEach
    public void testSetup(){
        dbProxy = new DBProxy(Mockito.mock(DatabaseConnector.class));
    }


    @Test
    @DisplayName("Should add Subscriber to the tour list of the Provider")
    void subscribeToTours() {
        Assertions.assertEquals(0, dbProxy.getTourSubscribers().size());
        dbProxy.subscribeToTours(tourSubscriber);
        Assertions.assertEquals(1, dbProxy.getTourSubscribers().size());

    }

    @Test
    @DisplayName("Should add Subscriber to the log list of the Provider")
    void subscribeToLogs() {
        Assertions.assertEquals(0, dbProxy.getLogSubscribers().size());
        dbProxy.subscribeToLogs(logSubscriber);
        Assertions.assertEquals(1, dbProxy.getLogSubscribers().size());
    }

    @Test
    @DisplayName("Should remove Subscriber from the tour list of the Provider")
    void unsubscribeTours() {
        dbProxy.subscribeToTours(tourSubscriber);
        Assertions.assertEquals(1, dbProxy.getTourSubscribers().size());
        dbProxy.unsubscribeTours(tourSubscriber);
        Assertions.assertEquals(0, dbProxy.getTourSubscribers().size());
    }

    @Test
    @DisplayName("Should remove Subscriber from the tour list of the Provider")
    void unsubscribeLogs() {
        dbProxy.subscribeToLogs(logSubscriber);
        Assertions.assertEquals(1, dbProxy.getLogSubscribers().size());
        dbProxy.unsubscribeLogs(logSubscriber);
        Assertions.assertEquals(0, dbProxy.getLogSubscribers().size());
    }

    @Test
    @DisplayName("Should notify all subscribers in the tour list")
    void notifyTourSubscribers() {
        dbProxy.subscribeToTours(tourSubscriber);
        dbProxy.notifyTourSubscribers(tourList);
        Mockito.verify(tourSubscriber).notify(tourList);
    }

    @Test
    @DisplayName("Should notify all subscribers in the log list")
    void notifyLogSubscribers() {
        dbProxy.subscribeToLogs(logSubscriber);
        dbProxy.notifyLogSubscribers(logList);
        Mockito.verify(logSubscriber).notify(logList);
    }
}