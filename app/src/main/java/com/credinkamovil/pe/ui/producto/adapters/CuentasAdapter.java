package com.credinkamovil.pe.ui.producto.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.utils.AppUtils;

import java.util.ArrayList;

public class CuentasAdapter extends BaseAdapter {
    private ViewHolder viewHolder = null;
    private ArrayList<EnCuentas> oListaMisCuentas;
    private static Context mContext;

    public CuentasAdapter(ArrayList<EnCuentas> listaMisCuentas) {
        this.oListaMisCuentas = listaMisCuentas;
    }

    @Override
    public int getCount() {
        return oListaMisCuentas.size();
    }

    @Override
    public Object getItem(int i) {
        return oListaMisCuentas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return oListaMisCuentas.get(i).getnNumeroCuenta();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_cuentas, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tvProducto = view.findViewById(R.id.tv_producto);
                viewHolder.tvNroCuenta = view.findViewById(R.id.tv_numero_cuenta);
                viewHolder.tvSaldo = view.findViewById(R.id.tv_saldo);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            EnCuentas cta = (EnCuentas) getItem(position);
            viewHolder.tvProducto.setText(cta.getsNombreProducto());
            viewHolder.tvNroCuenta.setText(String.valueOf(cta.getnNumeroCuenta()));
            String sSaldo = cta.getsSignoMoneda() + " " + AppUtils.formatStringDecimals(cta.getnSaldDisp());
            viewHolder.tvSaldo.setText(sSaldo);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return view;
    }

    public void addListNotifyChanged(ArrayList<EnCuentas> listaCuenta, Context context) {
        oListaMisCuentas = listaCuenta;
        mContext = context;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvNroCuenta;
        public TextView tvProducto;
        public TextView tvSaldo;
    }
}
