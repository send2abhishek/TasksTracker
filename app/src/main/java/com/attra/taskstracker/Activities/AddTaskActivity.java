package com.attra.taskstracker.Activities;

import android.app.DatePickerDialog;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Services.AccountService;
import com.squareup.otto.Subscribe;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends BaseActivity implements View.OnClickListener {

    private EditText taskTitle;
    private EditText taskDesc;
    private EditText taskDate;
    private EditText taskStartTime;
    private EditText taskEndTime;
    private Button addTaskBtn;
    private Toolbar toolbar;
    private String dataDuration;
    private String text;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private int currentHour;
    private int currentMinute;
    private String amPm;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskTitle=(EditText)findViewById(R.id.activity_task_title);
        taskDesc=(EditText)findViewById(R.id.activity_add_task_dsc);
        taskDate=(EditText)findViewById(R.id.activity_add_task_date);
        taskStartTime=(EditText)findViewById(R.id.activity_add_task_start_time);
        taskEndTime=(EditText)findViewById(R.id.activity_add_task_end_time);
        taskEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog=new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time;
                        if (hourOfDay >= 12) {
                            time = " PM";
                            hourOfDay=hourOfDay-12;
                        } else {
                            time = " AM";
                        }

                        taskEndTime.setText(String.format("%02d:%02d", hourOfDay, minute) + time);


                    }
                },currentHour, currentMinute,false);

                dialog.show();
            }
        });
        taskStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog=new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = " PM";
                            hourOfDay=hourOfDay-12;
                        } else {
                            amPm = " AM";
                        }

                        taskStartTime.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                    }
                },currentHour, currentMinute,false);

                timePickerDialog.show();
            }
        });
        addTaskBtn=(Button)findViewById(R.id.activity_add_taskAdd_btn);
        setUpControl();
        addTaskBtn.setOnClickListener(this);




    }

    private void setUpControl() {

        toolbar=(Toolbar)findViewById(R.id.activity_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Your Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        taskDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
                if(hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dialog = new DatePickerDialog(
                            AddTaskActivity.this,R.style.Theme_AppCompat_DayNight_Dialog,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    Calendar c = Calendar.getInstance();
                                    c.set(Calendar.YEAR, year);
                                    c.set(Calendar.MONTH, month);
                                    c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
                                    taskDate.setText(currentDateString);

                                }
                            },
                            year, month, day);
                    //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }

            }
        });


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {


        bus.post(new AccountService.AddTaskRequest(taskTitle.getText().toString(),taskDesc.getText().toString(),
                taskDate.getText().toString(),taskStartTime.getText().toString(),taskEndTime.getText().toString()
                ));
    }


    @Subscribe
    public void onTaskAddResponse(AccountService.AddTaskResponse response){

        if(!response.didSucceed()){

            taskTitle.setError(response.getPropertyErrors("TaskTitleEmpty"));
            taskDesc.setError(response.getPropertyErrors("TaskDescEmpty"));
            taskDate.setError(response.getPropertyErrors("TaskDateEmpty"));
            taskStartTime.setError(response.getPropertyErrors("taskStartTimeEmpty"));
            taskEndTime.setError(response.getPropertyErrors("TaskEndEmpty"));
        }

        else {

            if(response.AddTaskResponseId.getLastPathSegment().equals("-1")){

            }

            else {

                Toast.makeText(this,"Your New Task Has been registered- "
                        +response.AddTaskResponseId.getLastPathSegment(),Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
