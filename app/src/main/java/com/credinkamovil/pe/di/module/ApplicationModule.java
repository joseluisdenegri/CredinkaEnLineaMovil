package com.credinkamovil.pe.di.module;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.credinkamovil.pe.data.AppDataManager;
import com.credinkamovil.pe.data.DataManager;
import com.credinkamovil.pe.data.local.db.AppDatabase;
import com.credinkamovil.pe.data.local.db.AppDbHelper;
import com.credinkamovil.pe.data.local.db.DbHelper;
import com.credinkamovil.pe.data.local.prefs.AppPreferenceHelper;
import com.credinkamovil.pe.data.local.prefs.PreferenceHelper;
import com.credinkamovil.pe.data.local.prefs.PreferenceKeys;
import com.credinkamovil.pe.data.remote.ApiHelper;
import com.credinkamovil.pe.data.remote.AppApiHelper;
import com.credinkamovil.pe.data.remote.ingreso.ApiIngreso;
import com.credinkamovil.pe.data.remote.ingreso.FactoryIngreso;
import com.credinkamovil.pe.di.scope.ApplicationContext;
import com.credinkamovil.pe.di.scope.DatabaseInfo;
import com.credinkamovil.pe.di.scope.PreferenceInfo;
import com.credinkamovil.pe.utils.AppConstantes;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }


    @ApplicationContext
    @Provides
    public Context provideContext() {
        return mApplication;
    }

    @Provides
    public Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String providesDatabaseName() {
        return AppConstantes.DB_NAME;
    }

    @Provides
    @DatabaseInfo
    Integer providesDatabaseVersion() {
        return AppConstantes.DB_VERSION;
    }

    @Provides
    @PreferenceInfo
    String providesSharedPrefName() {
        return PreferenceKeys.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager providesDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper providesDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiHelper providesApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    PreferenceHelper providesPreferenceHelper(AppPreferenceHelper appPreferenceHelper) {
        return appPreferenceHelper;
    }

    @Provides
    @Singleton
    ApiIngreso providesApiCall() {
        return FactoryIngreso.create();
    }

    @Provides
    @Singleton
    AppDatabase providesAppDatabase(@ApplicationContext Context context, @DatabaseInfo String dbName) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dbName).build();
    }
}
