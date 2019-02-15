package com.example.bime.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bime.R;
import com.example.bime.common.MainActivity;
import com.example.bime.data.ApiInterface;
import com.example.bime.data.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {

    private View mRoot;
    private TextInputEditText mTextInputEditTextUsername;
    private TextInputEditText mTextInputEditTextPassword;
    private Button mButton;
    private ApiInterface apiInterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_login, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initRetrofit();
        initListener();
    }

    public void initUiComponents() {
        mTextInputEditTextUsername = mRoot.findViewById(R.id.loginusername);
        mTextInputEditTextPassword = mRoot.findViewById(R.id.loginpassword);
        mButton = mRoot.findViewById(R.id.loginbutton);
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> call = apiInterface.login(mTextInputEditTextUsername.getText().toString(),
                        mTextInputEditTextPassword.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            User user = new User();
                            user.setPassword(mTextInputEditTextPassword.getText().toString());
                            user.setUsername(mTextInputEditTextUsername.getText().toString());

                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("onfail", "onFailure: ");
                    }
                });


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
