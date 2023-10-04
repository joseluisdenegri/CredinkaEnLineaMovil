package com.credinkamovil.pe.ui.dialogs;

import com.credinkamovil.pe.ui.base.DialogMvpView;

public interface CuentaDialogMvpView extends DialogMvpView {
    void dismissDialog();

    void showFragmentEstadoCuenta(int iOpcion, String sNroCta);
}

