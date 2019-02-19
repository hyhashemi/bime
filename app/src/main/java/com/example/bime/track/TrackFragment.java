package com.example.bime.track;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bime.R;
import com.example.bime.data.ApiInterface;
import com.example.bime.trackreport.TrackReportActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                    String data = "";
                    try {
                         data = response.body().string();
                         if (data.contains("\"Data\":null")){
                             Snackbar snackbar = Snackbar.make(mRoot,"پیدا نشد", 3000);
                             View view = snackbar.getView();
                             FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                             params.gravity = Gravity.TOP;
                             view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                             view.setBackgroundColor(getResources().getColor(R.color.design_default_color_error));
                             snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE);
                             view.setLayoutParams(params);
                             snackbar.show();
                             return;
                         }
                         Intent intent = new Intent(getContext(), TrackReportActivity.class);
                         intent.putExtra("Data", data);
                         startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("fail", "onFailure: ");
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
