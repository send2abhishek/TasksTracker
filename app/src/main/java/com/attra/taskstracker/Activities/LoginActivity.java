package com.attra.taskstracker.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attra.taskstracker.Database.SqliteHelper;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Services.AccountService;
import com.attra.taskstracker.Utils.BaseUtils;
import com.squareup.otto.Subscribe;

public class LoginActivity extends BaseActivity {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private Button regBtn;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.activity_login_username);
        password=(EditText)findViewById(R.id.activity_login_Password);
        loginBtn=(Button)findViewById(R.id.activity_login_sign_btn);
        regBtn=(Button)findViewById(R.id.activity_login_sign_up_btn);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bus.post(new AccountService.LoginRequestRequest(username.getText().toString().trim(),
                        password.getText().toString().trim(),dialog));
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }


    @Subscribe
    public void onLoginResponse(AccountService.LoginRequestResponse response){


        if(!response.didSucceed()){
            username.setError(response.getPropertyErrors("usernameEmpty"));
            password.setError(response.getPropertyErrors("passwordEmpty"));
        }

        else {
                if(response.cursor!=null){


                    String name=null;
                    String password=null;
                    String username=null;
                    while (response.cursor.moveToNext()){

                       username=response.cursor.getString(response.cursor.getColumnIndex(SqliteHelper.EMAIL));
                       password=response.cursor.getString(response.cursor.getColumnIndex(SqliteHelper.PASSWORD));
                       name=response.cursor.getString(response.cursor.getColumnIndex(SqliteHelper.NAME));
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(BaseUtils.USER_EMAIL,username);
                        editor.putString(BaseUtils.USER_PASSWORD,password);
                        editor.putString(BaseUtils.USER_NAME,name);
                        editor.commit();
                    }

                    Intent intent=new Intent(this,HomepageActivity.class);
                    intent.putExtra(HomepageActivity.NAME,name);
                    intent.putExtra(HomepageActivity.USERNAME,username);
                    startActivity(intent);
                    finish();


                }

        }
    }


}
