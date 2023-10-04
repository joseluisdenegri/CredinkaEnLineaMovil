package com.credinkamovil.pe.ui.producto.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.utils.AppUtils;

import java.util.ArrayList;

public class CreditosAdapter extends BaseAdapter {
    private ArrayList<EnPrestamo> oListaMisCreditos;
    private static Context mContext;
    private ViewHolder viewHolder = null;

    public CreditosAdapter(ArrayList<EnPrestamo> enPrestamos) {
        this.oListaMisCreditos = enPrestamos;
    }

    @Override
    public int getCount() {
        return oListaMisCreditos.size();
    }

    @Override
    public Object getItem(int i) {
        return oListaMisCreditos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return oListaMisCreditos.get(i).getnNumeroCredito();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_creditos, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tvProducto = view.findViewById(R.id.tv_producto_credito);
                viewHolder.tvNroCuenta = view.findViewById(R.id.tv_numero_cuenta_credito);
                viewHolder.tvSaldoCuenta = view.findViewById(R.id.tv_saldo_credito);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            EnPrestamo oEnPrestamo = (EnPrestamo) getItem(position);
            viewHolder.tvProducto.setText(oEnPrestamo.getsNombreProducto());
            viewHolder.tvNroCuenta.setText(String.valueOf(oEnPrestamo.getnNumeroCredito()));
            String sSaldo = oEnPrestamo.getsSignoMoneda() + " " + AppUtils.formatStringDecimals(oEnPrestamo.getnSaldoCapital());
            viewHolder.tvSaldoCuenta.setText(sSaldo);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return view;
    }

    public void addListNotifyChanged(ArrayList<EnPrestamo> listaCreditos, Context context) {
        this.oListaMisCreditos = listaCreditos;
        mContext = context;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvNroCuenta;
        public TextView tvProducto;
        public TextView tvSaldoCuenta;
    }
}