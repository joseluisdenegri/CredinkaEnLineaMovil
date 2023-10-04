package com.credinkamovil.pe.di.component;

import android.app.Application;
import android.content.Context;

import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.di.module.ApiModule;
import com.credinkamovil.pe.di.module.ApplicationModule;
import com.credinkamovil.pe.di.module.NetworkModule;
import com.credinkamovil.pe.di.scope.ApplicationContext;
import com.credinkamovil.pe.root.BaseApp;
import com.credinkamovil.pe.ui.service.TokenService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(BaseApp app);

    void inject(TokenService tokenService);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
