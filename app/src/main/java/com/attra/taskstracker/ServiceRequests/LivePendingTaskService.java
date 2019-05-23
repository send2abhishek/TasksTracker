package com.attra.taskstracker.ServiceRequests;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.widget.Toast;

import com.attra.taskstracker.Database.SqliteHelper;
import com.attra.taskstracker.Infrastructure.TaskTrackerApp;
import com.attra.taskstracker.Providers.TaskProvider;
import com.attra.taskstracker.Services.PendingService;
import com.squareup.otto.Subscribe;

public class LivePendingTaskService extends BaseServiceRequest {
    public LivePendingTaskService(TaskTrackerApp trackerApp) {
        super(trackerApp);
    }


    @Subscribe
    public void onTaskUpdateRequest(PendingService.pendingAcceptTaskUpdateRequest request){

        PendingService.pendingAcceptTaskUpdateResponse response=new PendingService.pendingAcceptTaskUpdateResponse();

        if(request.taskId!=null && request.taskStatus!=null){

            ContentResolver resolver=trackerApp.getContentResolver();

            ContentValues values=new ContentValues();
            values.put(SqliteHelper.TASK_STATUS,request.taskStatus);
            int rows=resolver.update(TaskProvider.CONTENT_URI_TASK,values,SqliteHelper.TASK_ID +" =?",new String[]{request.taskId});

            if(rows==1){

                Toast.makeText(trackerApp.getApplicationContext(),"Task Updated",Toast.LENGTH_LONG).show();
                bus.post(response);
            }


        }
    }
}
