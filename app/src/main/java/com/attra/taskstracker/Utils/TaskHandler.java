package com.attra.taskstracker.Utils;

import java.util.ArrayList;

public interface TaskHandler {


    void onTaskStart();
    void onPauseTask();
    void setCurrentTask(ArrayList<TaskDetails> currentTasks, ArrayList<TaskDetails> AllTasks);
    void TaskCompleted();
    boolean isTaskStarted();
    boolean isTaskCompleted();

}
