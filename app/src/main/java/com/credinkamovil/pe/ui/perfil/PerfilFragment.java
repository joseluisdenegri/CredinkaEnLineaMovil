package com.credinkamovil.pe.ui.perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnSesion;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Objects;

import javax.inject.Inject;

public class PerfilFragment extends BaseFragment implements PerfilMvpView {
    public static final String TAG = PerfilFragment.class.getName();
    private TextView tvNombres, tvApellidos, tvFechaUltimoAcceso;
    private SimpleDraweeView sdvSello;
    private Button btnCerrarSesion;
    @Inject
    PerfilMvpPresenter<PerfilMvpView> mPresenterPerfil;

    public PerfilFragment() {
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_perfil, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterPerfil.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            sdvSello = view.findViewById(R.id.sdv_sello);
            tvNombres = view.findViewById(R.id.tv_nombres);
            tvApellidos = view.findViewById(R.id.tv_apellidos);
            tvFechaUltimoAcceso = view.findViewById(R.id.tv_ultimo_acceso);
            btnCerrarSesion = view.findViewById(R.id.btn_cerrar_sesion);

            btnCerrarSesion.setOnClickListener(new onClicCerrarSesion());
            mPresenterPerfil.onCargarDatosPerfil();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCompletarDatosPerfil(EnSesion sesion) {
        String sUrl = sesion.getsValor6();
        Uri uri = Uri.parse(sUrl);
        sdvSello.setImageURI(uri);
        tvNombres.setText(String.format("%s %s", sesion.getsValor1(), sesion.getsValor2()));
        tvApellidos.setText(String.format("%s %s", sesion.getsValor3(), sesion.getsValor4()));
        tvFechaUltimoAcceso.setText(sesion.getsValor5());
    }

    @Override
    public void onLogout() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    class onClicCerrarSesion implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mPresenterPerfil.onCerrarSesion();
        }
    }

    @Override
    public void onDestroyView() {
        mPresenterPerfil.onDetach();
        super.onDestroyView();
    }
}
