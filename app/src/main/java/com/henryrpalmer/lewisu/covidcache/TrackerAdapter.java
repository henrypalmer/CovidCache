package com.henryrpalmer.lewisu.covidcache;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class TrackerAdapter extends FirebaseRecyclerAdapter<Tracker, TrackerAdapter.TrackerHolder> {


//    RECYCLER VIEW CLASS
    public static class TrackerHolder extends RecyclerView.ViewHolder {
        private final TextView startDateView;
        private final TextView threeDaysDateView;
        private final TextView twoWeeksDateView;
        private final TextView symptomsView;

        public TrackerHolder(View itemView) {
            super(itemView);
            startDateView = itemView.findViewById(R.id.start_date);
            threeDaysDateView = itemView.findViewById(R.id.three_days_date);
            twoWeeksDateView = itemView.findViewById(R.id.two_weeks_date);
            symptomsView = itemView.findViewById(R.id.symptoms_list);
        }
}

//    DEFAULT CONSTRUCTOR
    public TrackerAdapter(@NonNull FirebaseRecyclerOptions<Tracker> options) {
        super(options);
    }


//    BIND THE DATA TO THE VIEW
    @Override
    protected void onBindViewHolder(@NonNull TrackerAdapter.TrackerHolder holder, int position, @NonNull Tracker model) {
        holder.startDateView.setText(model.getStartDateString());
        holder.threeDaysDateView.setText(model.getThreeDayString());
        holder.twoWeeksDateView.setText(model.getTwoWeekString());
        holder.symptomsView.setText(model.getSymptomsString());
    }


//    INFLATE THE LAYOUT
    @NonNull
    @Override
    public TrackerAdapter.TrackerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.text_row_item, parent, false);
        return new TrackerHolder(view);
    }
}
