package com.katcdavi.vaccimate;

import android.content.Intent;
import android.os.Bundle;

import com.katcdavi.vaccimate.modules.CryptoModule;
import com.katcdavi.vaccimate.adapters.EventsAdapter;
import com.katcdavi.vaccimate.modules.Gender;
import com.katcdavi.vaccimate.modules.UserDataModule;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgram;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgramLoader;
import com.katcdavi.vaccimate.userdb.User;
import com.katcdavi.vaccimate.userdb.UserRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDataModule userData;
    private VaccinationProgram program;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.main_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.home));

        try {
            if (!isRegistered()) {
                goToUserRegistration();
            } else if (!isLoggedIn()) {
                goToUserAuth();
            } else {
                showUserData();
                loadVaccinationProgram();
            }
        } catch (Exception e) {
            e.printStackTrace();
            int dummy = 1;
        }
    }

    private void goToUserRegistration() {
        Intent myIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void goToUserAuth() {
        Intent myIntent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(myIntent);
        finish();
    }

    private boolean tryRegister() {
        try {
            String nationalId = null;
            String username = null;
            String bdateStr = null;
            String pin = null;
            String country = null;
            String gender = null;

            if (getIntent() != null) {
                nationalId = getIntent().getStringExtra("NATIONAL_ID");
                username = getIntent().getStringExtra("USERNAME");
                bdateStr = getIntent().getStringExtra("BDATE_STR");
                pin = getIntent().getStringExtra("PIN");
                country = getIntent().getStringExtra("COUNTRY");
                gender = getIntent().getStringExtra("GENDER");
            }

            if (nationalId == null || nationalId.isEmpty() || username == null || username.isEmpty() || bdateStr == null || bdateStr.isEmpty() || country == null || country.isEmpty() || gender == null || gender.isEmpty()) {
                return false;
            }

            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(bdateStr);
            String secret = CryptoModule.pinToSecret(nationalId, username, date, pin);
            Gender parsedGender = Gender.fromString(gender);


            this.userData = new UserDataModule(nationalId, username, date, secret, parsedGender, country);
            this.userData.logIn();
            insertNewUser();

            Toast.makeText(getApplicationContext(), "Country: " + country + " ; Gender: " + gender, Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
            this.userData = null;
            return false;
        }
    }

    private boolean isRegistered() {
        try {
            if (tryRegister()) {
                return true;
            }

            UserRepository ur = new UserRepository(getApplicationContext());
            List<User> users = ur.getUsers();

            if (users.size() > 0) {
                User user = users.get(0);
                this.userData = new UserDataModule(user.getNationalId(), user.getUsername(), user.getBirthDate(), user.getSecret(), user.getGender(), user.getCountryId());
                return true;
            }

            return false;
        } catch (Exception e) {
            this.userData = null;
            return false;
        }
    }

    private boolean isLoggedIn() {
        try {
            if (tryLogIn()) {
                return true;
            }
            return this.userData.isLoggedIn();
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

        tv = (TextView) findViewById(R.id.main_userInfo_rowCountry_colVal);
        tv.setText(this.userData.getCountryId());

        tv = (TextView) findViewById(R.id.main_userInfo_rowGender_colVal);
        tv.setText(this.userData.getGender().toString());
    }

    private boolean tryLogIn() {
        try {
            String pin = null;

            if (getIntent() != null) {
                pin = getIntent().getStringExtra("PIN");
            }

            if (pin == null || pin.isEmpty()) {
                return false;
            }

            // verify pin
            if (this.userData.getSecret().equals(CryptoModule.pinToSecret(this.userData, pin))) {
                this.userData.logIn();
                return true;
            }

            return false;
        } catch (Exception e) {
            this.userData = null;
            return false;
        }
    }

    private void insertNewUser() {
        try {
            UserRepository ur = new UserRepository(getApplicationContext());
            ur.insertUser(this.userData.getNationalId(), this.userData.getUsername(), this.userData.getBdate(), this.userData.getSecret());
        } catch (Exception e) {
            System.out.println("Interrupt exception");
        }
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