package com.credinkamovil.pe.ui.detalleproducto.cuentas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnDetalleMovimiento;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.data.models.ItemOption;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters.MovimientoCuentasAdapter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento.MovimientoDetCuentaFragment;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogFragment;
import com.credinkamovil.pe.ui.producto.ProductosFragment;
import com.credinkamovil.pe.utils.AppUtils;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

public class DetalleCuentasFragment extends BaseFragment implements CuentasMvpView {
    public static final String TAG = DetalleCuentasFragment.class.getName();
    private ListView lvListaMovimientos;
    private TextView tvNombreProducto, tvNumeroCuenta;
    private TextView tvSaldoContable, tvSaldoDisponible;
    private LinearLayout lyDetalleCuentas, lyEstadoCuenta;
    private TextView tvSinMovimientos;
    private ImageView imvOptionsCta;
    private Button btnEstadoCuenta;
    @Inject
    CuentasMvpPresenter<CuentasMvpView> mCuentasPresenter;
    @Inject
    MovimientoCuentasAdapter mMovimientoCuentasAdapter;
    private static String sNroCuenta, sNombreCuenta;

    public DetalleCuentasFragment() {
    }

    public static DetalleCuentasFragment newInstance() {
        DetalleCuentasFragment fragment = new DetalleCuentasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sNroCuenta = getArguments().getString("sNumeroCuenta");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_detalle_cuentas, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mCuentasPresenter.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            lvListaMovimientos = view.findViewById(R.id.lv_movimiento_cuenta);
            tvNombreProducto = view.findViewById(R.id.tv_producto);
            tvNumeroCuenta = view.findViewById(R.id.tv_numero_cuenta);
            tvSaldoContable = view.findViewById(R.id.tv_saldo_contable);
            tvSaldoDisponible = view.findViewById(R.id.tv_saldo_disponible);
            lyDetalleCuentas = view.findViewById(R.id.ly_detalle_cuentas);
            lyEstadoCuenta = view.findViewById(R.id.linear_layout_eecc);
            tvSinMovimientos = view.findViewById(R.id.tv_sin_movimiento);
            imvOptionsCta = view.findViewById(R.id.imv_options_cta);
            btnEstadoCuenta = view.findViewById(R.id.btn_eecc);
            lvListaMovimientos.setAdapter(mMovimientoCuentasAdapter);
            setUpListeners();
            mCuentasPresenter.onObtenerMovimientoCuenta(sNroCuenta);
            imvOptionsCta.setOnClickListener(new isOnClicOptionsCuenta());
            btnEstadoCuenta.setOnClickListener(new onClicEstadoCuenta());
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        mCuentasPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onCompletarDetalleMovimiento(EnCuentas enCuentas, ArrayList<EnMovimientoCuenta> listaMovimiento) {
        try {
            sNombreCuenta = enCuentas.getsNombreProducto();
            tvNombreProducto.setText(enCuentas.getsNombreProducto());
            tvNumeroCuenta.setText(String.valueOf(enCuentas.getnNumeroCuenta()));
            tvSaldoContable.setText(String.format("%s %s", enCuentas.getsSignoMoneda(), AppUtils.formatStringDecimals(enCuentas.getnSaldCont())));
            tvSaldoDisponible.setText(String.format("%s %s", enCuentas.getsSignoMoneda(), AppUtils.formatStringDecimals(enCuentas.getnSaldDisp())));
            lyDetalleCuentas.setVisibility(View.VISIBLE);

            if (listaMovimiento.size() > 0) {
                if (lvListaMovimientos.getVisibility() == View.GONE) {
                    lvListaMovimientos.setVisibility(View.VISIBLE);
                }
                if (tvSinMovimientos.getVisibility() == View.VISIBLE) {
                    tvSinMovimientos.setVisibility(View.GONE);
                }
                if (enCuentas.getiFam() == 90) {
                    /* Descomentar para habilitar SubMenu (Personalizar) */
                    /*if (imvOptionsCta.getVisibility() == View.GONE) {
                        imvOptionsCta.setVisibility(View.VISIBLE);
                    }*/
                    if (lyEstadoCuenta.getVisibility() == View.GONE){
                        lyEstadoCuenta.setVisibility(View.VISIBLE);
                    }
                } else {
                    imvOptionsCta.setVisibility(View.GONE);
                    lyEstadoCuenta.setVisibility(View.GONE);
                }
            } else {
                if (lvListaMovimientos.getVisibility() == View.VISIBLE) {
                    lvListaMovimientos.setVisibility(View.GONE);
                }
                if (tvSinMovimientos.getVisibility() == View.GONE) {
                    tvSinMovimientos.setVisibility(View.VISIBLE);
                }
            }
            mMovimientoCuentasAdapter.addListNotifyChanged(listaMovimiento, getContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void showFragmentDetalleMovimiento(EnDetalleMovimiento detalleMovimiento) {
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("oDetalleMov", detalleMovimiento);
        showFragments(getParentFragmentManager(), new MovimientoDetCuentaFragment(), MovimientoDetCuentaFragment.TAG, mBundle);
    }

    @Override
    public void showFragmentProducto() {
        showFragments(getParentFragmentManager(), new ProductosFragment(), ProductosFragment.TAG, null);
    }

    @Override
    public void showDialogFragmentCuentaOpcion(ArrayList<ItemOption> listaOpcion, String sNameCta) {
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("oListaOpcion", listaOpcion);
        mBundle.putString("sNameCta", sNameCta);
        showDialogFragments(getParentFragmentManager(), new CuentaDialogFragment(), CuentaDialogFragment.TAG, mBundle);
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    private void setUpListeners() {
        lvListaMovimientos.setOnItemClickListener(new isOnClicItemListeners());
    }

    class isOnClicItemListeners implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            mCuentasPresenter.onOnClicItemListenerCuentas(position);
        }
    }

    class isOnClicOptionsCuenta implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mCuentasPresenter.onClicDialogCuenta(sNroCuenta);
        }
    }

    class onClicEstadoCuenta implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle mBundle = new Bundle();
            mBundle.putString("sNumeroCuenta", sNroCuenta);
            mBundle.putString("sNombreCuenta", sNombreCuenta);
            showFragments(getParentFragmentManager(), new EstadoCuentaFragment(), EstadoCuentaFragment.TAG, mBundle);
        }
    }
}
