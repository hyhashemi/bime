package com.example.bime.track;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bime.R;
import com.example.bime.data.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrackFragment extends Fragment {

    private View mRoot;
    private TextInputEditText trackId;
    private TextInputEditText nationalId;
    private Button button;
    private ApiInterface apiInterface;
    private TextView address;
    private TextView insurerDescription;
    private TextView name;
    private TextView phoneNumber;
    private TextView policyFullNumber;
    private TextView nationalCode;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_track, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trackId = mRoot.findViewById(R.id.trackid);
        nationalId = mRoot.findViewById(R.id.trackinternationalid);
        button = mRoot.findViewById(R.id.trackbutton);
        address = mRoot.findViewById(R.id.address_value);
        policyFullNumber = mRoot.findViewById(R.id.policyFullNumber_value);
        insurerDescription = mRoot.findViewById(R.id.desc_value);
        name = mRoot.findViewById(R.id.name_value);
        nationalCode = mRoot.findViewById(R.id.nationalcode_value);
        phoneNumber = mRoot.findViewById(R.id.phonenumber_value);

        initRetrofit();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackId.getText().toString().equals("") || nationalId.getText().toString().equals("")) {
                    return;
                }
                requestTrack();
            }
        });


    }

    private void requestTrack() {
        Call<ResponseBody> call = apiInterface.track(trackId.getText().toString(), nationalId.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        JSONObject data = jsonObject.getJSONObject("Data");
                        name.setText(data.get("FirstName").toString() + data.get("LastName").toString());
                        nationalCode.setText(data.get("NationalCode").toString());
                        phoneNumber.setText(data.get("PhoneNumber").toString());
                        insurerDescription.setText(data.get("InsurerDescription").toString());
                        policyFullNumber.setText(data.get("PolicyFullNumber").toString());
                        address.setText(data.get("Address").toString());

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.alert_layout, null);
                        alertDialog.setView(view);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "بازگشت",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("fail", "onFailure: " );
            }
        });

    }


    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://iloss.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }
}
