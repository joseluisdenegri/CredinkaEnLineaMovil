package com.credinkamovil.pe.ui.dialogs.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.credinkamovil.pe.R;
import com.credinkamovil.pe.data.models.ItemOption;

import java.util.ArrayList;

public class CustomDialogAdapter extends BaseAdapter {
    private ArrayList<ItemOption> itemListOptions;
    private Context mContext;

    public CustomDialogAdapter(ArrayList<ItemOption> itemOptions) {
        this.itemListOptions = itemOptions;
    }

    @Override
    public int getCount() {
        return itemListOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return itemListOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemListOptions.get(position).getiIdOption();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_cuenta, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.imvImage = convertView.findViewById(R.id.imv_imagen_opcion);
                viewHolder.tvIdOption = convertView.findViewById(R.id.tv_id_opcion);
                viewHolder.tvNombre = convertView.findViewById(R.id.tv_nombre_opcion);

                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            ItemOption item = (ItemOption) getItem(position);

            String sImagen = item.getsNombreOpcion();
            int iImagen = item.getiImageDrawable();
            int iIdopcion = item.getiIdOption();

            viewHolder.imvImage.setImageResource(iImagen);
            viewHolder.tvIdOption.setText(String.valueOf(iIdopcion));
            viewHolder.tvNombre.setText(sImagen);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), "ERROR EN: <getView> " + ex.getMessage());
        }
        return convertView;
    }

    public void addListNotifyChanged(ArrayList<ItemOption> itemListaOptions, Context context) {
        itemListOptions = itemListaOptions;
        mContext = context;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        private ImageView imvImage;
        public TextView tvIdOption;
        public TextView tvNombre;
    }
}
