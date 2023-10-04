package com.credinkamovil.pe.ui.detalleproducto.cuentas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnDetalleMovimiento;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.data.models.ItemOption;
import com.credinkamovil.pe.ui.base.MvpView;

import java.util.ArrayList;

public interface CuentasMvpView extends MvpView {
    void onCompletarDetalleMovimiento(EnCuentas enCuentas, ArrayList<EnMovimientoCuenta> listaMovimiento);

    void showFragmentDetalleMovimiento(EnDetalleMovimiento detalleMovimiento);

    void showFragmentProducto();

    void showDialogFragmentCuentaOpcion(ArrayList<ItemOption> listaOpcion, String sNameCta);
    
    void showFragments(FragmentManager fragmentManager, Fragment mFragment, String sTag, Bundle bundle);
}
