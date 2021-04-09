package com.katcdavi.vaccimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar tb = (Toolbar) findViewById(R.id.login_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.login));
    }

    public void btnLoginOnClick(View v) {
        TextView tv_pin = (TextView) findViewById(R.id.login_in_pin);
        String pin = tv_pin.getText().toString();

        if (pin.length() != 4) {
            return;
        }

        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        myIntent.putExtra("PIN", pin);

        startActivity(myIntent);
    }
}