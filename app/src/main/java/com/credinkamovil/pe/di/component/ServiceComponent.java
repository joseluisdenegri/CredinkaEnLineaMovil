package com.credinkamovil.pe.di.component;

import com.credinkamovil.pe.di.module.ServiceModule;
import com.credinkamovil.pe.di.scope.PerService;
import com.credinkamovil.pe.ui.service.TokenService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(TokenService tokenService);
}
