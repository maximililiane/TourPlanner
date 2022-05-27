package at.technikum_wien.tourPlanner.dataAccessLayer.database;

public enum TableName {
    REGULAR_TOUR_TABLE_NAME("tours"),
    REGULAR_TOUR_LOG_TABLE_NAME("logs"),
    DEMO_TOUR_TABLE_NAME("demo_tours"),
    DEMO_TOUR_LOG_TABLE_NAME("demo_logs");


    private final String tableName;

    TableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return this.tableName;
    }
}
