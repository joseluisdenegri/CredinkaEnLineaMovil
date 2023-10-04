package com.credinkamovil.pe.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.di.component.DaggerActivityComponent;
import com.credinkamovil.pe.di.module.ActivityModule;
import com.credinkamovil.pe.root.BaseApp;
import com.credinkamovil.pe.utils.AppResourceUtils;
import com.credinkamovil.pe.utils.CommonUtils;
import com.credinkamovil.pe.utils.NetworkUtils;

public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {
    private ProgressDialog mProgressDialog;
    private ActivityComponent mActivityComponent;
    private static boolean bStatusTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mActivityComponent = DaggerActivityComponent
                    .builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(((BaseApp) getApplication()).getComponent())
                    .build();
            bStatusTimer = false;
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <onCreate> " + ex.getMessage());
        }
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void showLoading(String sTitulo) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(@StringRes int restId) {
        onError(getString(restId));
    }

    @Override
    public String getMessages(int restId) {
        return getString(restId);
    }

    @Override
    public void onError(String sMessage) {
        CommonUtils.showDialogCustom(this, sMessage);
    }

    @Override
    public void showMessage(String sMessage) {

    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void failedConnectionInternet() {

    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public AlertDialog showAlertDialog(String sMessage, View.OnClickListener listener, boolean bCancelable) {
        return CommonUtils.showAlertDialog(this, sMessage, listener, bCancelable);
    }

    @Override
    public void showDialogCustom(String sMessage) {
        CommonUtils.showDialogCustom(this, sMessage);
    }

    @Override
    public AlertDialog showConfirmDialog(String sTitle, String sMessage, View.OnClickListener listener, String sNameButtom1, String sNameButtom2) {
        return CommonUtils.showConfirmDialogCustom(this, sTitle, sMessage, listener, sNameButtom1, sNameButtom2);
    }

    @Override
    public AlertDialog showDialogOnboarding(String sTitle, String sMessage, View.OnClickListener yeslist, View.OnClickListener canlist, String sNameButtom1, String sNameButtom2) {
        return CommonUtils.showDialogOnboarding(this, sTitle, sMessage, yeslist, canlist, sNameButtom1, sNameButtom2);
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .disallowAddToBackStack()
                    .remove(fragment)
                    .commitNow();
        }
    }

    protected abstract void setUpView();

    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        Intent intentActivity = new Intent(activityFrom, activityTo);
        activityFrom.startActivity(intentActivity);
        if (bStatus)
            activityFrom.finish();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if (bStatusTimer) {
            long nTimer = AppResourceUtils.getSessionTimerCount().getTimeDisminuid();
            AppResourceUtils.restartCountDown(this);
            Log.i("BaseActivity", "==============PRESSED============" + nTimer);
        }
    }

    @Override
    public void onUserInteractionRegister() {
        Log.i("BaseActivity", "=======================SESION LOGOUT==============");
    }

    @Override
    public void onUserInteractionTimer(boolean bStatus) {
        if (bStatus) {
            bStatusTimer = true;
            onUserInteraction();
        }
    }
}
