package com.katcdavi.vaccimate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.katcdavi.vaccimate.modules.Country;
import com.katcdavi.vaccimate.modules.CountryDataModule;
import com.katcdavi.vaccimate.modules.CryptoModule;
import com.katcdavi.vaccimate.adapters.EventsAdapter;
import com.katcdavi.vaccimate.modules.DataStore;
import com.katcdavi.vaccimate.modules.Gender;
import com.katcdavi.vaccimate.modules.UserStore;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgram;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgramLoader;
import com.katcdavi.vaccimate.userdb.User;
import com.katcdavi.vaccimate.userdb.UserRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static Context appContext;

    public static Context getContext() {
        return MainActivity.appContext;
    }

    private UserStore userStore;
    private VaccinationProgram program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.appContext = getApplicationContext();

        Toolbar tb = (Toolbar) findViewById(R.id.main_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.home));

        this.userStore = DataStore.getInstance().getUserStore();
        try {
            // no user has been yet registered, go to registration
            if (this.processRegistration() || this.userStore.isLoggedIn() || this.processLogIn()) {
                startApp();
            } else if (!this.userStore.isAvailable()) {
                goToUserRegistration();
            } else {
                goToUserAuth();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnNavNewRecordOnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), NewRecordActivity.class);
        startActivity(myIntent);
    }

    private void goToUserRegistration() {
        Intent myIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(myIntent);
    }

    private void goToUserAuth() {
        Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(myIntent);
    }

    private void startApp() {
        showUserData();
        loadVaccinationProgram();
        DataStore.getInstance().setProgram(this.program);
    }

    private boolean processLogIn() {
        try {
            if (this.userStore == null) {
                return false;
            }

            String pin = null;
            if (getIntent() != null) {
                pin = getIntent().getStringExtra("PIN");
            }
            if (pin == null || pin.isEmpty()) {
                return false;
            }

            return this.userStore.logIn(pin);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean processRegistration() {
        try {
            String nationalId = null;
            String username = null;
            String bdateStr = null;
            String pin = null;
            String countryId = null;
            String gender = null;

            if (getIntent() != null) {
                nationalId = getIntent().getStringExtra("NATIONAL_ID");
                username = getIntent().getStringExtra("USERNAME");
                bdateStr = getIntent().getStringExtra("BDATE_STR");
                pin = getIntent().getStringExtra("PIN");
                countryId = getIntent().getStringExtra("COUNTRY");
                gender = getIntent().getStringExtra("GENDER");
            }

            if (nationalId == null || nationalId.isEmpty() || username == null || username.isEmpty() || bdateStr == null || bdateStr.isEmpty() || countryId == null || countryId.isEmpty() || gender == null || gender.isEmpty()) {
                return false;
            }

            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(bdateStr);
            String secret = CryptoModule.pinToSecret(nationalId, username, date, pin);
            Gender parsedGender = Gender.fromString(gender);
            Country country = CountryDataModule.getInstance().getCountryById(countryId);

            User user = new User();
            user.setNationalId(nationalId);
            user.setUsername(username);
            user.setBirthDate(date);
            user.setSecret(secret);
            user.setCountry(country);
            user.setGender(parsedGender);

            UserRepository ur = UserRepository.getInstance();
            ur.insertUser(user);

            this.userStore.setUser(user);
            return this.userStore.logIn(pin);
        } catch (Exception e) {
            return false;
        }
    }

    private void showUserData() {
        TextView tv = null;

        tv = (TextView) findViewById(R.id.main_userInfo_rowNationalId_colVal);
        tv.setText(this.userStore.getNationalId());

        tv = (TextView) findViewById(R.id.main_userInfo_rowUsername_colVal);
        tv.setText(this.userStore.getUsername());

        tv = (TextView) findViewById(R.id.main_userInfo_rowBdate_colVal);
        tv.setText(this.userStore.getBdateStr());

        tv = (TextView) findViewById(R.id.main_userInfo_rowCountry_colVal);
        tv.setText(this.userStore.getCountry().getName());

        tv = (TextView) findViewById(R.id.main_userInfo_rowGender_colVal);
        tv.setText(this.userStore.getGender().toString());
    }

    private void loadVaccinationProgram() {
        TextView tv = (TextView) findViewById(R.id.main_errorLog);
        try {
            this.program = VaccinationProgramLoader.loadFromFile(getAssets().open("structure_test.json"));

            // region: DEBUG - begin
            String data = "===DEBUG===\nLoaded Categories: " + this.program.getCategoriesSize() + " ; Loaded Events: " + this.program.getEventsSize();
            DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            data += "\nmax width: " + dpWidth + "dp ; max height: " + dpHeight + "dp\n===DEBUG===";
            tv.setText(data);
            // region: DEBUG - end

            RecyclerView eventsRView = (RecyclerView) findViewById(R.id.main_upcomingEvents);
            eventsRView.setAdapter(new EventsAdapter(this.program.getEvents()));
            eventsRView.setLayoutManager(new LinearLayoutManager(this));

        } catch (IOException e) {
            e.printStackTrace();
            tv.setText("ERROR in loadVaccinationProgram()");
        }
    }
}