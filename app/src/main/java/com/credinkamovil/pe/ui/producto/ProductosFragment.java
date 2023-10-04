package com.credinkamovil.pe.ui.producto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.detalleproducto.creditos.DetalleCreditosFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.DetalleCuentasFragment;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.credinkamovil.pe.ui.producto.adapters.CreditosAdapter;
import com.credinkamovil.pe.ui.producto.adapters.CuentasAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

public class ProductosFragment extends BaseFragment implements ProductosMvpView {
    public static final String TAG = ProductosFragment.class.getName();
    private LinearLayout lyContenidoCuentas, lyContenidoCreditos;
    private ListView lvListaMisCuentas, lvListaMisCredito;
    @Inject
    ProductosMvpPresenter<ProductosMvpView> mPresenterProducto;
    @Inject
    CuentasAdapter mCuentasAdapter;
    @Inject
    CreditosAdapter mCreditosAdapter;

    public ProductosFragment() {
    }

    public static ProductosFragment newInstanceProducto() {
        ProductosFragment fragment = new ProductosFragment();
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
            mView = inflater.inflate(R.layout.fragment_productos, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterProducto.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            lyContenidoCuentas = view.findViewById(R.id.ly_contenido_cuentas);
            lyContenidoCreditos = view.findViewById(R.id.ly_contenido_creditos);
            lvListaMisCuentas = view.findViewById(R.id.lv_mis_cuentas);
            lvListaMisCredito = view.findViewById(R.id.lv_mis_creditos);

            lvListaMisCuentas.setAdapter(mCuentasAdapter);
            lvListaMisCredito.setAdapter(mCreditosAdapter);
            setUpListener();
            mPresenterProducto.onValidateTokenExpired();
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
    }
    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    @Override
    public void onDestroyView() {
        mPresenterProducto.onDetach();
        super.onDestroyView();
    }
    @Override
    public void onCompletarDatosLista(ArrayList<EnCuentas> listCuentas, ArrayList<EnPrestamo> listaCreditos) {
        try {
            if (listCuentas.size() > 0) {
                if (lyContenidoCuentas.getVisibility() == View.GONE) {
                    lyContenidoCuentas.setVisibility(View.VISIBLE);
                }
                mCuentasAdapter.addListNotifyChanged(listCuentas, getContext());
            } else {
                if (lyContenidoCuentas.getVisibility() == View.VISIBLE) {
                    lyContenidoCuentas.setVisibility(View.GONE);
                }
            }
            if (listaCreditos.size() > 0) {
                if (lyContenidoCreditos.getVisibility() == View.GONE) {
                    lyContenidoCreditos.setVisibility(View.VISIBLE);
                }
                mCreditosAdapter.addListNotifyChanged(listaCreditos, getContext());
            } else {
                if (lyContenidoCreditos.getVisibility() == View.VISIBLE) {
                    lyContenidoCreditos.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error en CompletarDatosLista" + ex.getMessage());
        }
    }

    @Override
    public void showFragmentDetalleMisCuentas(String sNumeroCuenta) {
        Bundle mBundle = new Bundle();
        mBundle.putString("sNumeroCuenta", sNumeroCuenta);
        showFragments(getParentFragmentManager(), new DetalleCuentasFragment(), DetalleCuentasFragment.TAG, mBundle);
    }

    @Override
    public void showFragmentDetalleMisCreditos(String sNroCred) {
        Bundle mBundle = new Bundle();
        mBundle.putString("sNumeroCredito", sNroCred);
        showFragments(getParentFragmentManager(), new DetalleCreditosFragment(), DetalleCreditosFragment.TAG, mBundle);
    }
    @Override
    public void intentNextActivity(Activity activityFrom, Class<?> activityTo, boolean bStatus) {
        super.intentNextActivity(activityFrom, activityTo, bStatus);
    }
    @Override
    public void intentLoginActivity() {
        intentNextActivity(getActivity(), LoginActivity.class, true);
    }

    @Override
    public void onConfirmarCampanaComercial() {
        Toast.makeText(getContext(), getString(R.string.str_mensaje_confirm_oferta), Toast.LENGTH_LONG).show();
    }
    private void setUpListener() {
        lvListaMisCuentas.setOnItemClickListener(new isOnClicListenerCuentas());
        lvListaMisCredito.setOnItemClickListener(new isOnClicListenerCreditos());
    }

    class isOnClicListenerCuentas implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mPresenterProducto.onOnClicListenerMisCuentas(position);
        }
    }

    class isOnClicListenerCreditos implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mPresenterProducto.onOnClicListenerMisCreditos(position);
        }
    }
}
