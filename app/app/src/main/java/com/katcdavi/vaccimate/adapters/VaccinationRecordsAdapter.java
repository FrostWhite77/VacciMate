package com.katcdavi.vaccimate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.katcdavi.vaccimate.R;
import com.katcdavi.vaccimate.modules.DataStore;
import com.katcdavi.vaccimate.vaccinedb.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VaccinationRecordsAdapter extends RecyclerView.Adapter<VaccinationRecordsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleView);
        }
    }

    private List<Event> events;

    public VaccinationRecordsAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public VaccinationRecordsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_event, parent, false);
        VaccinationRecordsAdapter.ViewHolder viewHolder = new VaccinationRecordsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VaccinationRecordsAdapter.ViewHolder holder, int position) {
        Event event = this.events.get(position);
        TextView textView = holder.titleTextView;
        String categoryName = DataStore.getInstance().getProgram().getCategoryById(event.getCategoryId()).getName();
        textView.setText("[" + this.dateFormat(event.getDate()) + "] " + event.getSubstance() + "(" + categoryName + ") - " + event.getNote());
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    private String dateFormat(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
}
