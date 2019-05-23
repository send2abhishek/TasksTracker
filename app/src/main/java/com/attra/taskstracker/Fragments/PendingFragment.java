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
import com.attra.taskstracker.Services.PendingService;
import com.attra.taskstracker.Utils.BaseUtils;
import com.attra.taskstracker.Utils.TaskDetails;
import com.attra.taskstracker.Views.PendingTaskAdapter;
import com.squareup.otto.Subscribe;

public class PendingFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, PendingTaskAdapter.TaskStateClicked {

    private RecyclerView recyclerView;
    private PendingTaskAdapter adapter;



    public static PendingFragment newInstance(){

        return new PendingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview=inflater.inflate(R.layout.pending_items,container,false);
        recyclerView=(RecyclerView)rootview.findViewById(R.id.pending_fragment_recyler_view);
        adapter=new PendingTaskAdapter(getActivity(),this);

        return rootview;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {



        return new CursorLoader(getActivity(), TaskProvider.CONTENT_URI_TASK, SqliteHelper.TABLE_TASK_ALL_COLUMNS,
                SqliteHelper.TASK_USER_NAME+ " =? AND "+ SqliteHelper.TASK_STATUS+" =?",new String[]{HomepageActivity.emailFromIntent, BaseUtils.TASK_STATUS_PENDING},
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

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onTaskAccept(TaskDetails taskDetails) {

        bus.post(new PendingService.pendingAcceptTaskUpdateRequest(taskDetails.getTaskId(),BaseUtils.TASK_STATUS_PROGRESS));
    }

    @Override
    public void onTaskReject(TaskDetails taskDetails) {
        Toast.makeText(getActivity(),"Task will rejected "+taskDetails.getTaskTitle(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskEdit(TaskDetails taskDetails) {
        Toast.makeText(getActivity(),"Task will edited soon  "+taskDetails.getTaskTitle(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskDelete(TaskDetails taskDetails) {
        Toast.makeText(getActivity(),"Task will deleted soon  "+taskDetails.getTaskTitle(),Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onDataUpdated(PendingService.pendingAcceptTaskUpdateResponse response){

        Toast.makeText(getActivity(),"data reset",Toast.LENGTH_LONG).show();
        getLoaderManager().restartLoader(0,null,this);
    }
}
