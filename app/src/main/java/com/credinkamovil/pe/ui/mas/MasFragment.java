package com.credinkamovil.pe.ui.mas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.credinkamovil.pe.ui.maps.fragments.MapLocateFragment;
import com.credinkamovil.pe.ui.perfil.PerfilFragment;
import com.credinkamovil.pe.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MasFragment extends BaseFragment implements MasMvpView{
    public static final String TAG = MasFragment.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 778;
    private LinearLayout lyMiPerfil, lyCerrarSesion, lyUbicacion, lyTipoCambio;
    private TextView tvTipoCambioCompra, tvTipoCambioVenta;
    @Inject
    MasMvpPresenter<MasMvpView> mPresenterMas;
    String[] mAppPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public boolean flagUpdateConfig;

    public MasFragment() {
    }

    public static MasFragment newInstance(String param1, String param2) {
        MasFragment fragment = new MasFragment();
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
            mView = inflater.inflate(R.layout.fragment_mas, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterMas.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            lyMiPerfil = view.findViewById(R.id.ly_mi_perfil);
            lyCerrarSesion = view.findViewById(R.id.ly_cerrar_sesion);
            lyUbicacion = view.findViewById(R.id.ly_ubicacion);
            tvTipoCambioCompra = view.findViewById(R.id.tv_tipo_cambio_compra);
            tvTipoCambioVenta = view.findViewById(R.id.tv_tipo_cambio_venta);
            lyTipoCambio = view.findViewById(R.id.ly_tipo_cambio);
            flagUpdateConfig = false;

            lyMiPerfil.setOnClickListener(new onClicPerfil());
            lyCerrarSesion.setOnClickListener(new onClicCerrarSesion());
            lyUbicacion.setOnClickListener(new onClicUbicacion());
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flagUpdateConfig) {
            flagUpdateConfig = false;
            showFragments(getParentFragmentManager(), new MapLocateFragment(), MapLocateFragment.TAG, null);
        }
    }

    @Override
    public void onDestroyView() {
        mPresenterMas.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onCompletarDatosTipoCambio(String sCompra, String sVenta, int iEstado) {
        try {
            tvTipoCambioCompra.setText(String.format("S/ %s", sCompra));
            tvTipoCambioVenta.setText(String.format("S/ %s", sVenta));
            if (iEstado == 0) {
                if (lyTipoCambio.getVisibility() == View.VISIBLE) {
                    lyTipoCambio.setVisibility(View.GONE);
                }
            } else {
                if (lyTipoCambio.getVisibility() == View.GONE) {
                    lyTipoCambio.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCerrarSesionApp() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onHabilitarGPS(boolean flagGps) {
        flagUpdateConfig = flagGps;
        MasFragment.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    class onClicPerfil implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            showFragments(getParentFragmentManager(), new PerfilFragment(), PerfilFragment.TAG, null);
        }
    }

    class onClicCerrarSesion implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mPresenterMas.onClicCerrarSesion();
        }
    }

    class onClicUbicacion implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (checkPermissionsApplication()) {
                if (NetworkUtils.validateEnableGPS(getContext())) {
                   showFragments(getParentFragmentManager(), new MapLocateFragment(), MapLocateFragment.TAG, null);
                } else {
                    flagUpdateConfig = false;
                    mPresenterMas.onSolicitarPermisos();
                }
            }
        }
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
                if (NetworkUtils.validateEnableGPS(getContext())) {
                   showFragments(getParentFragmentManager(), new MapLocateFragment(), MapLocateFragment.TAG, null);
                } else {
                    flagUpdateConfig = false;
                    mPresenterMas.onSolicitarPermisos();
                }
            }
        }
    }

    private boolean checkPermissionsApplication() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String perm : mAppPermissions) {
            if (ContextCompat.checkSelfPermission(getContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(perm);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE
            );
            //ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
}
