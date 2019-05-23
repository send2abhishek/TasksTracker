package com.attra.taskstracker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.attra.taskstracker.Infrastructure.TaskTrackerApp;
import com.squareup.otto.Bus;

public class BaseFragment extends Fragment {
    protected TaskTrackerApp trackerApp;
    protected Bus bus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trackerApp=(TaskTrackerApp)getActivity().getApplication();
        bus=trackerApp.getBus();
        bus.register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
