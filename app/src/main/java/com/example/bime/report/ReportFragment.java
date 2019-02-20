package com.example.bime.report;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.bime.R;
import com.example.bime.base.BaseFragment;
import com.example.bime.common.MainActivity;
import com.example.bime.data.ApiInterface;
import com.example.bime.data.model.InsuranceInfo;
import com.example.bime.data.model.ReportInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportFragment extends BaseFragment {

    private View mViewRoot;
    private static final int REQUEST_PERMISSION = 1;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private ApiInterface apiInterface;
    private InsuranceInfo insuranceInfo;
    private EditText firstName;
    private EditText nationalId;
    private EditText insuranceId;
    private Button button;
    private EditText phoneNumber;
    private EditText address;
    private EditText desc;
    private Spinner state;
    private Button selectFile;
    private final static int PICKFILE_RESULT_CODE = 1;
    private TextView fileName;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private TextView lastname;

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
        firstName = mViewRoot.findViewById(R.id.firstname);
        lastname = mViewRoot.findViewById(R.id.lasttname);
        insuranceId = mViewRoot.findViewById(R.id.insuranceid);
        nationalId = mViewRoot.findViewById(R.id.nationalCode);
        button = mViewRoot.findViewById(R.id.button);
        phoneNumber = mViewRoot.findViewById(R.id.phone);
        address = mViewRoot.findViewById(R.id.address);
        desc = mViewRoot.findViewById(R.id.description);
        state = mViewRoot.findViewById(R.id.state);
        selectFile = mViewRoot.findViewById(R.id.file);
        fileName = mViewRoot.findViewById(R.id.filename);
        spinner = mViewRoot.findViewById(R.id.insuranceType);

        String[] items = new String[]{"نوع بیمه", "بیمه شخص ثالث", "بیمه بدنه"};
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        initListener();
        initRetrofit();
        requestCameraPermission();
        initStates();

    }

    private void initStates() {

        apiInterface.getStates().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){
                    ArrayList<String> items = new ArrayList<>();
                    items.add("استان را انتخاب کنید");
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i=0 ; i < jsonArray.length(); i++){
                            items.add(jsonArray.getJSONObject(i).getString("Name"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
                    state.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("state", "onFailure: " );
            }
        });
    }

    private void initListener() {
        mDecoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if (result != null && result.toString().contains("https://SanhabInq.centinsur.ir")) {
                    mDecoratedBarcodeView.pause();
                    String[] resultsplit = result.toString().split("[?]");
                    getInfoByScanId(resultsplit[1]);
                }
                mDecoratedBarcodeView.resume();
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFields()) {
                    showSnackbar(mViewRoot, "لطفا همه اطلاعات را وارد کنید");
                    return;
                }
                requestDamageReport();
            }
        });

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_RESULT_CODE) {
            try {
                if (data == null)
                    return;

                Uri uri = Uri.parse(data.getData().getPath());
                data.setDataAndType(uri, "resource/folder");
                uri.getLastPathSegment();
                String[] filepath = uri.toString().split("[/]");
                fileName.setText("ضمیمه شد" + filepath[filepath.length - 1]);

            } catch (NullPointerException e) {

            }
        }
    }

    private boolean checkFields() {
        return (
                !firstName.getText().toString().equals("") &&
                        !lastname.getText().toString().equals("")
                        && !nationalId.getText().toString().equals("")
                        && !address.getText().toString().equals("")
                        && !insuranceId.getText().toString().equals("")
                        && !phoneNumber.getText().toString().equals("")
                        && spinner.getSelectedItemId() != 0
                        && state.getSelectedItemId() != 0
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

    private void getInfoByScanId(String uniqueidentifier) {
        Call<ResponseBody> call = apiInterface.getInfo(uniqueidentifier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        writeQrCodeResults(response.body().string());
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


    private void writeQrCodeResults(String body) {
        JSONObject data = null;
        try {
            JSONObject reader = new JSONObject(body);
            data = reader.getJSONObject("Data");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().create();

        if (data == null)
            return;

        insuranceInfo = gson.fromJson(data.toString(), InsuranceInfo.class);
        if (insuranceInfo.getName() != null) {
            String[] fullname = insuranceInfo.getName().split(" ");
            String lastpart = "";
            firstName.setText(fullname[0]);
            for (int i = 1; i < fullname.length; i++) {
                lastpart = lastpart + fullname[i];
            }
            lastname.setText(lastpart);
            if (insuranceInfo.getInsuranceField().equals("شخص ثالث")) {
                spinner.setSelection(1);
            } else
                spinner.setSelection(0);

            insuranceId.setText(insuranceInfo.getPolicyUniqueNumber());
            nationalId.setText(insuranceInfo.getNationalCode());
        }

    }

    private void requestDamageReport() {
        showMaterialDialog();
        Call<ResponseBody> call = apiInterface.report(new ReportInfo(
                firstName.getText().toString(), lastname.getText().toString(), phoneNumber.getText().toString(),
                "123456", address.getText().toString(), nationalId.getText().toString(), insuranceId.getText().toString()
                , desc.getText().toString(), 30, 50, state.getSelectedItemPosition(), true, spinner.getSelectedItemPosition(), "~/Uploads/Damage/c74fe3f0f0734d34ba0b5109144f7afa-better.things.s01.e01.srt", null
        ));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        MaterialDialog.Builder mMaterialDialogBuilder = new MaterialDialog.Builder(getContext())
                                .title("کد پیگیری")
                                .neutralText("بازگشت به صفحه نخست")
                                .contentGravity(GravityEnum.END)
                                .titleGravity(GravityEnum.END)
                                .content(jsonObject.get("Data").toString())
                                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        materialDialog.dismiss();
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .cancelable(false);
                        dismissMaterialDialog();
                        materialDialog = mMaterialDialogBuilder.build();
                        materialDialog.show();
                    } catch (IOException e) {
                        showSnackbar(mViewRoot, "با خطا مواجه شد");
                        dismissMaterialDialog();
                        materialDialog.dismiss();
                    } catch (JSONException e) {
                        showSnackbar(mViewRoot, "با خطا مواجه شد");
                        dismissMaterialDialog();
                        materialDialog.dismiss();
                    } catch (NullPointerException e) {
                        showSnackbar(mViewRoot, "با خطا مواجه شد");
                        materialDialog.dismiss();
                        dismissMaterialDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                materialDialog.dismiss();
                dismissMaterialDialog();
                showSnackbar(mViewRoot, "با خطا مواجه شد");
            }
        });
    }


    private void initRetrofit() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String token = preferences.getString("token", null);
        if (token == null)
            return;


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("x-api-key", "676bdb1ce2d84276b8874a41f143c739")
                        .header("Authorization", token)
                        .build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://iloss.net/")
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

}
