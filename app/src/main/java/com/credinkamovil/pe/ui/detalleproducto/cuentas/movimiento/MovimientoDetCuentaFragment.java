package com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnDetalleMovimiento;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.utils.AppUtils;

import javax.inject.Inject;

public class MovimientoDetCuentaFragment extends BaseFragment implements MovimientoCuentaMvpView {
    public static final String TAG = MovimientoDetCuentaFragment.class.getName();
    private static EnDetalleMovimiento oDetalleMov;
    private TextView tvSaldoContable, tvSaldoDisponible;
    private TextView tvDescripcionMovimiento, tvNroTransaccion, tvFechaMovimiento, tvMoneda, tvHoraTrx,
            tvImporteMovimiento, tvImporte;
    @Inject
    MovimientoCuentaMvpPresenter<MovimientoCuentaMvpView> mMovimientoCuentaPresenter;

    public MovimientoDetCuentaFragment() {
    }

    public static MovimientoDetCuentaFragment newInstance() {
        MovimientoDetCuentaFragment fragment = new MovimientoDetCuentaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oDetalleMov = new EnDetalleMovimiento();
        if (getArguments() != null) {
            oDetalleMov = (EnDetalleMovimiento) getArguments().getSerializable("oDetalleMov");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_movimiento_det_cuenta, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mMovimientoCuentaPresenter.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            tvSaldoContable = view.findViewById(R.id.tv_saldo_contable);
            tvSaldoDisponible = view.findViewById(R.id.tv_saldo_disponible);
            tvDescripcionMovimiento = view.findViewById(R.id.tv_descripcion_movimiento);
            tvNroTransaccion = view.findViewById(R.id.tv_nro_transaccion);
            tvFechaMovimiento = view.findViewById(R.id.tv_fecha_movimiento);
            tvMoneda = view.findViewById(R.id.tv_moneda);
            tvImporteMovimiento = view.findViewById(R.id.tv_importe_movimiento);
            tvImporte = view.findViewById(R.id.tv_importe);
            tvHoraTrx=view.findViewById(R.id.tv_hora_trx);

            mMovimientoCuentaPresenter.onObtenerDatosMovimientoDetalle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onCompletarDatosDetalleMovimiento() {
        try {
            String sTipoMoneda = "S/ ";
            String sMoneda = oDetalleMov.getsMoneda().trim();
            String sSldCont = String.format("%s %s", oDetalleMov.getsMoneda(), AppUtils.formatStringDecimals(oDetalleMov.getnSaldoContable()));
            String sSldDisp = String.format("%s %s", oDetalleMov.getsMoneda(), AppUtils.formatStringDecimals(oDetalleMov.getnSaldoDisponible()));
            tvSaldoContable.setText(sSldCont);
            tvSaldoDisponible.setText(sSldDisp);
            tvDescripcionMovimiento.setText(oDetalleMov.getsConcepto());
            tvNroTransaccion.setText(String.valueOf(oDetalleMov.getiAsientoTrx()));
            tvFechaMovimiento.setText(oDetalleMov.getsFechaMovimiento());
            String sSigno = oDetalleMov.getsSigno();

            if (sSigno.equals("+")) {
                sSigno = "";
                tvImporteMovimiento.setTextColor(Color.parseColor("#808080"));
            } else {
                sSigno = "-";
                tvImporteMovimiento.setTextColor(Color.parseColor("#E22639"));
            }
            if (sMoneda.equals("S/")) {
                sTipoMoneda = "SOLES";
            } else {
                sTipoMoneda = "DOLARES";
            }
            tvMoneda.setText(sTipoMoneda);
            tvHoraTrx.setText(oDetalleMov.getsHoraTrx());
            String sImporMov = String.format("%s %s%s", sMoneda, sSigno, AppUtils.formatStringDecimals(oDetalleMov.getnImporteMov()));
            String sImporte = String.format("%s %s", sMoneda, AppUtils.formatStringDecimals(oDetalleMov.getnSaldoAct()));
            tvImporteMovimiento.setText(sImporMov);
            tvImporte.setText(sImporte);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        mMovimientoCuentaPresenter.onDetach();
        super.onDestroyView();
    }
}
