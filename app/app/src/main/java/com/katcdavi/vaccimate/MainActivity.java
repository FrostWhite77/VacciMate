package com.katcdavi.vaccimate;

import android.content.Intent;
import android.os.Bundle;

import com.katcdavi.vaccimate.modules.CryptoModule;
import com.katcdavi.vaccimate.modules.UserDataModule;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgram;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationProgramLoader;
import com.katcdavi.vaccimate.user.User;
import com.katcdavi.vaccimate.user.UserRepository;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.DisplayMetrics;
import android.widget.TextView;

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

            if (getIntent() != null) {
                nationalId = getIntent().getStringExtra("NATIONAL_ID");
                username = getIntent().getStringExtra("USERNAME");
                bdateStr = getIntent().getStringExtra("BDATE_STR");
                pin = getIntent().getStringExtra("PIN");
            }

            if (nationalId == null || nationalId.isEmpty() || username == null || username.isEmpty() || bdateStr == null || bdateStr.isEmpty()) {
                return false;
            }

            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(bdateStr);
            String secret = CryptoModule.pinToSecret(nationalId, username, date, pin);

            this.userData = new UserDataModule(nationalId, username, date, secret);
            this.userData.logIn();
            insertNewUser();

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
                this.userData = new UserDataModule(user.getNationalId(), user.getUsername(), user.getBirthDate(), user.getSecret());
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
            String data = "Loaded Categories: " + this.program.getCategoriesSize() + " ; Loaded Events: " + this.program.getEventsSize();
            DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            data += "\nmax width: " + dpWidth + "dp ; max height: " + dpHeight + "dp";
            tv.setText(data);
            // region: DEBUG - end

        } catch (IOException e) {
            e.printStackTrace();
            tv.setText("ERROR in loadVaccinationProgram()");
        }
    }
}