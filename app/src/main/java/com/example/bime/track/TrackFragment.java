package com.example.bime.track;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bime.R;
import com.example.bime.base.BaseFragment;
import com.example.bime.data.ApiClient;
import com.example.bime.trackreport.TrackReportActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackFragment extends BaseFragment {

    private View mRoot;
    private TextInputEditText trackId;
    private TextInputEditText nationalId;
    private Button button;

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
        Call<ResponseBody> call = ApiClient.getAoiInterface().track(trackId.getText().toString(), nationalId.getText().toString());
        showMaterialDialog();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    String data = "";
                    try {
                        data = response.body().string();
                        if (data.contains("\"Data\":null")) {
                            dismissMaterialDialog();
                            showSnackbar(mRoot, "پرونده خسارت پیدا نشد");
                            return;
                        }
                        dismissMaterialDialog();
                        Intent intent = new Intent(getContext(), TrackReportActivity.class);
                        intent.putExtra("Data", data);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                        dismissMaterialDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("fail", "onFailure: ");
                dismissMaterialDialog();
            }
        });

    }
}
