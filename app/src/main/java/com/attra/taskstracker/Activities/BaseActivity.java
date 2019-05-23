package com.attra.taskstracker.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.attra.taskstracker.Infrastructure.TaskTrackerApp;
import com.attra.taskstracker.Utils.BaseUtils;
import com.squareup.otto.Bus;

public class BaseActivity extends AppCompatActivity {

    protected Bus bus;
    protected TaskTrackerApp trackerApp;
    private Boolean isRegisteredwithBus=false;
    protected SharedPreferences preferences;
    protected String username=null;
    protected String password=null;
    protected String name=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerApp=(TaskTrackerApp)getApplication();
        bus=trackerApp.getBus();
        bus.register(this);
        isRegisteredwithBus=true;
        preferences=getSharedPreferences(BaseUtils.SHARED_PREFENCES,MODE_PRIVATE);
        username=preferences.getString(BaseUtils.USER_EMAIL,null);
        password=preferences.getString(BaseUtils.USER_PASSWORD,null);
        name=preferences.getString(BaseUtils.USER_NAME,null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(isRegisteredwithBus){
            bus.unregister(this);
            isRegisteredwithBus=false;
        }
    }


    @Override
    public void finish(){
        super.finish();
        if(isRegisteredwithBus){
            bus.unregister(this);
            isRegisteredwithBus=false;
        }

    }


}
