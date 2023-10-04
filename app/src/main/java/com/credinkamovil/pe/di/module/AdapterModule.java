package com.credinkamovil.pe.di.module;

import com.credinkamovil.pe.data.models.EnCuentas;
import com.credinkamovil.pe.data.models.EnCuotasPendientesCredito;
import com.credinkamovil.pe.data.models.EnMovimientoCuenta;
import com.credinkamovil.pe.data.models.EnPeriodo;
import com.credinkamovil.pe.data.models.EnPrestamo;
import com.credinkamovil.pe.data.models.ItemOption;
import com.credinkamovil.pe.ui.detalleproducto.creditos.adapters.MovimientoCreditoAdapter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters.MovimientoCuentasAdapter;
import com.credinkamovil.pe.ui.detalleproducto.cuentas.adapters.PeriodoAdapter;
import com.credinkamovil.pe.ui.dialogs.adapters.CustomDialogAdapter;
import com.credinkamovil.pe.ui.producto.adapters.CreditosAdapter;
import com.credinkamovil.pe.ui.producto.adapters.CuentasAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    CuentasAdapter provideMisCuentasAdapter() {
        return new CuentasAdapter(new ArrayList<EnCuentas>());
    }

    @Provides
    CreditosAdapter provideMisCreditosAdapter() {
        return new CreditosAdapter(new ArrayList<EnPrestamo>());
    }

    @Provides
    MovimientoCuentasAdapter provideMovimientoCuentasAdapter() {
        return new MovimientoCuentasAdapter(new ArrayList<EnMovimientoCuenta>());
    }

    @Provides
    MovimientoCreditoAdapter provideMovimientoCreditoAdapter() {
        return new MovimientoCreditoAdapter(new ArrayList<EnCuotasPendientesCredito>());
    }

    @Provides
    CustomDialogAdapter provideCustomDialogAdapter() {
        return new CustomDialogAdapter(new ArrayList<ItemOption>());
    }

    @Provides
    PeriodoAdapter providePeriodoAdapter() {
        return new PeriodoAdapter(new ArrayList<EnPeriodo>());
    }
}
