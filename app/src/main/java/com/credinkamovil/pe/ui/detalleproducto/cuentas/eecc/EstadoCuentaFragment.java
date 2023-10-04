package com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters.PeriodoAdapter;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogFragment;
import com.credinkamovil.pe.ui.producto.ProductosFragment;
import com.credinkamovil.pe.utils.AppUtils;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;

public class EstadoCuentaFragment extends BaseFragment implements EstadoCuentaMvpView {
    public static final String TAG = CuentaDialogFragment.class.getName();

    private ListView lvEstadoCuenta;
    private MaterialTextView mtvTitle;
    private static String sNumeroCuenta, sNombreCuenta;
    @Inject
    EstadoCuentaMvpPresenter<EstadoCuentaMvpView> mEstadoCuentaPresenter;
    @Inject
    PeriodoAdapter mPeriodoAdapter;

    public EstadoCuentaFragment() {
    }

    public static EstadoCuentaFragment newInstance(String param1, String param2) {
        EstadoCuentaFragment fragment = new EstadoCuentaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sNumeroCuenta = getArguments().getString("sNumeroCuenta");
            sNombreCuenta = getArguments().getString("sNombreCuenta");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_estado_cuenta, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mEstadoCuentaPresenter.onAttach(this);
                mPeriodoAdapter.setCallbackPeriodo((EstadoCuentaPresenter) mEstadoCuentaPresenter);
            }

        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        lvEstadoCuenta = view.findViewById(R.id.lv_estado_cuenta);
        mtvTitle = view.findViewById(R.id.mtv_title);
        lvEstadoCuenta.setAdapter(mPeriodoAdapter);
        mEstadoCuentaPresenter.onObtenerPeriodo(sNumeroCuenta);
    }

    @Override
    public void showFragmentDetalleCuentas() {
        showFragments(getParentFragmentManager(), new ProductosFragment(), ProductosFragment.TAG, null);
    }

    @Override
    public void onCompletarDatosPeriodo(ArrayList<EnPeriodo> listaPeriodo, String sNumeroCuenta) {
        mtvTitle.setText("Estado de Cuenta \n" + sNombreCuenta);
        mPeriodoAdapter.addListNotifyChanged(listaPeriodo, getContext(), sNumeroCuenta);
    }

    @Override
    public void onDownloadEstadoCuenta(InputStream inputStream) {
        try {
            String urlFile = "";
            urlFile = AppUtils.downloadEstadoCuentaPdf(inputStream, getContext());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(Uri.parse(urlFile), "application/pdf");
            startActivity(intent);
        } catch (ActivityNotFoundException not) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        mEstadoCuentaPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    @Override
    public void onDetach() {
        mPeriodoAdapter.removeCallback();
        super.onDetach();
    }

}
