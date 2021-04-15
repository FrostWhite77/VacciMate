package com.katcdavi.vaccimate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.katcdavi.vaccimate.adapters.VaccinationRecordsAdapter;
import com.katcdavi.vaccimate.modules.Country;
import com.katcdavi.vaccimate.modules.CountryDataModule;
import com.katcdavi.vaccimate.modules.CryptoModule;
import com.katcdavi.vaccimate.adapters.EventsAdapter;
import com.katcdavi.vaccimate.modules.DataStore;
import com.katcdavi.vaccimate.modules.Gender;
import com.katcdavi.vaccimate.modules.UserStore;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationEvent;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgram;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgramLoader;
import com.katcdavi.vaccimate.userdb.User;
import com.katcdavi.vaccimate.userdb.UserRepository;
import com.katcdavi.vaccimate.vaccinedb.Event;
import com.katcdavi.vaccimate.vaccinedb.VaccineRepository;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context appContext;

    public static Context getContext() {
        return MainActivity.appContext;
    }

    private UserStore userStore;
    private VaccinationProgram program;

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startApp() {
        showUserData();

        if (DataStore.getInstance().getProgram() == null) {
            loadVaccinationProgram();
            DataStore.getInstance().setProgram(this.program);
        } else {
            this.program = DataStore.getInstance().getProgram();
        }

        TextView tv = (TextView) findViewById(R.id.main_errorLog);
        tv.setText("===DEBUG===\n");

        VaccineRepository vr = VaccineRepository.getInstance();
        List<Event> userEvents = vr.getEvents();
        RecyclerView myVaccinationsRView = (RecyclerView) findViewById(R.id.main_userEvents);
        myVaccinationsRView.setAdapter(new VaccinationRecordsAdapter(this.sortByDate(userEvents)));
        myVaccinationsRView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView eventsRView = (RecyclerView) findViewById(R.id.main_upcomingEvents);
        eventsRView.setAdapter(new EventsAdapter(this.sortByAge(this.program.getUnassociatedEvents())));
        eventsRView.setLayoutManager(new LinearLayoutManager(this));

        // region: DEBUG - begin
        String data = tv.getText().toString() + "Loaded Categories: " + this.program.getCategoriesSize() + " ; Loaded Events: " + this.program.getEventsSize();
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        data += "\nmax width: " + dpWidth + "dp ; max height: " + dpHeight + "dp\n";
        int records = vr.getEvents().size();
        data += "size of stored user events: " + records + "\n";
        tv.setText(data);
        // region: DEBUG - end

        tv.setText(tv.getText().toString() + "===DEBUG===\n");
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
            this.program = VaccinationProgramLoader.loadFromFile(getAssets().open("structure_test.json"), this.userStore.getGender());
        } catch (IOException e) {
            e.printStackTrace();
            tv.setText("ERROR in loadVaccinationProgram()");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<VaccinationEvent> sortByAge(List<VaccinationEvent> programEvents)
    {
        programEvents.sort(Comparator.comparing(VaccinationEvent::getRecommendedAge));
        return programEvents;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Event> sortByDate(List<Event> userEvents)
    {
        userEvents.sort(Comparator.comparing(Event::getDate));
        return userEvents;
    }
}