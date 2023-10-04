package com.credinkamovil.pe.ui.login.bloquear;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.models.EnContacto;
import com.credinkamovil.pe.data.models.EnResultService;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.utils.NetworkUtils;
import com.credinkamovil.pe.utils.StatusService;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class BloqueoPresenter<V extends BloqueoMvpView> extends BasePresenter<V> implements BloqueoMvpPresenter<V> {
    private AlertDialog mAlertDialog;

    @Inject
    public BloqueoPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        obtenerListaContactos();
    }

    private void obtenerListaContactos() {
        try {
            getMvpView().showLoading("Cargando");
            String sId = getDataManager().getStringValue(PreferenceKeys.PREF_UISID, null);
            getCompositeDisposable().add(getDataManager()
                    .obtenerListaContactos(sId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<EnResultService>() {
                        @Override
                        public void accept(EnResultService enResultService) throws Exception {
                            if (enResultService.getiResultado() == StatusService.OK && enResultService.getbResultado()) {
                                Gson gson = new GsonBuilder().create();
                                JsonElement jsonElement = gson.toJsonTree(enResultService.getObContent());
                                ArrayList<EnContacto> listaContacto = NetworkUtils.httpParsearContacto(jsonElement, 1);
                                String sNumNacional1 = "";
                                String sNumNacional2 = "";
                                String sNumUsaCanada = "";
                                String sNumRestoMundo = "";
                                if (listaContacto.size() > 0) {
                                    for (int coffe = 0; coffe < listaContacto.size(); coffe++) {
                                        int iTipoContacto = listaContacto.get(coffe).getiTipoContacto();
                                        int iOrder = listaContacto.get(coffe).getiOrder();
                                        switch (iTipoContacto) {
                                            case 1:
                                                if (iOrder == 1)
                                                    sNumNacional1 = listaContacto.get(coffe).getsNumeroTelefono();
                                                else
                                                    sNumNacional2 = listaContacto.get(coffe).getsNumeroTelefono();
                                                break;
                                            case 2:
                                                sNumUsaCanada = listaContacto.get(coffe).getsNumeroTelefono();
                                                break;
                                            case 3:
                                                sNumRestoMundo = listaContacto.get(coffe).getsNumeroTelefono();
                                                break;
                                        }
                                    }
                                    getMvpView().onCompletarDatosContacto(sNumNacional1, sNumNacional2, sNumUsaCanada, sNumRestoMundo);
                                } else {
                                    mAlertDialog = getMvpView().showAlertDialog("Lo sentimos, la información solicitada no se generó. Estamos trabajando para solucionar el inconveniente.",
                                            new onClicOK(), false);
                                }
                                getMvpView().hideLoading();
                            } else {
                                getMvpView().hideLoading();
                                mAlertDialog = getMvpView().showAlertDialog(enResultService.getsMensaje(), new onClicOK(), false);
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            if (!isViewAttached())
                                return;
                            getMvpView().hideLoading();
                            mAlertDialog = getMvpView().showAlertDialog("Lo sentimos, la información solicitada no se generó. Estamos trabajando para solucionar el inconveniente.",
                                    new onClicOK(), false);
                        }
                    })
            );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class onClicOK implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mAlertDialog.dismiss();
            getMvpView().intentLogin();
        }
    }
}
