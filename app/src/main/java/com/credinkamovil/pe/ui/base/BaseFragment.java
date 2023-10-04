package com.credinkamovil.pe.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.utils.CommonUtils;

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(false);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <onCreate> " + ex.getMessage());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            this.mActivity = activity;
            activity.onFragmentAttached();
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
    }

    @Override
    public void onDetach() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        this.mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading(String sTitulo) {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onError(@StringRes int restId) {
        if (mActivity != null) {
            mActivity.onError(restId);
        }
    }

    @Override
    public void onError(String sMessage) {
        if (mActivity != null) {
            mActivity.showDialogCustom(sMessage);
        }
    }

    @Override
    public String getMessages(int restId) {
        return getString(restId);
    }

    @Override
    public void showMessage(String sMessage) {
        if (mActivity != null) {
            mActivity.showMessage(sMessage);
        }
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void failedConnectionInternet() {

    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public AlertDialog showAlertDialog(String sMessage, View.OnClickListener listener, boolean bCancelable) {
        if (mActivity != null) {
            return mActivity.showAlertDialog(sMessage, listener, bCancelable);
        }
        return null;
    }

    @Override
    public void showDialogCustom(String sMessage) {
        if (mActivity != null) {
            mActivity.showDialogCustom(sMessage);
        }
    }

    @Override
    public AlertDialog showConfirmDialog(String sTitle, String sMessage, View.OnClickListener listener, String sNameButtom1, String sNameButtom2) {
        if (mActivity != null) {
            return mActivity.showConfirmDialog(sTitle, sMessage, listener, sNameButtom1, sNameButtom2);
        }
        return null;
    }

    @Override
    public AlertDialog showDialogOnboarding(String sTitle, String sMessage, View.OnClickListener yeslist, View.OnClickListener canlist, String sNameButtom1, String sNameButtom2) {
        if (mActivity != null) {
            return mActivity.showDialogOnboarding(sTitle, sMessage, yeslist, canlist, sNameButtom1, sNameButtom2);
        }
        return null;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    protected abstract void setUpView(View view);

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        Fragment mPrevFragment = fragmentManager.findFragmentByTag(sTag);
        if (mPrevFragment != null) {
            mFragmentTransaction.remove(mPrevFragment);
        }
        mFragment.setArguments(bundle);
        mFragmentTransaction.replace(R.id.fcv_principal, mFragment);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        mFragmentTransaction.addToBackStack(sTag);
        mFragmentTransaction.commit();
    }
    public void showDialogFragments(FragmentManager fragmentManager, DialogFragment mFragment, String sTag, Bundle bundle) {
        FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
        Fragment mPrevFragment = fragmentManager.findFragmentByTag(sTag);
        if (mPrevFragment != null) {
            mFragmentTransaction.remove(mPrevFragment);
        }
        mFragmentTransaction.addToBackStack(null);
        mFragment.setArguments(bundle);
        mFragment.setCancelable(true);
        mFragment.show(mFragmentTransaction, sTag);
    }
    @Override
    public void onUserInteractionRegister() {
    }

    @Override
    public void onUserInteractionTimer(boolean bStatus) {
    }

    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        Intent intentActivity = new Intent(activityFrom, activityTo);
        activityFrom.startActivity(intentActivity);
        if (bStatus)
            activityFrom.finish();
    }
}
