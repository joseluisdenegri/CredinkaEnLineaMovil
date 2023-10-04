package com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MovimientoCuentaPresenter<V extends MovimientoCuentaMvpView> extends BasePresenter<V>
        implements MovimientoCuentaMvpPresenter<V> {
    private APIServiceHolder mApiServiceHolder = null;
    @Inject
    public MovimientoCuentaPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onObtenerDatosMovimientoDetalle() {
        getMvpView().showLoading("Cargando");
        getMvpView().onCompletarDatosDetalleMovimiento();
        mApiServiceHolder.LogoutMobileApp(2);
        getMvpView().hideLoading();
    }
}