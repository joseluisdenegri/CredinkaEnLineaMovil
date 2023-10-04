package com.credinkamovil.pe.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.R;

public final class CommonUtils {
    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.show();
        if (mProgressDialog.getWindow() != null) {
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mProgressDialog.setContentView(R.layout.custom_loading);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        ImageView imageView = mProgressDialog.findViewById(R.id.imv_loading);
        imageView.setImageResource(0);
        imageView.setBackgroundResource(R.drawable.drw_loading_splash);
        AnimationDrawable mAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        mAnimationDrawable.start();
        return mProgressDialog;
    }

    public static AlertDialog showAlertDialog(Activity activity, String sMessage, View.OnClickListener listener, boolean bCancelable) {
        AlertDialog builder = new AlertDialog.Builder(activity, R.style.Theme_Dialog_Translucent).create();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_alert, null);
        //builder.addContentView(new View(activity), (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)));
        TextView titulo = view.findViewById(R.id.tv_titulo_dialog);
        titulo.setText(R.string.app_name);
        Button btnOk = view.findViewById(R.id.btn_aceptar);
        TextView contenido = view.findViewById(R.id.tv_mensaje_dialog);
        contenido.setText(sMessage);

        if (listener == null)
            listener = v -> builder.dismiss();

        btnOk.setOnClickListener(listener);
        builder.setView(view);
        builder.setCancelable(bCancelable);
        builder.show();
        return builder;
    }

    public static void showDialogCustom(Context context, String sMessage) {
        final Dialog mDialog = new Dialog(context, R.style.Theme_Dialog_Translucent);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setCancelable(false);
        mDialog.setContentView(R.layout.dialog_alert);
        mDialog.addContentView(new View(context), (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));
        TextView titulo = mDialog.findViewById(R.id.tv_titulo_dialog);
        titulo.setText(R.string.app_name);
        TextView contenido = (TextView) mDialog.findViewById(R.id.tv_mensaje_dialog);
        contenido.setText(sMessage);
        mDialog.findViewById(R.id.btn_aceptar).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    public static AlertDialog showConfirmDialogCustom(Activity mActivity, String sTittle, String sMessage, View.OnClickListener yesListener,
                                                      String sNameButtom1, String sNameButtom2) {
        AlertDialog mAlertDialog = new AlertDialog.Builder(mActivity, R.style.Theme_Dialog_Translucent).create();
        try {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_confirmation, null);
            TextView tvMensajeConfirmacion = view.findViewById(R.id.tv_mensaje_confirmacion);
            TextView tvTitulo = view.findViewById(R.id.tv_titulo_dialog);
            Button btDialogSi = view.findViewById(R.id.btn_si);
            Button btDialogNo = view.findViewById(R.id.btn_no);

            tvMensajeConfirmacion.setText(sMessage);
            tvTitulo.setText(sTittle);
            btDialogNo.setText(sNameButtom1);
            btDialogSi.setText(sNameButtom2);
            if (yesListener == null)
                yesListener = v -> mAlertDialog.dismiss();

            btDialogSi.setOnClickListener(yesListener);
            btDialogNo.setOnClickListener(v -> mAlertDialog.dismiss());

            mAlertDialog.setView(view);
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mAlertDialog;
    }

    public static AlertDialog showDialogOnboarding(Activity mActivity, String sTittle, String sMessage, View.OnClickListener yesListener,
                                                   View.OnClickListener cancelListener, String sNameButtom1, String sNameButtom2) {
        AlertDialog mAlertDialog = new AlertDialog.Builder(mActivity, R.style.Theme_Dialog_Translucent).create();
        try {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_confirmation, null);
            TextView tvMensajeConfirmacion = view.findViewById(R.id.tv_mensaje_confirmacion);
            TextView tvTitulo = view.findViewById(R.id.tv_titulo_dialog);
            Button btDialogSi = view.findViewById(R.id.btn_si);
            Button btDialogNo = view.findViewById(R.id.btn_no);

            tvMensajeConfirmacion.setGravity(Gravity.CENTER);
            tvMensajeConfirmacion.setText(sMessage);
            tvTitulo.setText(sTittle);
            btDialogNo.setText(sNameButtom1);
            btDialogSi.setText(sNameButtom2);
            if (yesListener == null)
                yesListener = v -> mAlertDialog.dismiss();
            if (cancelListener == null)
                cancelListener = v -> mAlertDialog.dismiss();

            btDialogSi.setOnClickListener(yesListener);
            btDialogNo.setOnClickListener(cancelListener);

            mAlertDialog.setView(view);
            mAlertDialog.setCancelable(false);
            mAlertDialog.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mAlertDialog;
    }
}
