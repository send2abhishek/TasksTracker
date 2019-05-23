package com.attra.taskstracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.attra.taskstracker.Database.SqliteHelper;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Utils.BaseUtils;

public class MainActivity extends BaseActivity{

    private SqliteHelper helper;
    private SQLiteDatabase database;

    private Handler handler=new Handler();
    Runnable taskDelay=new Runnable() {
        @Override
        public void run() {


            if(username==null && password==null){
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Intent intent=new Intent(MainActivity.this,HomepageActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(taskDelay,4000);

    }


}
