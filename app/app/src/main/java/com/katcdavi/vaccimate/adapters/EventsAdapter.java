package com.katcdavi.vaccimate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.katcdavi.vaccimate.R;
import com.katcdavi.vaccimate.modules.vaccinationProgram.VaccinationEvent;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleView);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VaccinationEvent event = this.events.get(position);
        TextView textView = holder.titleTextView;
        textView.setText(event.getId() + ". [" + event.getCategory().getName() + "] " + event.getNote() + " (A: " + event.getRecommendedAge() + "yrs, G: " + event.getRecommendedGender() + ")");
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }
}
