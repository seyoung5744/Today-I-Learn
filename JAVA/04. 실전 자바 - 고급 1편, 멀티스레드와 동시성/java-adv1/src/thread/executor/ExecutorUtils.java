package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        printState(executorService, null);
    }

    public static void printState(ExecutorService executorService, String taskName) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            String message = getStateMessage(poolExecutor);
            log(taskName == null ? message : taskName + " -> " + message);
        } else {
            log(executorService);
        }
    }

    private static String getStateMessage(ThreadPoolExecutor poolExecutor) {
        int pool = poolExecutor.getPoolSize();
        int active = poolExecutor.getActiveCount();
        int queuedTasks = poolExecutor.getQueue().size();
        long completedTask = poolExecutor.getCompletedTaskCount();
        return "[pool=" + pool + ", active=" + active + ", queuedTasks=" + queuedTasks + ", completedTask=" + completedTask + "]";
    }

}
