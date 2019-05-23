package com.attra.taskstracker.Infrastructure;

import android.app.Application;

import com.attra.taskstracker.ServiceRequests.Module;
import com.squareup.otto.Bus;

public class TaskTrackerApp extends Application {

    private Bus bus;


    public TaskTrackerApp() {
       bus=new Bus();
        Module.register(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Bus getBus() {
        return bus;
    }
}
