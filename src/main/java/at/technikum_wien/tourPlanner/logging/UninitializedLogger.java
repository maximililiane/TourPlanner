package at.technikum_wien.tourPlanner.logging;

public class UninitializedLogger extends BaseStateLogger {
    @Override
    public void debug(String message) {
        printUninitializedWarning();
        return;
    }

    @Override
    public void info(String message) {
        printUninitializedWarning();
        return;
    }

    @Override
    public void warn(String message) {
        printUninitializedWarning();
        return;
    }

    @Override
    public void error(String message) {
        printUninitializedWarning();
        return;
    }

    @Override
    public void fatal(String message) {
        printUninitializedWarning();
        return;
    }

    public void printUninitializedWarning(){
        System.out.println("Warning, this logger is not initialized");
    }
}
