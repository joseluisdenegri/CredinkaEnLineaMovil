package com.credinkamovil.pe.di.module;

import com.credinkamovil.pe.utils.EncryptionUtils;

import dagger.Module;
import dagger.Provides;

@Module
public class EntitiesModule {
    @Provides
    public EncryptionUtils provideEncryptionUtils() {
        return new EncryptionUtils();
    }
}
