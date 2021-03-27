package com.katcdavi.vaccimate;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.katcdavi.vaccimate.modules.UserDataModule;
import com.katcdavi.vaccimate.user.User;
import com.katcdavi.vaccimate.user.UserDao;
import com.katcdavi.vaccimate.user.UserDatabase;
import com.katcdavi.vaccimate.user.UserRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.service.autofill.UserData;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDataModule userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.main_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.home));

        if (!loggedIn() && !auth()) {
            goToUserRegistration();
        } else {
            showUserData();
        }
    }

    private void goToUserRegistration() {
        Intent myIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(myIntent);
        finish();
    }

    private boolean auth() {
        try {
            String nationalId = null;
            String username = null;
            String bdateStr = null;

            if (getIntent() != null) {
                nationalId = getIntent().getStringExtra("NATIONAL_ID");
                username = getIntent().getStringExtra("USERNAME");
                bdateStr = getIntent().getStringExtra("BDATE_STR");
            }

            if (nationalId == null || nationalId.isEmpty() || username == null || username.isEmpty() || bdateStr == null || bdateStr.isEmpty()) {
                return false;
            }

            this.userData = new UserDataModule(nationalId, username, bdateStr);
            insertNewUser();

            return true;
        } catch (Exception e) {
            this.userData = null;
            return false;
        }
    }

    private boolean loggedIn() {
        try {
            UserRepository ur = new UserRepository(getApplicationContext());
            List<User> users = ur.getTasks().getValue();

            if (users.size() > 0) {
                User user = users.get(0);
                this.userData = new UserDataModule(user.getNationalId(), user.getUsername(), "12.05.1999");
                return true;
            }

            return false;
        } catch (Exception e) {
            this.userData = null;
            return false;
        }
    }

    private void showUserData() {
        TextView tv = null;

        tv = (TextView) findViewById(R.id.main_userInfo_rowNationalId_colVal);
        tv.setText(this.userData.getNationalId());

        tv = (TextView) findViewById(R.id.main_userInfo_rowUsername_colVal);
        tv.setText(this.userData.getUsername());

        tv = (TextView) findViewById(R.id.main_userInfo_rowBdate_colVal);
        tv.setText(this.userData.getBdateStr());
    }

    private void insertNewUser() {
        UserRepository ur = new UserRepository(getApplicationContext());
        ur.insertUser(this.userData.getNationalId(), this.userData.getUsername());
    }
}