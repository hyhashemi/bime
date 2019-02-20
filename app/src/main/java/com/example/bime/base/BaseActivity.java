package com.example.bime.base;

import android.content.Context;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private MaterialDialog materialDialog;

    public void showMaterialDialog() {

        Context context = this;
        MaterialDialog.Builder mMaterialDialogBuilder = new MaterialDialog.Builder(context)
                .title("در حال پردازش")
                .content("لطفا منتظر بمانید")
                .progress(true, 100)
                .negativeText("Cancel")
                .cancelable(false);
        materialDialog = mMaterialDialogBuilder.build();
        materialDialog.show();

        materialDialog.getBuilder().onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                materialDialog.dismiss();
            }
        });
    }

    public void dismissMaterialDialog() {

        materialDialog.dismiss();
    }
}
