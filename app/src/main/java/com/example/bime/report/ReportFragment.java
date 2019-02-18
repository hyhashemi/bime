package com.example.bime.report;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bime.R;
import com.example.bime.common.MainActivity;
import com.example.bime.data.ApiInterface;
import com.example.bime.data.model.InsuranceInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportFragment extends Fragment {

    private View mViewRoot;
    private static final int REQUEST_PERMISSION = 1;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private ApiInterface apiInterface;
    private InsuranceInfo insuranceInfo;
    private EditText name;
    private EditText nationalId;
    private EditText insuranceId;
    private EditText insuranceType;
    private Button button;
    private EditText phoneNumber;
    private EditText address;
    private EditText desc;
    private EditText state;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewRoot = inflater.inflate(R.layout.fragment_report, container, false);
        return mViewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDecoratedBarcodeView = mViewRoot.findViewById(R.id.qrcodescanner_container);
        mDecoratedBarcodeView.setVisibility(View.VISIBLE);
        mDecoratedBarcodeView.setStatusText("کد QR بیمه نامه را اسکن کنید ");
        name = mViewRoot.findViewById(R.id.firstname);
        insuranceId = mViewRoot.findViewById(R.id.insuranceid);
        insuranceType = mViewRoot.findViewById(R.id.insuranceType);
        nationalId = mViewRoot.findViewById(R.id.nationalCode);
        button = mViewRoot.findViewById(R.id.button);
        phoneNumber = mViewRoot.findViewById(R.id.phone);
        address = mViewRoot.findViewById(R.id.address);
        desc = mViewRoot.findViewById(R.id.description);
        state = mViewRoot.findViewById(R.id.state);

        initListener();
        initRetrofit();
        requestCameraPermission();

    }

    public void initListener() {
        mDecoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if (result != null) {
                    mDecoratedBarcodeView.pause();
                    String[] resultsplit = result.toString().split("[?]");
                    requestId(resultsplit[1]);
                } else {
                    mDecoratedBarcodeView.resume();
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFields())
                    return;
                requestReport();
            }
        });
    }

    private boolean checkFields() {
        return (
                !name.getText().toString().equals("")
                        && !nationalId.getText().toString().equals("")
                        && !address.getText().toString().equals("")
                        && !insuranceId.getText().toString().equals("")
                        && !insuranceType.getText().toString().equals("")
                        && !phoneNumber.getText().toString().equals("")
        );
    }

    private void requestCameraPermission() {

        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        } else {

            resumeScanner();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        resumeScanner();
    }

    private void resumeScanner() {

        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            mDecoratedBarcodeView.resume();
            mDecoratedBarcodeView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void requestId(String uniqueidentifier) {
        Call<ResponseBody> call = apiInterface.getInfo(uniqueidentifier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        putToFields(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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

    private void putToFields(String body) {
        JSONObject data = null;
        try {
            JSONObject reader = new JSONObject(body);
            data = reader.getJSONObject("Data");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();
        insuranceInfo = gson.fromJson(data.toString(), InsuranceInfo.class);
        name.setText(insuranceInfo.getName());
        insuranceType.setText(insuranceInfo.getInsuranceField());
        insuranceId.setText(insuranceInfo.getPolicyUniqueNumber());
        nationalId.setText(insuranceInfo.getNationalCode());
    }

    private void requestReport() {
        String[] fullName = name.getText().toString().split(" ");
        String firstName = fullName[0];
        String lastName = "";
        for (int i = 1; i < fullName.length; i++) {
            lastName += fullName[i];
        }
        Call<ResponseBody> call = apiInterface.report(firstName, lastName, phoneNumber.getText().toString(),
                "123456", address.getText().toString(), nationalId.getText().toString(), insuranceId.getText().toString()
                , desc.getText().toString(), 30, 50, 21, true, insuranceType.getText().toString(), "");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if (response.code() == 200){
                   try {
                       AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                       alertDialog.setTitle("کد پیگیری");
                       alertDialog.setMessage(response.body().string());
                       alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "ورود به صفحه نخست",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int which) {
                                       dialog.dismiss();
                                       Intent intent = new Intent(getContext(), MainActivity.class);
                                       startActivity(intent);
                                   }
                               });
                       alertDialog.show();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
