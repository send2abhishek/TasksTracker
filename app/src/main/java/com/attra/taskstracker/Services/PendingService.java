package com.attra.taskstracker.Services;

import com.attra.taskstracker.Infrastructure.ServiceResponse;

public class PendingService {

    private PendingService() {
    }

    public static class pendingAcceptTaskUpdateRequest{

        public String taskId;
        public String taskStatus;

        public pendingAcceptTaskUpdateRequest(String taskId, String taskStatus) {
            this.taskId = taskId;
            this.taskStatus = taskStatus;
        }
    }

    public static class pendingAcceptTaskUpdateResponse extends ServiceResponse{

    }
}
