package com.attra.taskstracker.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Utils.TaskDetails;

public class pendingItemsViewHolder extends RecyclerView.ViewHolder{

    private TextView taskDate;
    private TextView taskDuration;
    private TextView taskTitle;
    private TextView taskDesc;
    private TextView taskAccept;
    private TextView taskReject;
    private CardView cardView;
    private TextView sideMenu;
    public pendingItemsViewHolder(@NonNull View itemView) {
        super(itemView);

        taskDate=itemView.findViewById(R.id.pending_task_date);
        taskDuration=itemView.findViewById(R.id.pending_task_duration);
        taskTitle=itemView.findViewById(R.id.pending_task_title);
        taskDesc=itemView.findViewById(R.id.pending_task_desc);

        taskReject=itemView.findViewById(R.id.pending_task_reject_new);
        taskAccept=itemView.findViewById(R.id.pending_task_accept);
        cardView=(CardView)itemView.findViewById(R.id.pending_task_cardView);
        sideMenu=(TextView)itemView.findViewById(R.id.pending_task_side_menu);
    }

    public void populate(final Context context, final TaskDetails details, final PendingTaskAdapter.TaskStateClicked stateClicked){

        taskDate.setText(details.getTaskdate());
        taskDuration.setText(details.getTaskStartTime() + " - " + details.getTaskEndTime());
        taskTitle.setText(details.getTaskTitle());
        taskDesc.setText(details.getTaskdesc());

        taskReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.pending_task_reject_new){
                    stateClicked.onTaskReject(details);
                }

            }
        });

        taskAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.pending_task_accept){
                    stateClicked.onTaskAccept(details);
                }

            }
        });

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu menu=new PopupMenu(context,sideMenu);
                menu.setGravity(Gravity.RIGHT);

                menu.getMenuInflater().inflate(R.menu.recyler_view_side_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){

                            case R.id.side_menu_edit:
                                stateClicked.onTaskEdit(details);
                                break;

                            case R.id.side_menu_del:
                                stateClicked.onTaskDelete(details);
                                break;

                            default:
                                break;
                        }

                        return false;
                    }
                });
                menu.show();
            }
        });


    }


}
