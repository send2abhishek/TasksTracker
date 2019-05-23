package com.attra.taskstracker.ServiceRequests;

import com.attra.taskstracker.Infrastructure.TaskTrackerApp;

public class Module {
    public static void register(TaskTrackerApp trackerApp){

        new LiveAccountService(trackerApp);
        new LivePendingTaskService(trackerApp);
    }
}
