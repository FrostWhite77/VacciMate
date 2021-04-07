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
import android.widget.Toast;

import com.katcdavi.vaccimate.modules.Gender;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private List<String> countries;
    private String selectedCountry;

    private List<Gender> genders;
    private Gender selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar tb = (Toolbar) findViewById(R.id.reg_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.registration));

        this.countries = this.getSupportedCountries();
        this.selectedCountry = this.countries.get(0);

        Spinner countriesSpinner = (Spinner) findViewById(R.id.reg_in_country);
        countriesSpinner.setOnItemSelectedListener(this);
        countriesSpinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, this.countries));

        this.genders = this.getGenders();
        this.selectedGender = this.genders.get(0);

        Spinner gendersSpinner = (Spinner) findViewById(R.id.reg_in_gender);
        gendersSpinner.setOnItemSelectedListener(this);
        gendersSpinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, this.genders));
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
        myIntent.putExtra("GENDER", this.selectedGender.toString());

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

    private List<Gender> getGenders() {
        List<Gender> genders = new ArrayList<>();
        genders.add(Gender.male());
        genders.add(Gender.female());
        return genders;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.reg_in_country) {
            this.selectedCountry = this.countries.get(position);
            Toast.makeText(getApplicationContext(), "Country: " + this.selectedCountry, Toast.LENGTH_LONG).show();
        }

        if (parent.getId() == R.id.reg_in_gender) {
            this.selectedGender = this.genders.get(position);
            Toast.makeText(getApplicationContext(), "Gender: " + this.selectedGender, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}