package com.credinkamovil.pe.ui.detalleproducto.creditos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCuotasPendientesCredito;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;
import com.credinkamovil.pe.ui.detalleproducto.creditos.adapters.MovimientoCreditoAdapter;
import com.credinkamovil.pe.ui.producto.ProductosFragment;
import com.credinkamovil.pe.utils.AppUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;

import javax.inject.Inject;

public class DetalleCreditosFragment extends BaseFragment implements CreditosMvpView {
    public static final String TAG = "DetalleCreditosFragment";
    private static String sNumeroCredito;
    private TextView tvProductoCredito, tvNumeroCredito, tvTotalCuotas, tvSaldoCapital;
    private TextView tvMontoProximaCuota, tvMontoOriginal, tvTEA, tvFechaPago,
            tvDiasAtraso, tvCuotasRestantes;
    private ListView lvListaCronograma;
    private LinearLayout lySinContenido, lyDetalleCreditos, lyProxCuota, lyFchPago, lyDwnCronograma;
    private Button btnDescargarCronograma;
    @Inject
    CreditosMvpPresenter<CreditosMvpView> mCreditosPresenter;
    @Inject
    MovimientoCreditoAdapter mMovimientoCreditoAdapter;

    public DetalleCreditosFragment() {
    }

    public static DetalleCreditosFragment newInstance() {
        DetalleCreditosFragment fragment = new DetalleCreditosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sNumeroCredito = getArguments().getString("sNumeroCredito");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_detalle_creditos, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mCreditosPresenter.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            tvProductoCredito = view.findViewById(R.id.tv_producto);
            tvNumeroCredito = view.findViewById(R.id.tv_numero_credito);
            tvTotalCuotas = view.findViewById(R.id.tv_estado_cuotas);
            tvSaldoCapital = view.findViewById(R.id.tv_saldo_capital);
            tvMontoProximaCuota = view.findViewById(R.id.tv_monto_proxima_cuota);
            tvMontoOriginal = view.findViewById(R.id.tv_monto_original);
            tvTEA = view.findViewById(R.id.tv_tea);
            tvFechaPago = view.findViewById(R.id.tv_fecha_pago);
            tvDiasAtraso = view.findViewById(R.id.tv_dias_atraso);
            tvCuotasRestantes = view.findViewById(R.id.tv_cuotas_restantes);
            lvListaCronograma = view.findViewById(R.id.lv_lista_cronograma);
            lySinContenido = view.findViewById(R.id.ly_sin_contenido);
            lyDetalleCreditos = view.findViewById(R.id.ly_detalle_creditos);
            lyProxCuota = view.findViewById(R.id.ly_prox_cuota);
            lyFchPago = view.findViewById(R.id.ly_fch_pago);
            lyDwnCronograma = view.findViewById(R.id.ly_dwn_cronograma);
            btnDescargarCronograma = view.findViewById(R.id.btn_descargar_pdf);
            lvListaCronograma.setAdapter(mMovimientoCreditoAdapter);

            mCreditosPresenter.onObtenerDetalleCreditos(sNumeroCredito);
            btnDescargarCronograma.setOnClickListener(new onClicDescargarCronograma());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        mCreditosPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onCompletarDetalleCredito(EnPrestamo enPrestamo, ArrayList<EnCuotasPendientesCredito> olista) {
        try {
            String sCuotaPagadas = String.valueOf(enPrestamo.getiCuotasPagadas());
            String sTotalCuotas = String.valueOf(enPrestamo.getiTotalCuotas());
            tvProductoCredito.setText(enPrestamo.getsNombreProducto());
            tvNumeroCredito.setText(String.valueOf(enPrestamo.getnNumeroCredito()));
            tvTotalCuotas.setText(String.format("%s/%s", sCuotaPagadas, sTotalCuotas));
            String sSldCapital = String.format("%s %s", enPrestamo.getsSignoMoneda(), AppUtils.formatStringDecimals(enPrestamo.getnSaldoCapital()));
            tvSaldoCapital.setText(sSldCapital);
            String sMontPrest = String.format("%s %s", enPrestamo.getsSignoMoneda(), AppUtils.formatStringDecimals(enPrestamo.getnMontoPrestamo()));
            tvMontoOriginal.setText(sMontPrest);
            tvTEA.setText(AppUtils.formatStringDecimals(enPrestamo.getnTasaInters()));
            tvDiasAtraso.setText(String.valueOf(enPrestamo.getiNroDiasAtrasado()));
            String sCuota = String.valueOf(Integer.parseInt(sTotalCuotas) - Integer.parseInt(sCuotaPagadas));
            tvCuotasRestantes.setText(sCuota);
            if (olista.size() > 0) {

                EnCuotasPendientesCredito oPendiente = olista.stream()
                        .min(Comparator.comparing(EnCuotasPendientesCredito::getiNroCuota))
                        .orElseThrow(NoSuchElementException::new);
                String sProxPago = String.format("%s %s", enPrestamo.getsSignoMoneda(), AppUtils.formatStringDecimals(oPendiente.getnPagoTotal()));
                tvFechaPago.setText(oPendiente.getsFechaVencimiento());
                tvMontoProximaCuota.setText(sProxPago);

                if (lyDetalleCreditos.getVisibility() == View.GONE) {
                    lyDetalleCreditos.setVisibility(View.VISIBLE);
                }
                if (lySinContenido.getVisibility() == View.VISIBLE) {
                    lySinContenido.setVisibility(View.GONE);
                }
            } else {
                lyProxCuota.setVisibility(View.GONE);
                lyFchPago.setVisibility(View.GONE);
                lvListaCronograma.setVisibility(View.GONE);
                lyDwnCronograma.setVisibility(View.GONE);

                if (lyDetalleCreditos.getVisibility() == View.GONE) {
                    lyDetalleCreditos.setVisibility(View.VISIBLE);
                }
                if (lySinContenido.getVisibility() == View.GONE) {
                    lySinContenido.setVisibility(View.VISIBLE);
                }
            }
            mMovimientoCreditoAdapter.addListNotifyChanged(olista, getContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle) {
        super.showFragments(fragmentManager, mFragment, sTag, bundle);
    }

    @Override
    public void onSinInformacion() {
        showFragments(getParentFragmentManager(), new ProductosFragment(), ProductosFragment.TAG, null);
    }

    @Override
    public void onDownloadCronograma(InputStream inputStream) {
        try {
            String urlFile = "";
            urlFile = AppUtils.downloadCronogramaPdf(inputStream, getContext());
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

    class onClicDescargarCronograma implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mCreditosPresenter.onDescargarCronograma(sNumeroCredito);
        }
    }
}
