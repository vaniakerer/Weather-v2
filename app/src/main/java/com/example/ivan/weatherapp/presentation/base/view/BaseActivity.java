package com.example.ivan.weatherapp.presentation.base.view;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.ivan.weatherapp.R;

/**
 * Created by ivan
 */

public abstract class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void showErrorMessage(String message) {
        new MaterialDialog.Builder(this)
                .title(R.string.error)
                .content(message)
                .build()
                .show();
    }
}
