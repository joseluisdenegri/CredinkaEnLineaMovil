package com.credinkamovil.pe.ui.dialogs;

import android.util.Log;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CuentaDialogPresenter<V extends CuentaDialogMvpView> extends BasePresenter<V> implements CuentaDialogMvpPresenter<V> {
    @Inject
    public CuentaDialogPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onClickedEECC(int iOpcion, String sNroCta) {
        try {
            getMvpView().showFragmentEstadoCuenta(iOpcion, sNroCta);
            getMvpView().dismissDialog(CuentaDialogFragment.TAG);
        } catch (Exception ex) {
            Log.e("ClickedEECC", "Error en ====> " + ex.getMessage());
        }
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }
}
