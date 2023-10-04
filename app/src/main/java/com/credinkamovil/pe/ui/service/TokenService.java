package com.credinkamovil.pe.ui.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.di.component.DaggerServiceComponent;
import com.credinkamovil.pe.di.component.ServiceComponent;
import com.credinkamovil.pe.root.BaseApp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TokenService extends Service {
    private static final String TAG = TokenService.class.getCanonicalName();
    private ScheduledExecutorService forChangesServer;
    @Inject
    DataManager mDataManager;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            this.mCompositeDisposable = new CompositeDisposable();
            forChangesServer = Executors.newSingleThreadScheduledExecutor();
            ServiceComponent component = DaggerServiceComponent.builder()
                    .applicationComponent(((BaseApp) getApplication()).getComponent())
                    .build();
            component.inject(this);
            getTokenService();
        } catch (Exception ex) {
            Log.i(TAG, "Error en :" + ex.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "========Token Service started==========");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "=============Token Service stopped================");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getTokenService() {
        try {
            forChangesServer.scheduleWithFixedDelay(() -> {
                /*if (NetworkUtils.isNetworkConnected(TokenService.this)) {
                    EnParametro oEnParametro = new EnParametro();

                }*/
            }, 1, 1, TimeUnit.MINUTES);

        } catch (Exception ex) {
            Log.i(TAG, "Error service: <getTokenService>=========== " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
