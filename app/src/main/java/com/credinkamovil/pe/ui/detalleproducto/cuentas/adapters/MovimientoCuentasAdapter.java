package com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.utils.AppUtils;

import java.util.ArrayList;

public class MovimientoCuentasAdapter extends BaseAdapter {
    private ArrayList<EnMovimientoCuenta> oListaMovimientoCuenta;
    private static Context mContext;
    private ViewHolder viewHolder = null;

    public MovimientoCuentasAdapter(ArrayList<EnMovimientoCuenta> listaMovimiento) {
        this.oListaMovimientoCuenta = listaMovimiento;
    }

    @Override
    public int getCount() {
        return oListaMovimientoCuenta.size();
    }

    @Override
    public Object getItem(int i) {
        return oListaMovimientoCuenta.get(i);
    }

    @Override
    public long getItemId(int i) {
        return oListaMovimientoCuenta.get(i).getiJtsOid();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_movimiento_cuenta, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tvDescripcionMovimiento = view.findViewById(R.id.tv_descripcion_movimiento);
                viewHolder.tvFechaMovimiento = view.findViewById(R.id.tv_fecha_movimiento);
                viewHolder.tvImporte = view.findViewById(R.id.tv_importe_movimiento);

                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            final EnMovimientoCuenta cta = (EnMovimientoCuenta) getItem(position);
            String sSigno = cta.getsSigno();
            String sTipoMoneda = cta.getsSignoMoneda();
            if (sSigno.equals("+")) {
                sSigno = "";
                viewHolder.tvImporte.setTextColor(Color.parseColor("#808080"));
            } else {
                sSigno = "-";
                viewHolder.tvImporte.setTextColor(Color.parseColor("#E22639"));
            }

            String sImporte = String.format("%s %s%s", sTipoMoneda, sSigno, AppUtils.formatStringDecimals(cta.getnImporteMov()));
            viewHolder.tvDescripcionMovimiento.setText(cta.getsConcepto());
            viewHolder.tvFechaMovimiento.setText(cta.getsFechaProceso());
            viewHolder.tvImporte.setText(sImporte);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return view;
    }

    public void addListNotifyChanged(ArrayList<EnMovimientoCuenta> listaMovimiento, Context context) {
        oListaMovimientoCuenta = listaMovimiento;
        mContext = context;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView tvDescripcionMovimiento;
        public TextView tvFechaMovimiento;
        public TextView tvImporte;
    }
}
