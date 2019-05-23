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

public class progressItemsViewHolder extends RecyclerView.ViewHolder {
    private TextView taskDate;
    private TextView taskDuration;
    private TextView taskTitle;
    private TextView taskDesc;
    private TextView taskStart;
    private TextView taskEnd;
    private CardView cardView;
    private TextView sideMenu;

    public progressItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        taskDate=itemView.findViewById(R.id.progress_task_date);
        taskDuration=itemView.findViewById(R.id.progress_task_duration);
        taskTitle=itemView.findViewById(R.id.progress_task_title);
        taskDesc=itemView.findViewById(R.id.progress_task_desc);
        taskStart=itemView.findViewById(R.id.progress_task_start_task);
        taskEnd=itemView.findViewById(R.id.progress_task_end_task);
        cardView=itemView.findViewById(R.id.progress_task_cardView);
        sideMenu=itemView.findViewById(R.id.progress_task_side_menu);
    }

    public void populate(final Context context, final TaskDetails details,
                         final progressTaskAdapter.progressItemClicked itemClicked){

        taskDate.setText(details.getTaskdate());
        taskDuration.setText(details.getTaskStartTime() + " - " + details.getTaskEndTime());
        taskTitle.setText(details.getTaskTitle());
        taskDesc.setText(details.getTaskdesc());
        taskStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked.onProgressStartTask(details);
            }
        });

        taskEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked.onProgressCompleteTask(details);
            }
        });

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu=new PopupMenu(context,sideMenu);
                menu.setGravity(Gravity.RIGHT);
                menu.getMenuInflater().inflate(R.menu.progress_side_menu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){


                            case R.id.side_menu_delay:

                                itemClicked.onProgressTaskDelay(details);

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
