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

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TrackReport extends Fragment {

    private TextView address;
    private TextView insurerDescription;
    private TextView name;
    private TextView phoneNumber;
    private TextView policyFullNumber;
    private TextView nationalCode;
    private View view;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.alert_layout, container, false);
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

        String dataString = (String) getArguments().get("Data");
        JSONObject jsonArray = null;
        JSONObject data = null;
        try {
            jsonArray = new JSONObject(dataString);
            data = jsonArray.getJSONObject("Data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (data != null && data.get("InsurerDescription") != null) {
                name.setText(data.get("FirstName") + data.get("LastName").toString());
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
