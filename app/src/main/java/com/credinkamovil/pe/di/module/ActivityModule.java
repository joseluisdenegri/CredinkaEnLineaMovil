package com.credinkamovil.pe.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.credinkamovil.pe.di.scope.ActivityContext;
import com.credinkamovil.pe.di.scope.PerActivity;
import com.credinkamovil.pe.ui.detalleproducto.creditos.CreditosMvpPresenter;
import com.credinkamovil.pe.ui.detalleproducto.creditos.CreditosMvpView;
import com.credinkamovil.pe.ui.detalleproducto.creditos.DetalleCreditosPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.CuentasMvpPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.CuentasMvpView;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.DetalleCuentasPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaMvpPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaMvpView;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.eecc.EstadoCuentaPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento.MovimientoCuentaMvpPresenter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento.MovimientoCuentaMvpView;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.movimiento.MovimientoCuentaPresenter;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogMvpPresenter;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogMvpView;
import com.credinkamovil.pe.ui.dialogs.CuentaDialogPresenter;
import com.credinkamovil.pe.ui.login.LoginMvpPresenter;
import com.credinkamovil.pe.ui.login.LoginMvpView;
import com.credinkamovil.pe.ui.login.LoginPresenter;
import com.credinkamovil.pe.ui.login.bloquear.BloqueoMvpPresenter;
import com.credinkamovil.pe.ui.login.bloquear.BloqueoMvpView;
import com.credinkamovil.pe.ui.login.bloquear.BloqueoPresenter;
import com.credinkamovil.pe.ui.login.contacto.ContactoMvpPresenter;
import com.credinkamovil.pe.ui.login.contacto.ContactoMvpView;
import com.credinkamovil.pe.ui.login.contacto.ContactoPresenter;
import com.credinkamovil.pe.ui.main.MainMvpPresenter;
import com.credinkamovil.pe.ui.main.MainMvpView;
import com.credinkamovil.pe.ui.main.MainPresenter;
import com.credinkamovil.pe.ui.maps.UbicacionMvpPresenter;
import com.credinkamovil.pe.ui.maps.UbicacionMvpView;
import com.credinkamovil.pe.ui.maps.UbicacionPresenter;
import com.credinkamovil.pe.ui.maps.fragments.MapLocateMvpPresenter;
import com.credinkamovil.pe.ui.maps.fragments.MapLocateMvpView;
import com.credinkamovil.pe.ui.maps.fragments.MapLocatePresenter;
import com.credinkamovil.pe.ui.mas.MasMvpPresenter;
import com.credinkamovil.pe.ui.mas.MasMvpView;
import com.credinkamovil.pe.ui.mas.MasPresenter;
import com.credinkamovil.pe.ui.ofertas.OfertasMvpPresenter;
import com.credinkamovil.pe.ui.ofertas.OfertasMvpView;
import com.credinkamovil.pe.ui.ofertas.OfertasPresenter;
import com.credinkamovil.pe.ui.ofertas.comercial.OfertaCreditoMvpPresenter;
import com.credinkamovil.pe.ui.ofertas.comercial.OfertaCreditoMvpView;
import com.credinkamovil.pe.ui.ofertas.comercial.OfertaCreditoPresenter;
import com.credinkamovil.pe.ui.perfil.PerfilMvpPresenter;
import com.credinkamovil.pe.ui.perfil.PerfilMvpView;
import com.credinkamovil.pe.ui.perfil.PerfilPresenter;
import com.credinkamovil.pe.ui.producto.ProductosMvpPresenter;
import com.credinkamovil.pe.ui.producto.ProductosMvpView;
import com.credinkamovil.pe.ui.producto.ProductosPresenter;
import com.credinkamovil.pe.ui.splash.SplashMvpPresenter;
import com.credinkamovil.pe.ui.splash.SplashMvpView;
import com.credinkamovil.pe.ui.splash.SplashPresenter;
import com.credinkamovil.pe.utils.rx.AppSchedulerProvider;
import com.credinkamovil.pe.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {
    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideScheduleProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> splashPresenter) {
        return splashPresenter;
    }

    @Provides
    @PerActivity
    LoginMvpPresenter<LoginMvpView> provideLoginMvpPresenter(LoginPresenter<LoginMvpView> loginPresenter) {
        return loginPresenter;
    }

    @Provides
    @PerActivity
    ContactoMvpPresenter<ContactoMvpView> provideContactoMvpPresenter(ContactoPresenter<ContactoMvpView> contactoPresenter) {
        return contactoPresenter;
    }

    @Provides
    @PerActivity
    BloqueoMvpPresenter<BloqueoMvpView> provideBloqueoMvpPresenter(BloqueoPresenter<BloqueoMvpView> bloqueoPresenter) {
        return bloqueoPresenter;
    }

    @Provides
    @PerActivity
    UbicacionMvpPresenter<UbicacionMvpView> provideUbicacionMvpPresenter(UbicacionPresenter<UbicacionMvpView> ubicacionPresenter) {
        return ubicacionPresenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainMvpPresenter(MainPresenter<MainMvpView> mainPresenter) {
        return mainPresenter;
    }

    @Provides
    ProductosMvpPresenter<ProductosMvpView> provideProductosMvpPresenter(ProductosPresenter<ProductosMvpView> productosPresenter) {
        return productosPresenter;
    }

    @Provides
    OfertasMvpPresenter<OfertasMvpView> provideOfertasMvpPresenter(OfertasPresenter<OfertasMvpView> ofertasPresenter) {
        return ofertasPresenter;
    }

    @Provides
    MasMvpPresenter<MasMvpView> provideMasMvpPresenter(MasPresenter<MasMvpView> masPresenter) {
        return masPresenter;
    }

    @Provides
    CuentasMvpPresenter<CuentasMvpView> provideDetalleCuentasPresenter(
            DetalleCuentasPresenter<CuentasMvpView> cuentasPresenter) {
        return cuentasPresenter;
    }

    @Provides
    CreditosMvpPresenter<CreditosMvpView> provideCreditosMvpPresenter(DetalleCreditosPresenter<CreditosMvpView> creditosPresenter) {
        return creditosPresenter;
    }

    @Provides
    MovimientoCuentaMvpPresenter<MovimientoCuentaMvpView> provideMovimientoCuentaMvpPresenter(
            MovimientoCuentaPresenter<MovimientoCuentaMvpView> movimientoCuentaPresenter) {
        return movimientoCuentaPresenter;
    }

    @Provides
    MapLocateMvpPresenter<MapLocateMvpView> provideMapLocateMvpPresenter(MapLocatePresenter<MapLocateMvpView> mapLocatePresenter) {
        return mapLocatePresenter;
    }

    @Provides
    PerfilMvpPresenter<PerfilMvpView> providePerfilMvpPresenter(PerfilPresenter<PerfilMvpView> perfilPresenter) {
        return perfilPresenter;
    }

    @Provides
    OfertaCreditoMvpPresenter<OfertaCreditoMvpView> provideOfertaCreditoMvpPresenter(OfertaCreditoPresenter<OfertaCreditoMvpView> ofertaCreditoPresenter) {
        return ofertaCreditoPresenter;
    }

    @Provides
    CuentaDialogMvpPresenter<CuentaDialogMvpView> provideCuentaDialogMvpPresenter(CuentaDialogPresenter<CuentaDialogMvpView> cuentaDialogPresenter) {
        return cuentaDialogPresenter;
    }

    @Provides
    EstadoCuentaMvpPresenter<EstadoCuentaMvpView> provideEstadoCuentaMvpPresenter(EstadoCuentaPresenter<EstadoCuentaMvpView> estadoCuentaPresenter) {
        return estadoCuentaPresenter;
    }
}
