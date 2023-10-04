package com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.DetalleCuentasFragment;

import java.util.ArrayList;

public class PeriodoAdapter extends BaseAdapter {
    private ArrayList<EnPeriodo> oListaPeriodo;
    private static Context mContext;
    private ViewHolder viewHolder = null;
    private CallbackPeriodo callbackPer;
    private String sNroCuentaAhr;

    public PeriodoAdapter(ArrayList<EnPeriodo> listaPeriodo) {
        this.oListaPeriodo = listaPeriodo;
    }

    @Override
    public int getCount() {
        return oListaPeriodo.size();
    }

    @Override
    public Object getItem(int position) {
        return oListaPeriodo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return oListaPeriodo.get(position).getiCodigo();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_periodo, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.tvNombrePerido = view.findViewById(R.id.tv_nombre_perido);
                viewHolder.imvDown = view.findViewById(R.id.img_check_down);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            final EnPeriodo per = (EnPeriodo) getItem(position);
            viewHolder.tvNombrePerido.setText(per.getsMes());
            viewHolder.imvDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callbackPer != null) {
                        callbackPer.onDownItemClicked(per, sNroCuentaAhr);
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return view;
    }

    public void addListNotifyChanged(ArrayList<EnPeriodo> listaPeriodo, Context context, String sNumeroCuenta) {
        oListaPeriodo = listaPeriodo;
        mContext = context;
        sNroCuentaAhr = sNumeroCuenta;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private TextView tvNombrePerido;
        private ImageButton imvDown;
    }

    public interface CallbackPeriodo {
        void onDownItemClicked(EnPeriodo periodo, String sNroCuenta);
    }

    public void setCallbackPeriodo(CallbackPeriodo callback) {
        this.callbackPer = callback;
    }

    public void removeCallback() {
        callbackPer = null;
    }
}
