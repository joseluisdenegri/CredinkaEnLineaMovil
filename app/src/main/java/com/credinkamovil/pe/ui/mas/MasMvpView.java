package com.credinkamovil.pe.ui.mas;

import com.credinkamovil.pe.ui.base.MvpView;

public interface MasMvpView extends MvpView {
    void onCompletarDatosTipoCambio(String sCompra, String sVenta, int iEstado);

    void onCerrarSesionApp();

    void onHabilitarGPS(boolean flagGps);
}
