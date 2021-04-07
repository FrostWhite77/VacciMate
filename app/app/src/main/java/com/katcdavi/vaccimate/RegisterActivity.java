package com.katcdavi.vaccimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<String> countries;
    private String selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar tb = (Toolbar) findViewById(R.id.reg_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.registration));

        this.countries = this.getSupportedCountries();
        Spinner countriesSpinner = (Spinner) findViewById(R.id.reg_in_country);
        countriesSpinner.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, countries);
        countriesSpinner.setAdapter(adapter);
        this.selectedCountry = this.countries.get(0);
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
        myIntent.putExtra("COUNTRY", this.selectedCountry);

        startActivity(myIntent);
        finish();
    }

    private List<String> getSupportedCountries() {
        List<String> strings = new ArrayList<>();
        strings.add("Czech Republic");
        strings.add("Test");
        strings.add("UK");
        return strings;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedCountry = this.countries.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}