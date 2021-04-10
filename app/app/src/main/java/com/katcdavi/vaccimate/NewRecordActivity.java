package com.katcdavi.vaccimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.katcdavi.vaccimate.modules.DataStore;
import com.katcdavi.vaccimate.modules.vaccinationProgram.DiseaseCategory;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationEvent;

import java.util.List;

public class NewRecordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private VaccinationEvent selectedEvent;
    private List<VaccinationEvent> events;

    private DiseaseCategory selectedCategory;
    private List<DiseaseCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);

        Toolbar tb = (Toolbar) findViewById(R.id.newrec_topToolbar);
        tb.setTitle(getResources().getString(R.string.app_name) + " - " + getResources().getString(R.string.newRecord));

        this.events = DataStore.getInstance().getProgram().getEvents();
        this.selectedEvent = this.events.get(0);

        Spinner eventIdSpinner = (Spinner) findViewById(R.id.newrec_in_programEventId);
        eventIdSpinner.setOnItemSelectedListener(this);
        eventIdSpinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, this.events));

        this.categories = DataStore.getInstance().getProgram().getCategories();
        this.selectedCategory = this.categories.get(0);

        Spinner categorySpinner = (Spinner) findViewById(R.id.newrec_in_category);
        categorySpinner.setOnItemSelectedListener(this);
        categorySpinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, this.categories));
    }

    public void btnNewRecordOnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.newrec_in_programEventId) {
            this.selectedEvent = this.events.get(position);
        }

        if (parent.getId() == R.id.newrec_in_category) {
            this.selectedCategory = this.categories.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}