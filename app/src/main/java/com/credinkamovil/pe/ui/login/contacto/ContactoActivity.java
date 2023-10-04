package com.credinkamovil.pe.ui.login.contacto;

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
import com.credinkamovil.pe.data.models.EnContacto;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class ContactoActivity extends BaseActivity implements ContactoMvpView{
    private static final String TAG = ContactoActivity.class.getName();
    @Inject
    ContactoMvpPresenter<ContactoMvpView> mContactoPresenter;
    private TextView tvContactoLima, tvContactoProvincias;
    private ImageButton imbLima, imbProvincias;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_contacto);
            getActivityComponent().inject(this);
            mContactoPresenter.onAttach(ContactoActivity.this);
            setUpView();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void setUpView() {
        tvContactoLima = findViewById(R.id.tv_contacto_lima);
        tvContactoProvincias = findViewById(R.id.tv_contacto_provincias);
        imbLima = findViewById(R.id.imb_lima);
        imbProvincias = findViewById(R.id.imb_provincias);
        mToolbar = findViewById(R.id.tb_contacto);

        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        imbLima.setOnClickListener(new onClicLima());
        imbProvincias.setOnClickListener(new onClicProvincias());
        mToolbar.setNavigationOnClickListener(new onClicNavigationBack());
    }

    @Override
    public void onCompletarDatosContacto(ArrayList<EnContacto> lista) {
        if (lista.size() > 0) {
            for (int coffe = 0; coffe < lista.size(); coffe++) {
                int iTipoContacto = lista.get(coffe).getiTipoContacto();
                switch (iTipoContacto) {
                    case 1:
                        tvContactoLima.setText(lista.get(coffe).getsNumeroTelefono().trim());
                        break;
                    case 2:
                        tvContactoProvincias.setText(lista.get(coffe).getsNumeroTelefono().trim());
                        break;
                }
            }
        } else {
            onLoginActivity();
        }
    }

    @Override
    public void onLoginActivity() {
        intentNextActivity(ContactoActivity.this, LoginActivity.class, true);
        overridePendingTransition(R.anim.transition_in_left, R.anim.transition_out_right);
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    @Override
    protected void onDestroy() {
        mContactoPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        onLoginActivity();
    }

    class onClicLima implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = "tel:" + tvContactoLima.getText().toString().trim();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse(sNumero));
            if (call.resolveActivity(getPackageManager()) != null) {
                startActivity(call);
            }
        }
    }

    class onClicProvincias implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String sNumero = "tel:" + tvContactoProvincias.getText().toString().trim();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse(sNumero));
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
