package com.credinkamovil.pe.di.module;

import com.credinkamovil.pe.data.remote.producto.ApiCampanas;
import com.credinkamovil.pe.data.remote.producto.ApiCuentas;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Singleton
    @Provides
    ApiCuentas provideApiCuentas(Retrofit retrofit) {
        return retrofit.create(ApiCuentas.class);
    }

    @Singleton
    @Provides
    ApiCampanas provideApiCampana(Retrofit retrofit) {
        return retrofit.create(ApiCampanas.class);
    }
}
