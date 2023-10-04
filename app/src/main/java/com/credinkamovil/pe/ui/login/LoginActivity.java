package com.credinkamovil.pe.ui.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.credinkamovil.pe.BuildConfig;
import com.credinkamovil.pe.R;
import com.credinkamovil.pe.ui.base.BaseActivity;
import com.credinkamovil.pe.ui.login.bloquear.BloqueoActivity;
import com.credinkamovil.pe.ui.login.contacto.ContactoActivity;
import com.credinkamovil.pe.ui.main.MainActivity;
import com.credinkamovil.pe.ui.maps.UbicacionMapsActivity;
import com.credinkamovil.pe.utils.AppConstantes;
import com.credinkamovil.pe.utils.AppResourceUtils;
import com.credinkamovil.pe.utils.AppUtils;
import com.credinkamovil.pe.utils.NetworkUtils;
import com.credinkamovil.pe.utils.views.CustomCardMaskNumber;
import com.credinkamovil.pe.utils.views.DrawableClickListener;
import com.credinkamovil.pe.utils.views.NumberDocumentTransformation;
import com.credinkamovil.pe.utils.views.TextWatcherExtendedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginMvpView {
    private static final String TAG = LoginActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 777;
    @Inject
    LoginMvpPresenter<LoginMvpView> mLoginPresenter;
    private Button btnLogin, btnOlvidoClave;
    private EditText edtNumeroTD;
    private EditText edtNumeroDNI;
    private EditText edtClave;
    private RelativeLayout rlUbicacion, rlBloquearTD, rlContacto;
    private CheckBox chkRecordarTd, chkRecordarDocumento;
    private CustomCardMaskNumber mCardMaskNumber;
    private Activity mActivity;
    private static String sNumeroTarjetaPartial = "";
    private static String sNumeroDocumentoPartial = "";
    private NumberDocumentTransformation mDocumentTransformation;
    private boolean bCardValidator;
    private boolean bDocumentValidator;
    String[] mAppPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public boolean flagUpdateConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_login);
            getActivityComponent().inject(this);
            mLoginPresenter.onAttach(LoginActivity.this);
            setUpView();
            setListenersView();
            setUpTouchListeners();
            setUpInit();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    protected void setUpView() {
        edtNumeroTD = findViewById(R.id.edt_numero_td);
        edtNumeroDNI = findViewById(R.id.edt_numero_documento);
        edtClave = findViewById(R.id.edt_password);
        rlUbicacion = findViewById(R.id.rl_ubicacion);
        rlBloquearTD = findViewById(R.id.rl_bloquear_td);
        rlContacto = findViewById(R.id.rl_contacto);
        chkRecordarTd = findViewById(R.id.chk_recordar_td);
        chkRecordarDocumento = findViewById(R.id.chk_recordar_documento);
        TextView tvVersion = findViewById(R.id.tv_version);
        mDocumentTransformation = new NumberDocumentTransformation();
        mDocumentTransformation.mChar = getString(R.string.point).charAt(0);
        mActivity = this;
        bCardValidator = false;
        bDocumentValidator = false;
        btnLogin = findViewById(R.id.btn_login);
        btnOlvidoClave = findViewById(R.id.btn_olvido_clave);
        flagUpdateConfig = false;

        tvVersion.setText("Versión " + BuildConfig.VERSION_NAME);
    }

    @Override
    public void intentMainActivity() {
        intentNextActivity(LoginActivity.this, MainActivity.class, true);
        overridePendingTransition(R.anim.transition_in_right, R.anim.transition_out_left);
        finish();
    }

    @Override
    public void intentUbicacion() {
        intentNextActivity(LoginActivity.this, UbicacionMapsActivity.class, false);
        overridePendingTransition(R.anim.transition_in_right, R.anim.transition_out_left);
    }

    @Override
    public void intentBloquearTarjeta() {
        intentNextActivity(LoginActivity.this, BloqueoActivity.class, false);
        overridePendingTransition(R.anim.transition_in_right, R.anim.transition_out_left);
    }

    @Override
    public void intentContactos() {
        intentNextActivity(LoginActivity.this, ContactoActivity.class, false);
        overridePendingTransition(R.anim.transition_in_right, R.anim.transition_out_left);
    }

    @Override
    public void onCompletarDatosLocal(String sValorTD, String sValorDoc) {
        if (sValorTD.length() > 0) {
            chkRecordarTd.setChecked(true);
            bCardValidator = true;
            mCardMaskNumber.hasFormat = true;
            sNumeroTarjetaPartial = sValorTD;
            String mCardNumberPartial = AppResourceUtils.getPartialAccount(sValorTD);
            edtNumeroTD.setText(AppResourceUtils.getFormatNumber(mCardNumberPartial, this.mActivity));
            setFiltersInputs(this.edtNumeroTD.getText().toString().length());
            edtNumeroTD.clearFocus();
        } else {
            setFiltersInputs(AppConstantes.MAX_LENGTH_CARD_NUMBER);
            chkRecordarTd.setChecked(false);
            mCardMaskNumber.hasFormat = false;
            setCompoDrawableEditText(edtNumeroTD);
        }
        if (sValorDoc.length() > 0) {
            bDocumentValidator = true;
            chkRecordarDocumento.setChecked(true);
            sNumeroDocumentoPartial = sValorDoc;
            edtNumeroDNI.setTransformationMethod(null);
            //edtNumeroDNI.setText(this.mDocNumberPartial);
            edtNumeroDNI.setTransformationMethod(mDocumentTransformation);
            edtNumeroDNI.setText(sValorDoc);
            //edtNumeroDNI.clearFocus();
        } else {
            chkRecordarDocumento.setChecked(false);
            setCompoDrawableEditText(edtNumeroDNI);
        }
        chkRecordarTd.setButtonDrawable(R.drawable.drw_bottom_chk);
        chkRecordarDocumento.setButtonDrawable(R.drawable.drw_bottom_chk);
    }

    @Override
    public void isChecked(int option) {
        if (option == 1) {
            chkRecordarTd.setChecked(false);
        } else {
            chkRecordarDocumento.setChecked(false);
        }
    }

    @Override
    public void onHabilitarGPS(boolean flagGps) {
        LoginActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        flagUpdateConfig = flagGps;
    }

    @Override
    public void onFinishApplication() {
        finish();
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (flagUpdateConfig) {
            flagUpdateConfig = false;
            mLoginPresenter.onClicUbicacion();
        }
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        mLoginPresenter.onFinishApplication();
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
                if (NetworkUtils.validateEnableGPS(getApplicationContext())) {
                    mLoginPresenter.onClicUbicacion();
                } else {
                    flagUpdateConfig = false;
                    mLoginPresenter.onSolicitarPermisos();
                }
            }
        }
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setUpTouchListeners() {
        edtNumeroTD.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(edtNumeroTD) {
            @Override
            public boolean onDrawableClick() {
                edtNumeroTD.setText("");
                mCardMaskNumber.hasFormat = false;
                bCardValidator = false;
                sNumeroTarjetaPartial = "";
                mLoginPresenter.onClicEliminarTarjeta();
                chkRecordarTd.setChecked(false);
                setCompoDrawableEditText(edtNumeroTD);
                return false;
            }
        });
        edtNumeroDNI.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(edtNumeroDNI) {
            @Override
            public boolean onDrawableClick() {
                edtNumeroDNI.setText("");
                bDocumentValidator = false;
                sNumeroDocumentoPartial = "";
                mLoginPresenter.onClicEliminarDocumento();
                chkRecordarDocumento.setChecked(false);
                edtNumeroDNI.setTransformationMethod(null);
                return false;
            }
        });
        edtClave.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(edtClave) {
            @Override
            public boolean onDrawableClick() {
                edtClave.setText("");
                return false;
            }
        });
        edtClave.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void setListenersView() {
        btnLogin.setOnClickListener(new onClickLogin());
        rlUbicacion.setOnClickListener(new onClicUbicacion());
        rlBloquearTD.setOnClickListener(new onClicBloquearTarjeta());
        rlContacto.setOnClickListener(new onClicContactos());
        chkRecordarTd.setOnClickListener(new onClicRecordarTD());
        chkRecordarDocumento.setOnClickListener(new onClicRecordarDocumento());
        btnOlvidoClave.setOnClickListener(new onClicInfoClaveOlvido());
        sNumeroTarjetaPartial = "";
        sNumeroDocumentoPartial = "";
    }

    private void setUpInit() {
        eventMethodsTarjeta();
        eventMethodsDocumento();
        eventMethodsPin();
        mLoginPresenter.onClicInitialData();
    }

    class onClickLogin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String sClave = edtClave.getText().toString().trim();
            mLoginPresenter.onServerLoginClick(sNumeroTarjetaPartial, sNumeroDocumentoPartial, sClave);
        }
    }

    class onClicUbicacion implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (checkPermissionsApplication()) {
                if (NetworkUtils.validateEnableGPS(getApplicationContext())) {
                    mLoginPresenter.onClicUbicacion();
                } else {
                    flagUpdateConfig = false;
                    mLoginPresenter.onSolicitarPermisos();
                }
            }
        }
    }

    class onClicBloquearTarjeta implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mLoginPresenter.onClicBloquearTarjeta();
        }
    }

    class onClicContactos implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mLoginPresenter.onClicContactos();
        }
    }

    class onClicRecordarTD implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                if (sNumeroTarjetaPartial.length() == 16) {
                    mLoginPresenter.onClicRecordarTarjeta(sNumeroTarjetaPartial);
                    if (!sNumeroTarjetaPartial.equals("")) {
                        Toast.makeText(getApplicationContext(), "Número de tarjeta recordado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    chkRecordarTd.setChecked(false);
                }
            } else {
                mLoginPresenter.onClicEliminarTarjeta();
            }
        }
    }

    class onClicRecordarDocumento implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            boolean checked = ((CheckBox) view).isChecked();
            if (checked) {
                if (sNumeroDocumentoPartial.length() == 8) {
                    mLoginPresenter.onClicRecordarDocumento(sNumeroDocumentoPartial);
                    if (!sNumeroDocumentoPartial.equals("")) {
                        Toast.makeText(getApplicationContext(), "Número de documento recordado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    chkRecordarDocumento.setChecked(false);
                }
            } else {
                mLoginPresenter.onClicEliminarDocumento();
            }
        }
    }

    class onClicInfoClaveOlvido implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mLoginPresenter.onClicInfoOlvidoClave();
        }
    }

    private void eventMethodsTarjeta() {
        try {
            mCardMaskNumber = new CustomCardMaskNumber(edtNumeroTD, new TextWatcherExtendedListener() {
                int countBefore;

                @Override
                public void afterTextChanged(Editable editable) {
                    if (mCardMaskNumber.hasFormat && editable.toString().length() < this.countBefore) {
                        mCardMaskNumber.hasFormat = false;
                        bCardValidator = false;
                        setFiltersInputs(AppConstantes.MAX_LENGTH_CARD_NUMBER);
                        edtNumeroTD.setText("");
                        edtClave.setText("");
                        sNumeroTarjetaPartial = "";
                        mLoginPresenter.onClicEliminarTarjeta();
                        chkRecordarTd.setChecked(false);
                        setCompoDrawableEditText(edtNumeroTD);
                    } else if (editable.length() == 0) {
                        bCardValidator = false;
                        setCompoDrawableEditText(edtNumeroTD);
                        mLoginPresenter.onClicEliminarTarjeta();
                        chkRecordarTd.setChecked(false);
                    } else {
                        String sNumero = editable.toString().trim().replaceAll(" ", "");
                        if (!bCardValidator) {
                            if (sNumero.length() == 16) {
                                bCardValidator = true;
                                sNumeroTarjetaPartial = sNumero;
                            }
                        } else {
                            if (AppUtils.isNumeric(sNumero)) {
                                sNumeroTarjetaPartial = sNumero;
                            }
                        }
                        edtNumeroTD.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_small, 0);
                    }
                    if (!mCardMaskNumber.hasFormat) {
                        mActivity.setResult(0);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                    this.countBefore = charSequence.length();
                    //String sNumero = charSequence.toString().trim().replaceAll(" ", "");
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    //String sNumero = charSequence.toString().trim().replaceAll(" ", "");
                }
            });
            edtNumeroTD.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (!mCardMaskNumber.hasFormat) {
                        String sNumero = reemplazarFocusarStringEspacios();
                        if (i == 5 && AppResourceUtils.validateNumberCard(sNumero)) {
                            setFiltersInputs(edtNumeroTD.getText().toString().length());
                            edtNumeroTD.setText(AppResourceUtils.getFormatNumber(sNumero, LoginActivity.this.mActivity));
                            mCardMaskNumber.hasFormat = true;
                        }
                    }
                    return false;
                }
            });
            edtNumeroTD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus && !mCardMaskNumber.hasFormat) {
                        String sNumero = reemplazarFocusarStringEspacios();
                        if (AppResourceUtils.validateNumberCard(sNumero)) {
                            setFiltersInputs(edtNumeroTD.getText().toString().length());
                            edtNumeroTD.setText(AppResourceUtils.getFormatNumber(sNumero, LoginActivity.this.mActivity));
                            mCardMaskNumber.hasFormat = true;
                        }
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void eventMethodsDocumento() {
        edtNumeroDNI.setTransformationMethod(null);
        edtNumeroDNI.setText("");
        edtNumeroDNI.setInputType(InputType.TYPE_CLASS_PHONE);
        edtNumeroDNI.setFilters(new InputFilter[]{new InputFilter.LengthFilter(AppConstantes.DNI)});
        mDocumentTransformation.minimunCharacter = AppConstantes.DNI;
        mDocumentTransformation.maximunCharacter = AppConstantes.DNI;
        edtNumeroDNI.addTextChangedListener(new TextWatcher() {
            int antesCambio = -1;
            boolean bValidate = true;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                this.antesCambio = count;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (count < antesCambio && edtNumeroDNI.getTransformationMethod() != null) {
                    edtNumeroDNI.setTransformationMethod(null);
                    edtNumeroDNI.setText("");
                    sNumeroDocumentoPartial = "";
                    bValidate = false;
                    chkRecordarDocumento.setChecked(false);
                    mLoginPresenter.onClicEliminarDocumento();
                    setCompoDrawableEditText(edtNumeroDNI);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (bValidate) {
                    if (editable.length() == 0) {
                        setCompoDrawableEditText(edtNumeroDNI);
                    } else {
                        String sNumero = editable.toString().trim().replaceAll(" ", "");
                        if (!bDocumentValidator) {
                            if (sNumero.length() == 8) {
                                bDocumentValidator = true;
                                sNumeroDocumentoPartial = sNumero;
                            }
                        } else {
                            if (AppUtils.isNumeric(sNumero)) {
                                sNumeroDocumentoPartial = sNumero;
                            }
                        }
                        edtNumeroDNI.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_small, 0);
                    }
                }
                bValidate = true;
            }
        });
        edtNumeroDNI.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    edtNumeroDNI.setSelection(edtNumeroDNI.getText().length());
                    if (edtNumeroDNI.getText().toString().isEmpty()) {
                        edtNumeroDNI.setTransformationMethod(null);
                        return;
                    }
                    return;
                }
                edtNumeroDNI.setTransformationMethod(mDocumentTransformation);
            }
        });
    }

    private void eventMethodsPin() {
        edtClave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String textPin = charSequence.toString();
                if (textPin.length() == 6) {
                    mLoginPresenter.hideVirtualKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    setCompoDrawableEditText(edtClave);
                } else {
                    edtClave.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_small, 0);
                }
            }
        });
    }

    private static void setCompoDrawableEditText(EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private void setFiltersInputs(int size) {
        edtNumeroTD.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size)});
    }

    private String reemplazarFocusarStringEspacios() {
        return edtNumeroTD.getText().toString().replaceAll(" ", "");
    }

    private boolean checkPermissionsApplication() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : mAppPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
}