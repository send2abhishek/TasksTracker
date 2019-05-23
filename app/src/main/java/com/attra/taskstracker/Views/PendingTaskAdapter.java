package com.attra.taskstracker.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Utils.TaskDetails;
import java.util.ArrayList;

public class PendingTaskAdapter extends RecyclerView.Adapter<pendingItemsViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TaskDetails> taskDetailsArrayList;
    private TaskStateClicked stateClicked;

    public PendingTaskAdapter(Context context,TaskStateClicked stateClicked) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        taskDetailsArrayList=new ArrayList<>();
        this.stateClicked=stateClicked;
    }

    public void AddDataToList(TaskDetails details){

        taskDetailsArrayList.add(details);
    }

    @NonNull
    @Override
    public pendingItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(R.layout.pending_task_view,viewGroup,false);
        pendingItemsViewHolder viewHolder=new pendingItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull pendingItemsViewHolder pendingItemsViewHolder, int position) {

        TaskDetails taskDetails=taskDetailsArrayList.get(position);
        pendingItemsViewHolder.populate(context,taskDetails,stateClicked);

    }

    @Override
    public int getItemCount() {
        return taskDetailsArrayList.size();
    }


    public interface TaskStateClicked{

        void onTaskAccept(TaskDetails taskDetails);
        void onTaskReject(TaskDetails taskDetails);
        void onTaskEdit(TaskDetails taskDetails);
        void onTaskDelete(TaskDetails taskDetails);
    }
}
