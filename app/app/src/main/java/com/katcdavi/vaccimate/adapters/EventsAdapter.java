package com.katcdavi.vaccimate.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.katcdavi.vaccimate.MainActivity;
import com.katcdavi.vaccimate.R;
import com.katcdavi.vaccimate.modules.DataStore;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationEvent;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.titleTextView = (TextView) itemView.findViewById(R.id.titleView);
        }
    }

    private List<VaccinationEvent> events;

    public EventsAdapter(List<VaccinationEvent> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VaccinationEvent event = this.events.get(position);
        TextView textView = holder.titleTextView;
        textView.setText(event.getId() + ". [" + event.getCategory().getName() + "] " + event.getNote() + " (A: " + event.getRecommendedAge() + "yrs, G: " + event.getRecommendedGender() + ")");

        VaccinationEvent e = this.events.get(position);
        int age = Period.between(DataStore.getInstance().getUserStore().getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();

        if (age < e.getRecommendedAge()) {
            holder.view.setBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.event_past));
        } else if (age == e.getRecommendedAge()) {
            holder.view.setBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.event_now));
        } else {
            holder.view.setBackgroundColor(ContextCompat.getColor(MainActivity.getContext(), R.color.event_future));
        }
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
