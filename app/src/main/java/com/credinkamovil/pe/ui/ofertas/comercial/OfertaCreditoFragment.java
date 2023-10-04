package com.credinkamovil.pe.ui.ofertas.comercial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnOfertaComercial;
import com.credinkamovil.pe.di.component.ActivityComponent;
import com.credinkamovil.pe.ui.base.BaseFragment;

import javax.inject.Inject;

public class OfertaCreditoFragment extends BaseFragment implements OfertaCreditoMvpView {
    public static final String TAG = OfertaCreditoFragment.class.getName();
    private static EnOfertaComercial oEnOfertaComercial;
    private TextView tvNombreCliente;
    private TextView tvMontoCredito;
    private Button btnAceptar;

    @Inject
    OfertaCreditoMvpPresenter<OfertaCreditoMvpView> mPresenterOfertas;

    public OfertaCreditoFragment() {
    }

    public static OfertaCreditoFragment newInstance() {
        OfertaCreditoFragment fragment = new OfertaCreditoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oEnOfertaComercial = new EnOfertaComercial();
        if (getArguments() != null) {
            oEnOfertaComercial = (EnOfertaComercial) getArguments().getSerializable("oEnOfertaComercial");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = null;
        try {
            mView = inflater.inflate(R.layout.fragment_oferta_credito, container, false);
            ActivityComponent mComponent = getActivityComponent();
            if (mComponent != null) {
                mComponent.inject(this);
                mPresenterOfertas.onAttach(this);
            }
        } catch (Exception ex) {
            Log.i(TAG, "Error: " + ex.getMessage());
        }
        return mView;
    }

    @Override
    protected void setUpView(View view) {
        try {
            tvNombreCliente = view.findViewById(R.id.tv_nombres);
            tvMontoCredito = view.findViewById(R.id.tv_monto);
            btnAceptar = view.findViewById(R.id.btn_si);

            mPresenterOfertas.onOfertaCredito();
            btnAceptar.setOnClickListener(new onClickConfirmarCampana());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        mPresenterOfertas.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onCompletarDatosOferta() {
        try {
            tvNombreCliente.setText(oEnOfertaComercial.getsMensaje());
            //String sSimbMoneda = AppUtils.getMontoSigno(oEnOfertaComercial.getsMoneda());
            //String sMonto = AppUtils.formatStringDecimals(oEnOfertaComercial.getnMonto());
            //tvMontoCredito.setText(String.format("%s%s", sSimbMoneda, sMonto));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConfirmarCampanaComercial() {
        Toast.makeText(getContext(), getString(R.string.str_mensaje_confirm_oferta), Toast.LENGTH_LONG).show();
    }

    class onClickConfirmarCampana implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPresenterOfertas.onConfirmarCampanaComercial();
        }
    }
}