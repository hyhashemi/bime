package com.example.bime.base;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.bime.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public MaterialDialog materialDialog;

    public void showMaterialDialog() {

        MaterialDialog.Builder mMaterialDialogBuilder = new MaterialDialog.Builder(getContext())
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

    public void showSnackbar(View root, String content) {

        Snackbar snackbar = Snackbar.make(root, content, 3000);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        view.setBackgroundColor(
                getResources().getColor(R.color.design_default_color_error));
        snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE);
        view.setLayoutParams(params);
        snackbar.show();
    }
}
