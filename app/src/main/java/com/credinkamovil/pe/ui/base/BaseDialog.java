package com.credinkamovil.pe.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;

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
import com.credinkamovil.pe.utils.FragmentUtils;

import okhttp3.internal.Util;

public abstract class BaseDialog extends DialogFragment implements DialogMvpView {
    private BaseActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity mActivity = (BaseActivity) context;
            this.mActivity = mActivity;
            mActivity.onFragmentAttached();
        }
    }

    @Override
    public void showLoading(String sTitulo) {
        if (mActivity != null) {
            mActivity.showLoading(getString(R.string.str_cargando));
        }
    }

    @Override
    public void hideLoading() {
        if (mActivity != null) {
            mActivity.hideLoading();
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
    public void showMessage(String sMessage) {
        if (mActivity != null) {
            mActivity.showMessage(sMessage);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public ActivityComponent getActivityComponent() {
        if (mActivity != null) {
            return mActivity.getActivityComponent();
        }
        return null;
    }

    protected abstract void setUp(View view);

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
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

    @Override
    public void dismissDialog(String tag) {
        dismiss();
        getBaseActivity().onFragmentDetached(tag);
    }

    @Override
    public void failedConnectionInternet() {

    }

    @Override
    public String getMessages(int restId) {
        return getString(restId);
    }

    @Override
    public AlertDialog showAlertDialog(String sMessage, View.OnClickListener listener, boolean bCancelable) {
        return null;
    }

    @Override
    public AlertDialog showConfirmDialog(String sTitle, String sMessage, View.OnClickListener listener, String sNameButtom1, String sNameButtom2) {
        return null;
    }

    @Override
    public AlertDialog showDialogOnboarding(String sTitle, String sMessage, View.OnClickListener yeslist, View.OnClickListener canlist, String sNameButtom1, String sNameButtom2) {
        return null;
    }

    @Override
    public void showDialogCustom(String sMessage) {
        if (mActivity != null) {
            mActivity.showDialogCustom(sMessage);
        }
    }

    @Override
    public void onUserInteractionRegister() {
    }

    @Override
    public void onUserInteractionTimer(boolean bStatus) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
