package com.credinkamovil.pe.di.component;

import com.credinkamovil.pe.di.module.ActivityModule;
import com.credinkamovil.pe.di.module.AdapterModule;
import com.credinkamovil.pe.di.module.EntitiesModule;
import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.detalleproducto.creditos.DetalleCreditosFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.DetalleCuentasFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaFragment;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento.MovimientoDetCuentaFragment;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogFragment;
import com.credinkamovil.pe.ui.login.LoginActivity;
import com.credinkamovil.pe.ui.login.bloquear.BloqueoActivity;
import com.credinkamovil.pe.ui.login.contacto.ContactoActivity;
import com.credinkamovil.pe.ui.main.MainActivity;
import com.credinkamovil.pe.ui.maps.UbicacionMapsActivity;
import com.credinkamovil.pe.ui.maps.fragments.MapLocateFragment;
import com.credinkamovil.pe.ui.mas.MasFragment;
import com.credinkamovil.pe.ui.ofertas.OfertasFragment;
import com.credinkamovil.pe.ui.ofertas.comercial.OfertaCreditoFragment;
import com.credinkamovil.pe.ui.perfil.PerfilFragment;
import com.credinkamovil.pe.ui.producto.ProductosFragment;
import com.credinkamovil.pe.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {ActivityModule.class, EntitiesModule.class, AdapterModule.class})
public interface ActivityComponent {
    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(ContactoActivity contactoActivity);

    void inject(BloqueoActivity bloqueoActivity);

    void inject(UbicacionMapsActivity ubicacionMapsActivity);

    void inject(MainActivity mainActivity);

    void inject(ProductosFragment productosFragment);

    void inject(OfertasFragment ofertasFragment);

    void inject(MasFragment masFragment);

    void inject(DetalleCuentasFragment detalleCuentasFragment);

    void inject(DetalleCreditosFragment detalleCreditosFragment);

    void inject(MovimientoDetCuentaFragment movimientoDetCuentaFragment);

    void inject(MapLocateFragment mapLocateFragment);

    void inject(PerfilFragment perfilFragment);

    void inject(OfertaCreditoFragment ofertaCreditoFragment);

    void inject(CuentaDialogFragment cuentaDialogFragment);

    void inject(EstadoCuentaFragment estadoCuentaFragment);
}
