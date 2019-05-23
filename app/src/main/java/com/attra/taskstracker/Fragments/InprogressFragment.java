package com.attra.taskstracker.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.attra.taskstracker.Activities.HomepageActivity;
import com.attra.taskstracker.Database.SqliteHelper;
import com.attra.taskstracker.Providers.TaskProvider;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Utils.BaseUtils;
import com.attra.taskstracker.Utils.TaskDetails;
import com.attra.taskstracker.Views.progressTaskAdapter;

public class InprogressFragment extends BaseFragment implements progressTaskAdapter.progressItemClicked,
        LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private progressTaskAdapter adapter;

    public static InprogressFragment newInstance(){

        return new InprogressFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.inprogress_items,container,false);
        recyclerView=rootview.findViewById(R.id.progress_fragment_recyler_view);
        adapter=new progressTaskAdapter(getActivity(),this);
        return rootview;
    }

    @Override
    public void onProgressStartTask(TaskDetails taskDetails) {

        Toast.makeText(getActivity(),taskDetails.getTaskTitle(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProgressCompleteTask(TaskDetails taskDetails) {

    }

    @Override
    public void onProgressTaskDelay(TaskDetails taskDetails) {

    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
         return new CursorLoader(getActivity(), TaskProvider.CONTENT_URI_TASK, SqliteHelper.TABLE_TASK_ALL_COLUMNS,
                SqliteHelper.TASK_USER_NAME+ " =? AND "+SqliteHelper.TASK_STATUS+ " =?",new String[]{HomepageActivity.emailFromIntent, BaseUtils.TASK_STATUS_PROGRESS},
                SqliteHelper.TASK_ID + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        while (cursor.moveToNext()){
            String taskId=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_ID));
            String taskTitle=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_TITLE));
            String taskdesc=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_DESC));
            String taskdate=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_DATE));
            String taskStartTime=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_START_TIME));
            String taskEndTime=cursor.getString(cursor.getColumnIndex(SqliteHelper.TASK_END_TIME));

            adapter.AddDataToList(new TaskDetails(taskId,taskTitle,taskdesc,taskdate,taskStartTime,taskEndTime));
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
