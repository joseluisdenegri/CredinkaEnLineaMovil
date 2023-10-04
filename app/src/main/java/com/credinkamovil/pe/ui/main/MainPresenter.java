package com.credinkamovil.pe.ui.main;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentTransaction;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.ui.base.BasePresenter;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogFragment;
import com.credinkamovil.pe.ui.service.APIServiceHolder;
import com.credinkamovil.pe.utils.AppMessages;
import com.credinkamovil.pe.utils.FragmentUtils;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private AlertDialog mAlertDialog;
    private APIServiceHolder mApiServiceHolder = null;

    @Inject
    public MainPresenter(SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable, DataManager dataManager) {
        super(schedulerProvider, compositeDisposable, dataManager);
        mApiServiceHolder = new APIServiceHolder(compositeDisposable, schedulerProvider, dataManager);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void onInitFragment(FragmentTransaction mFragment) {
        getMvpView().initFragment(mFragment);
    }

    @Override
    public void onClickFragmentProducto(FragmentTransaction mFragment) {
        getMvpView().showFragmentProducto(mFragment);
    }

    @Override
    public void onClickFragmentOfrecemos(FragmentTransaction mFragment) {
        getMvpView().showFragmentOfertas(mFragment);
    }

    @Override
    public void onClickFragmentMas(FragmentTransaction mFragment) {
        getMvpView().showFragmentMas(mFragment);
    }

    @Override
    public void finishApplication() {
        mAlertDialog = getMvpView().showAlertDialog(AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_100),
                new onClicOK(), false);
    }

    @Override
    public void onBackPressed() {
        mAlertDialog = getMvpView().showConfirmDialog("Confimación", AppMessages.getInstance().getAppMessages(AppMessages.BACKEND_CODE_0),
                new onClicButtom(), "No", "Sí");
    }
    class onClicOK implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mApiServiceHolder.RegistrarBitacora("Cerrando Sesión", "End");
            mApiServiceHolder.LogoutMobileApp(1);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
            getDataManager().removeKey(PreferenceKeys.PREF_VALID_CAMPANA);
            getDataManager().removeKey(PreferenceKeys.PREF_UISID);
            getDataManager().removeKey(PreferenceKeys.PREF_CTAS_CRE);
            getDataManager().removeKey(PreferenceKeys.PREF_CTAS_AHR);
            mAlertDialog.dismiss();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getMvpView().intentLoginActivity();
                }
            }, 200);
        }
    }
    class onClicButtom implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mApiServiceHolder.RegistrarBitacora("Cerrando Sesión", "End");
            mApiServiceHolder.LogoutMobileApp(1);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN);
            getDataManager().removeKey(PreferenceKeys.PREF_KEY_TOKEN_REFRESH);
            getDataManager().removeKey(PreferenceKeys.PREF_SESION);
            getDataManager().removeKey(PreferenceKeys.PREF_VALUE_TIMEOUT);
            getDataManager().removeKey(PreferenceKeys.PREF_VALID_CAMPANA);
            getDataManager().removeKey(PreferenceKeys.PREF_UISID);
            getDataManager().removeKey(PreferenceKeys.PREF_CTAS_CRE);
            getDataManager().removeKey(PreferenceKeys.PREF_CTAS_AHR);
            mAlertDialog.dismiss();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getMvpView().finishApplication();
                }
            }, 200);
        }
    }
}
