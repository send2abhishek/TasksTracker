package com.attra.taskstracker.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.attra.taskstracker.R;
import com.attra.taskstracker.Services.AccountService;
import com.squareup.otto.Subscribe;

public class RegistrationActivity extends BaseActivity {

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button regBtn;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraion);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        name=(EditText)findViewById(R.id.activity_reg_name);
        email=(EditText)findViewById(R.id.activity_reg_username);
        password=(EditText)findViewById(R.id.activity_reg_Password);
        confirmPassword=(EditText)findViewById(R.id.activity_reg_cnfPassword);
        regBtn=(Button)findViewById(R.id.activity_reg_sign_btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bus.post(new AccountService.RegistrationRequestRequest(name.getText().toString().trim(),
                        email.getText().toString().trim(),
                        password.getText().toString().trim(),
                        confirmPassword.getText().toString().trim(),
                        dialog));
            }
        });
    }


    @Subscribe
    public void onRegistrationRequestResponse(final AccountService.RegistrationRequestResponse requestResponse){
        Handler handler=new Handler();
        if(!requestResponse.didSucceed()){
            name.setError(requestResponse.getPropertyErrors("nameEmpty"));
            email.setError(requestResponse.getPropertyErrors("usernameEmpty"));
            password.setError(requestResponse.getPropertyErrors("PasswordEmpty"));
            confirmPassword.setError(requestResponse.getPropertyErrors("ConfirmPasswordEmpty"));
        }
        else {

            if(requestResponse.registrationId.getLastPathSegment().equals("-1")){
                dialog.hide();

            }
            else {
                Runnable taskDelay=new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegistrationActivity.this,"User Registered Successfully"+requestResponse.registrationId.getLastPathSegment()
                                ,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();

                        dialog.hide();
                    }
                };

                handler.postDelayed(taskDelay,3000);
            }

        }
    }
}
