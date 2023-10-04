package com.credinkamovil.pe.ui.detalleproducto.creditos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCuotasPendientesCredito;
import com.credinkamovil.pe.utils.AppUtils;

import java.util.ArrayList;

public class MovimientoCreditoAdapter extends BaseAdapter {
    private ArrayList<EnCuotasPendientesCredito> oListaMovimientoCuota;
    private Context mContext;
    private ViewHolder viewHolder = null;

    public MovimientoCreditoAdapter(ArrayList<EnCuotasPendientesCredito> oLista) {
        this.oListaMovimientoCuota = oLista;
    }

    @Override
    public int getCount() {
        return oListaMovimientoCuota.size();
    }

    @Override
    public Object getItem(int i) {
        return oListaMovimientoCuota.get(i);
    }

    @Override
    public long getItemId(int i) {
        return oListaMovimientoCuota.get(i).getiNroCuota();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_movimiento_credito, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tvNombreMovimiento = view.findViewById(R.id.tv_descripcion_movimiento);
                viewHolder.tvMontoPagar = view.findViewById(R.id.tv_monto_pagar);
                viewHolder.tvFechaPagoCuota = view.findViewById(R.id.tv_fecha_movimiento);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            final EnCuotasPendientesCredito movimiento = (EnCuotasPendientesCredito) getItem(position);
           viewHolder.tvNombreMovimiento.setText(String.format("%s %s","Cuota Nro. ", movimiento.getiNroCuota()));
            viewHolder.tvMontoPagar.setText(AppUtils.formatStringDecimals(movimiento.getnPagoTotal()));
            viewHolder.tvFechaPagoCuota.setText(movimiento.getsFechaVencimiento());
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return view;
    }

    public void addListNotifyChanged(ArrayList<EnCuotasPendientesCredito> olista, Context context) {
        this.mContext = context;
        this.oListaMovimientoCuota = olista;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView tvNombreMovimiento, tvMontoPagar, tvFechaPagoCuota;
    }
}
