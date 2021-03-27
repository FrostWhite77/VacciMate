package com.katcdavi.vaccimate;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.katcdavi.vaccimate.modules.UserDataModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private UserDataModule userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.main_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.home));

        if (!auth()) {
            goToUserRegistration();
        }

        showUserData();
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
            return true;
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
}