package com.attra.taskstracker.Views;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.attra.taskstracker.Fragments.DoneFragment;
import com.attra.taskstracker.Fragments.InprogressFragment;
import com.attra.taskstracker.Fragments.PendingFragment;

public class TabViews extends FragmentStatePagerAdapter {

    public TabViews(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment returnFragment;

        switch(position){

            case 0:

                returnFragment= PendingFragment.newInstance();
                break;

            case 1:

                returnFragment= InprogressFragment.newInstance();
                break;

            case 2:
                returnFragment= DoneFragment.newInstance();
                break;

            default:
                return null;

        }

        return returnFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch(position){

            case 0:

                title="Pending";
                break;

            case 1:

                title="In Progress";
                break;

            case 2:
                title="Done";
                break;

            default:
                return null;

        }
        return title;
    }
}
