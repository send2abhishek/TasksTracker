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

public class progressTaskAdapter extends RecyclerView.Adapter<progressItemsViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TaskDetails> taskDetailsArrayList;
    private progressItemClicked itemClicked;

    public progressTaskAdapter(Context context,progressItemClicked itemClicked) {
        this.context = context;
        inflater= LayoutInflater.from(context);
        taskDetailsArrayList=new ArrayList<>();
        this.itemClicked=itemClicked;
    }
    public void AddDataToList(TaskDetails details){
        taskDetailsArrayList.add(details);
    }


    @NonNull
    @Override
    public progressItemsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view=inflater.inflate(R.layout.progress_task_layout,viewGroup,false);
        return new progressItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull progressItemsViewHolder progressItemsViewHolder, int position) {

        TaskDetails taskDetails=taskDetailsArrayList.get(position);
        progressItemsViewHolder.populate(context,taskDetails,itemClicked);
    }

    @Override
    public int getItemCount() {
        return taskDetailsArrayList.size();
    }

    public interface progressItemClicked{

        void onProgressStartTask(TaskDetails taskDetails);
        void onProgressCompleteTask(TaskDetails taskDetails);
        void onProgressTaskDelay(TaskDetails taskDetails);
    }
}
