package com.attra.taskstracker.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.attra.taskstracker.R;

public class DoneFragment extends BaseFragment {


        public static DoneFragment newInstance(){

            return new DoneFragment();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootview=inflater.inflate(R.layout.done_items,container,false);
            return rootview;
        }
}
