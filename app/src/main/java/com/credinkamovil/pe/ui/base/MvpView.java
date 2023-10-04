package com.credinkamovil.pe.ui.base;

import android.view.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

public interface MvpView {
    void showLoading(String sTitulo);

    void hideLoading();

    void onError(@StringRes int restId);

    String getMessages(@StringRes int restId);

    void onError(String sMessage);

    void showMessage(String sMessage);

    void hideKeyboard();

    void failedConnectionInternet();

    boolean isNetworkConnected();

    AlertDialog showAlertDialog(String sMessage, View.OnClickListener listener, boolean bCancelable);

    void showDialogCustom(String sMessage);

    void onUserInteractionRegister();

    void onUserInteractionTimer(boolean bStatus);

    AlertDialog showConfirmDialog(String sTitle, String sMessage, View.OnClickListener listener, String sNameButtom1, String sNameButtom2);

    AlertDialog showDialogOnboarding(String sTitle, String sMessage, View.OnClickListener yeslist, View.OnClickListener canlist, String sNameButtom1, String sNameButtom2);
}
