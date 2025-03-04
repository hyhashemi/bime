package com.example.bime.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bime.R;
import com.example.bime.base.BaseFragment;
import com.example.bime.common.MainActivity;
import com.example.bime.data.ApiClient;
import com.example.bime.data.ApiInterface;
import com.example.bime.data.model.User;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends BaseFragment {

    private View mRoot;
    private TextInputEditText mTextInputEditTextUsername;
    private TextInputEditText mTextInputEditTextPassword;
    private Button mButton;

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

                showMaterialDialog();
                Call<ResponseBody> call = ApiClient.getAoiInterface().login(mTextInputEditTextUsername.getText().toString(),
                        mTextInputEditTextPassword.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            User user = new User();
                            user.setPassword(mTextInputEditTextPassword.getText().toString());
                            user.setUsername(mTextInputEditTextUsername.getText().toString());
                            String token = response.headers().get("Authorization");
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", token);
                            editor.apply();

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            showSnackbar(mRoot, "رمز عبور یا نام کاربری اشتباه است");
                        }

                        dismissMaterialDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("onfail", "onFailure: ");
                        dismissMaterialDialog();
                    }
                });

            }
        });
    }

}
