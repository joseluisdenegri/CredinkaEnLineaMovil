package com.credinkamovil.pe.ui.dialogs;

import com.credinkamovil.pe.ui.base.MvpPresenter;

public interface CuentaDialogMvpPresenter<V extends CuentaDialogMvpView> extends MvpPresenter<V> {
    void onClickedEECC(int iOpcion, String sNroCta);
}
