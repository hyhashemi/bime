package com.example.bime.trackreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bime.R;
import com.example.bime.common.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TrackReport extends Fragment {

    private TextView address;
    private TextView insurerDescription;
    private TextView name;
    private TextView phoneNumber;
    private TextView policyFullNumber;
    private TextView nationalCode;
    private View view;
    private Button button;
    private String dataString;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_layout, container, false);
        dataString = (String) getArguments().get("Data");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        address = view.findViewById(R.id.address_value);
        policyFullNumber = view.findViewById(R.id.policyFullNumber_value);
        insurerDescription = view.findViewById(R.id.desc_value);
        name = view.findViewById(R.id.name_value);
        nationalCode = view.findViewById(R.id.nationalcode_value);
        phoneNumber = view.findViewById(R.id.phonenumber_value);
        button = view.findViewById(R.id.trackreportbutton);
        recyclerView = view.findViewById(R.id.recyclerview);

        JSONObject jsonArray = null;
        JSONArray states = null;
        JSONObject data = null;
        try {
//
//            InputStream inputStream = getContext().getResources().openRawResource(R.raw.tracking);
//
//            int size = inputStream.available();
//
//            byte[] buffer = new byte[size];
//
//            inputStream.read(buffer);
//
//            inputStream.close();
//
//            dataString = new String(buffer, "UTF-8");
            jsonArray = new JSONObject(dataString);
            data = jsonArray.getJSONObject("Data");
            states = data.getJSONArray("States");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        if (states != null && states.length() != 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            TrackReportAdapter adapter = new TrackReportAdapter(states);
            recyclerView.setAdapter(adapter);
        }

        try {
            if (data != null && data.get("InsurerDescription") != null) {
                name.setText(data.get("FirstName") + " " + data.get("LastName").toString());
                nationalCode.setText(data.get("NationalCode").toString());
                phoneNumber.setText(data.get("MobileNumber").toString());
                insurerDescription.setText(data.get("InsurerDescription").toString());
                policyFullNumber.setText(data.get("PolicyFullNumber").toString());
                address.setText(data.get("Address").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    static TrackReport newInstance(String data) {

        Bundle args = new Bundle();
        args.putString("Data", data);
        TrackReport fragment = new TrackReport();
        fragment.setArguments(args);
        return fragment;
    }
}
