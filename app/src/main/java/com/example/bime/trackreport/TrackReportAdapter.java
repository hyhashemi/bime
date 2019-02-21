package com.example.bime.trackreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrackReportAdapter extends RecyclerView.Adapter<TrackReportAdapter.TrackReportViewHolder> {

    private JSONArray data;

    public TrackReportAdapter(JSONArray data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TrackReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtracking, parent, false);
        return new TrackReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackReportViewHolder holder, int position) {
        if (data != null && data.length() != 0) {
            try {
                JSONObject statejson = data.getJSONObject(position);
                holder.title.setText(statejson.getString("StateTitle"));
                holder.value.setText(statejson.getString("CreateDateString"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public int getItemCount() {

        return data.length();
    }

    class TrackReportViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView value;


        private TrackReportViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.trackreporttitle);
            value = itemView.findViewById(R.id.trackreportvalue);

        }
    }
}
