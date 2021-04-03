package com.katcdavi.vaccimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar tb = (Toolbar) findViewById(R.id.reg_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.registration));
    }

    public void btnRegisterOnClick(View v) {
        TextView tv_nationalId = (TextView) findViewById(R.id.reg_in_nationalId);
        String nationalId = tv_nationalId.getText().toString();

        TextView tv_username = (TextView) findViewById(R.id.reg_in_username);
        String username = tv_username.getText().toString();

        TextView tv_date = (TextView) findViewById(R.id.reg_in_bdate);
        String bdate = tv_date.getText().toString();

        TextView tv_pin = (TextView) findViewById(R.id.reg_in_pin);
        String pin = tv_pin.getText().toString();

        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        myIntent.putExtra("NATIONAL_ID", nationalId);
        myIntent.putExtra("USERNAME", username);
        myIntent.putExtra("BDATE_STR", bdate);
        myIntent.putExtra("PIN", pin);

        startActivity(myIntent);
        finish();
    }
}