package com.credinkamovil.pe.ui.splash;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.scottyab.rootbeer.RootBeer;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {
    private static final String TAG = SplashActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 777;
    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash);
            getActivityComponent().inject(this);
            mPresenter.onAttach(SplashActivity.this);

            RootBeer rootBeer = new RootBeer(this);
            if (rootBeer.isRooted()) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Equipo Rooteado")
                        .setMessage("No se puede ejecutar la app en un equipo rooteado.")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            dialog.dismiss();
                            finish();
                        });
            }

            setUpView();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void setUpView() {
        ImageView imvLoading = findViewById(R.id.imv_loading);
        imvLoading.setImageResource(0);
        imvLoading.setBackgroundResource(R.drawable.drw_loading_splash);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) imvLoading.getBackground();
        mAnimationDrawable.start();
        //if (checkPermissionsApplication()) {
        mPresenter.onPingService();
        //}
    }

    @Override
    public void intentLoginActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                intentNextActivity(SplashActivity.this, LoginActivity.class, true);
            }
        }, 1000);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;
            for (int coffe = 0; coffe < grantResults.length; coffe++) {
                if (grantResults[coffe] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[coffe], grantResults[coffe]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0) {
                mPresenter.onPingService();
            } else {
                mPresenter.onReportAccessDenied();
            }
        }
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }
}