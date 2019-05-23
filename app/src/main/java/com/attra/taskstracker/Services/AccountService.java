package com.attra.taskstracker.Services;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;

import com.attra.taskstracker.Infrastructure.ServiceResponse;

public class AccountService {


    private AccountService() {
    }


    public static class LoginRequestRequest {

        public String username;
        public String Password;
        public ProgressDialog progressDialog;

        public LoginRequestRequest(String username, String password, ProgressDialog progressDialog) {
            this.username = username;
            Password = password;
            this.progressDialog = progressDialog;
        }
    }


    public static class LoginRequestResponse extends ServiceResponse {

        public Cursor cursor=null;
    }


    public  static  class RegistrationRequestRequest{
        public String name;
        public String username;
        public String Password;
        public String CnfPassword;
        public ProgressDialog progressDialog;

        public RegistrationRequestRequest(String name, String username, String password,
                                          String cnfPassword, ProgressDialog progressDialog) {
            this.name = name;
            this.username = username;
            Password = password;
            CnfPassword = cnfPassword;
            this.progressDialog = progressDialog;
        }
    }
    public static class RegistrationRequestResponse extends ServiceResponse {
        public Uri registrationId;

    }

    public static class AddTaskRequest{
        public String TaskTitle;
        public String TaskDesc;
        public String TaskDate;
        public String TaskStartTime;
        public String TaskEndTime;

        public AddTaskRequest(String taskTitle, String taskDesc, String taskDate, String taskStartTime, String taskEndTime) {
            TaskTitle = taskTitle;
            TaskDesc = taskDesc;
            TaskDate = taskDate;
            TaskStartTime = taskStartTime;
            TaskEndTime = taskEndTime;
        }
    }

    public static class AddTaskResponse extends ServiceResponse {
        public Uri AddTaskResponseId;

    }
}
