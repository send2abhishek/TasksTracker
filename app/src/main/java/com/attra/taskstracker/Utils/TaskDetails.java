package com.attra.taskstracker.Utils;

public class TaskDetails {

    private String taskId;
    private String taskTitle;
    private String taskdesc;
    private String taskdate;
    private String taskStartTime;
    private String taskEndTime;

    public TaskDetails(String taskId, String taskTitle, String taskdesc,
                       String taskdate, String taskStartTime, String taskEndTime) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskdesc = taskdesc;
        this.taskdate = taskdate;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskdesc() {
        return taskdesc;
    }

    public String getTaskdate() {
        return taskdate;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }
}
