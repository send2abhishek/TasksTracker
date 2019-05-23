package com.attra.taskstracker.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.attra.taskstracker.R;
import com.attra.taskstracker.Utils.BaseUtils;
import com.attra.taskstracker.Views.TabViews;

public class HomepageActivity extends BaseActivity implements View.OnClickListener {

    public static final String USERNAME="username";
    public static final String NAME="name";
    public static String nameFromIntent=null;
    public static String emailFromIntent=null;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout layout;
    private TextView profileName;
    private TextView profileEmail;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabViews adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        nameFromIntent=getIntent().getStringExtra(NAME);
        emailFromIntent=getIntent().getStringExtra(USERNAME);
        if(nameFromIntent==null && emailFromIntent==null){
            nameFromIntent=name;
            emailFromIntent=username;
            Toast.makeText(this,"Welcome "+ nameFromIntent,Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Welcome "+nameFromIntent,Toast.LENGTH_LONG).show();
        }
        setUpToolbar();

    }

    private void setUpToolbar() {

        drawerLayout=(DrawerLayout)findViewById(R.id.activity_home_drawer_layout);
        toolbar=(Toolbar)findViewById(R.id.activity_home_toolbar);
        layout=(LinearLayout)findViewById(R.id.navigation_profile_logout);
        profileName=(TextView)findViewById(R.id.navigation_profile_name);
        profileEmail=(TextView)findViewById(R.id.navigation_profile_username);
        profileName.setText(nameFromIntent);
        profileEmail.setText(emailFromIntent);
        tabLayout=(TabLayout)findViewById(R.id.activity_home_tablayout);
        viewPager=(ViewPager)findViewById(R.id.activity_home_pager);
        adapter=new TabViews(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        layout.setOnClickListener(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(drawerLayout.isDrawerOpen(Gravity.START)){

                    drawerLayout.closeDrawer(Gravity.START);
                }

                else {

                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(BaseUtils.USER_EMAIL,null);
        editor.putString(BaseUtils.USER_PASSWORD,null);
        editor.putString(BaseUtils.USER_NAME,null);
        editor.commit();

        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.activity_main_add_task_menu){

            Intent intent=new Intent(this,AddTaskActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
