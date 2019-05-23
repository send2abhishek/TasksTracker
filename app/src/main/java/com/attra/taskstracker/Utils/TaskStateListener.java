package com.attra.taskstracker.Utils;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public abstract class TaskStateListener {

    public void onTaskStarted(ArrayList<TaskDetails> allTaskRunning){

    }

    public void onTaskStateChanged(@State int state){

    }

    public void onTaskEnd(){

    }

    @IntDef({State.PENDING,State.RUNNING,State.PAUSED,State.COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State{

        int PENDING=-1;
        int RUNNING=0;
        int PAUSED=1;
        int COMPLETED=2;

    }

}



