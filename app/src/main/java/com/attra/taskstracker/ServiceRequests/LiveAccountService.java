package com.attra.taskstracker.ServiceRequests;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

import com.attra.taskstracker.Activities.HomepageActivity;
import com.attra.taskstracker.Database.SqliteHelper;
import com.attra.taskstracker.Infrastructure.TaskTrackerApp;
import com.attra.taskstracker.Providers.TaskProvider;
import com.attra.taskstracker.Services.AccountService;
import com.attra.taskstracker.Utils.BaseUtils;
import com.squareup.otto.Subscribe;

public class LiveAccountService extends BaseServiceRequest {
    public LiveAccountService(TaskTrackerApp trackerApp) {
        super(trackerApp);
    }

    @Subscribe
    public void onLoginRequest(AccountService.LoginRequestRequest request){

        AccountService.LoginRequestResponse response=new AccountService.LoginRequestResponse();

        if(request.username.isEmpty()){
            response.setPropertyErrors("usernameEmpty","Username can't be empty");
        }

        else if(request.Password.isEmpty()){
            response.setPropertyErrors("passwordEmpty","Password can't be empty");
        }

        else {

            if(response.didSucceed()){


                ContentResolver resolver=trackerApp.getContentResolver();

                Cursor cursor=resolver.query(TaskProvider.CONTENT_URI,
                        SqliteHelper.TABLE_ALL_COLUMNS,SqliteHelper.EMAIL + "=? AND "
                                + SqliteHelper.PASSWORD + "=?"
                        , new String[]{request.username,request.Password},null);

                if(cursor!=null){
                    int row=cursor.getCount();
                    if(row==1){
                        response.cursor=cursor;
                    }
                    else {
                        Toast.makeText(trackerApp.getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(trackerApp.getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_LONG).show();
                }
            }
        }

        bus.post(response);
    }



    @Subscribe
    public void onRegistrationRequest(final AccountService.RegistrationRequestRequest Regrequest){

        AccountService.RegistrationRequestResponse Regresponse=new AccountService.RegistrationRequestResponse();

        if(Regrequest.name.isEmpty()){
            Regresponse.setPropertyErrors("nameEmpty","Name Can't Be Empty");
        }
        else if(Regrequest.username.isEmpty()){
            Regresponse.setPropertyErrors("usernameEmpty","Email Can't Be Empty");
        }

        else if(Regrequest.Password.isEmpty()){
            Regresponse.setPropertyErrors("PasswordEmpty","Password Can't Be Empty");
        }
        else if(Regrequest.CnfPassword.isEmpty()){
            Regresponse.setPropertyErrors("ConfirmPasswordEmpty","ConfirmPassword Can't Be Empty");
        }

        else if(!(Regrequest.Password.equals(Regrequest.CnfPassword))){

            Toast.makeText(trackerApp.getApplicationContext(),"Password didn't match with confirm password",Toast.LENGTH_LONG).show();
        }

        else {
            Regrequest.progressDialog.setMessage("Registering User");
            Regrequest.progressDialog.setTitle("Please Wait...");
            Regrequest.progressDialog.show();



            ContentResolver resolver=trackerApp.getContentResolver();
            ContentValues values=new ContentValues();
            values.put(SqliteHelper.NAME,Regrequest.name);
            values.put(SqliteHelper.EMAIL,Regrequest.username);
            values.put(SqliteHelper.PASSWORD,Regrequest.Password);

            Regresponse.registrationId=resolver.insert(TaskProvider.CONTENT_URI,values);
            if(Regresponse.registrationId.getLastPathSegment().equals("-1")){

                Toast.makeText(trackerApp.getApplicationContext(),"Something went wrong, Failed to register user",Toast.LENGTH_LONG).show();
                Regrequest.progressDialog.hide();
            }



        }

        bus.post(Regresponse);

    }



    @Subscribe
    public void onTaskAddRequest(AccountService.AddTaskRequest request){

        AccountService.AddTaskResponse response=new AccountService.AddTaskResponse();

        if(request.TaskTitle.isEmpty()){

            response.setPropertyErrors("TaskTitleEmpty","Task Title can't be empty");
        }

        else if(request.TaskDesc.isEmpty()){

            response.setPropertyErrors("TaskDescEmpty","Task Description can't be empty");
        }

        else if(request.TaskDate.isEmpty()){

            response.setPropertyErrors("TaskDateEmpty","Task Date can't be empty");
        }

        else if(request.TaskStartTime.isEmpty()){

            response.setPropertyErrors("TaskDurationEmpty","Task Start time can't be empty");
        }

        else if(request.TaskEndTime.isEmpty()){
            response.setPropertyErrors("TaskEndEmpty","Task end time can't be empty");
        }

        else {

            Toast.makeText(trackerApp.getApplicationContext(),"Task will added soon...",Toast.LENGTH_LONG).show();
            ContentResolver resolver=trackerApp.getContentResolver();
            ContentValues values=new ContentValues();

            values.put(SqliteHelper.TASK_TITLE,request.TaskTitle);
            values.put(SqliteHelper.TASK_DESC,request.TaskDesc);
            values.put(SqliteHelper.TASK_DATE,request.TaskDate);
            values.put(SqliteHelper.TASK_START_TIME,request.TaskStartTime);
            values.put(SqliteHelper.TASK_END_TIME,request.TaskEndTime);
            values.put(SqliteHelper.TASK_STATUS, BaseUtils.TASK_STATUS_PENDING);
            values.put(SqliteHelper.TASK_COMMENTS, "default");
            values.put(SqliteHelper.TASK_USER_NAME, HomepageActivity.emailFromIntent);

            response.AddTaskResponseId=resolver.insert(TaskProvider.CONTENT_URI_TASK,values);
            if(response.AddTaskResponseId.getLastPathSegment().equals("-1")){

                Toast.makeText(trackerApp.getApplicationContext(),"Something went wrong, Failed to register task",Toast.LENGTH_LONG).show();

            }
        }

        bus.post(response);
    }
}
