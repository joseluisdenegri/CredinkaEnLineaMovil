package com.credinkamovil.pe.ui.login.bloquear;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.login.LoginActivity;

import javax.inject.Inject;

public class BloqueoActivity extends BaseActivity implements BloqueoMvpView{
    private static final String TAG = BloqueoActivity.class.getName();
    @Inject
    BloqueoMvpPresenter<BloqueoMvpView> mBloqueoPresenter;
    private TextView tvNacional1, tvNacional2;
    private TextView tvUsaCanada, tvRestoMundo;
    private ImageButton imbNacional1, imbNacional2, imbUsaCanada, imbRestoMundo;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_bloqueo);
            getActivityComponent().inject(this);
            mBloqueoPresenter.onAttach(BloqueoActivity.this);
            setUpView();
            setUpListeners();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void setUpView() {
        try {
            tvNacional1 = findViewById(R.id.tv_contacto_nacional_1);
            tvNacional2 = findViewById(R.id.tv_contacto_nacional_2);
            tvUsaCanada = findViewById(R.id.tv_contacto_usa_canada);
            tvRestoMundo = findViewById(R.id.tv_contacto_resto_mundo);
            mToolbar = findViewById(R.id.tb_bloqueo);
            imbNacional1 = findViewById(R.id.imb_nacional_1);
            imbNacional2 = findViewById(R.id.imb_nacional_2);
            imbUsaCanada = findViewById(R.id.imb_usa_canada);
            imbRestoMundo = findViewById(R.id.imb_resto_mundo);

            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle(null);
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onCompletarDatosContacto(String sNumNacional1, String sNumNacional2, String sNumUsaCanada, String sNumRestoMundo) {
        tvNacional1.setText(sNumNacional1);
        tvNacional2.setText(sNumNacional2);
        tvUsaCanada.setText(sNumUsaCanada);
        tvRestoMundo.setText(sNumRestoMundo);
    }

    @Override
    public void intentLogin() {
        intentNextActivity(BloqueoActivity.this, LoginActivity.class, true);
        overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }
    @Override
    public void onBackPressed() {
        intentLogin();
    }
    private void setUpListeners() {
        imbNacional1.setOnClickListener(new onClicNacional1());
        imbNacional2.setOnClickListener(new onClicNacional2());
        imbUsaCanada.setOnClickListener(new onClicUsaCanada());
        imbRestoMundo.setOnClickListener(new onClicRestoMundo());

        mToolbar.setNavigationOnClickListener(new onClicNavigationBack());
    }
    class onClicNacional1 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = tvNacional1.getText().toString().trim();
            sNumero = sNumero.replaceAll("[^\\.A-Za-z0-9_]", "");
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + sNumero));
            if (call.resolveActivity(getPackageManager()) != null) {
                startActivity(call);
            }
        }
    }
    class onClicNacional2 implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = tvNacional2.getText().toString().trim();
            sNumero = sNumero.replaceAll("[^\\.A-Za-z0-9_]", "");
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + sNumero));
            if (call.resolveActivity(getPackageManager()) != null) {
                startActivity(call);
            }
        }
    }

    class onClicUsaCanada implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = tvUsaCanada.getText().toString().trim();
            sNumero = sNumero.replaceAll("[^\\.A-Za-z0-9_]", "");
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + sNumero));
            if (call.resolveActivity(getPackageManager()) != null) {
                startActivity(call);
            }
        }
    }

    class onClicRestoMundo implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = tvRestoMundo.getText().toString().trim();
            sNumero = sNumero.replaceAll("[^\\.A-Za-z0-9_]", "");
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + sNumero));
            if (call.resolveActivity(getPackageManager()) != null) {
                startActivity(call);
            }
        }
    }
    class onClicNavigationBack implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            onBackPressed();
            overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);
        }
    }
}
