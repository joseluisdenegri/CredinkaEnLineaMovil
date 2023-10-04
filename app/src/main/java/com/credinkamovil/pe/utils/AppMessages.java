package com.credinkamovil.pe.utils;

import android.app.Activity;

public class AppMessages extends Activity {
    private static AppMessages mInstance = null;
    public static final int BACKEND_CODE_0 = 0;
    public static final int BACKEND_CODE_99 = 99;
    public static final int BACKEND_CODE_100 = 100;
    public static final int BACKEND_CODE_101 = 101;
    public static final int BACKEND_CODE_102 = 102;
    public static final int BACKEND_CODE_103 = 103;
    public static final int BACKEND_CODE_999 = 999;

    private static final String BACKEND_DESC_0 = "¿Estas seguro que deseas salir de la Banca Móvil del Credinka en Línea?";
    private static final String BACKEND_DESC_99 = "Es necesario acceder permisos a la aplicación para su funcionamiento correcto.";
    private static final String BACKEND_DESC_100 = "Lo sentimos, tu tiempo de conexión ha expirado por inactividad. Por tu seguridad debes ingresar nuevamente los datos.";
    private static final String BACKEND_DESC_101 = "Aún no tenemos ofertas exclusivas para ti.";
    private static final String BACKEND_DESC_102 = "En estos momentos no te podemos atender, por favor intenta nuevamanete mas tarde (90)";
    private static final String BACKEND_DESC_103 = "Cuenta aperturado correctamente. Ya puedes utilizar tu nueva cuenta de ahorro";
    private static final String GENERAL_DESC_ERROR = "Lo sentimos, tu operación no ha podido ser realizada. Estamos trabajando para solucionar el inconveniente.";
    private static final String BACKEND_DESC_999 = "Lo sentimos, en estos momentos no está disponible el servicio. Por favor intenta nuevamente mas tarde.";

    public static AppMessages getInstance() {
        if (mInstance == null) {
            mInstance = new AppMessages();
        }
        return mInstance;
    }

    public String getAppMessages(int iCode) {
        switch (iCode) {
            case BACKEND_CODE_0:
                getInstance().getClass();
                return BACKEND_DESC_0;
            case BACKEND_CODE_99:
                getInstance().getClass();
                return BACKEND_DESC_99;
            case BACKEND_CODE_100:
                getInstance().getClass();
                return BACKEND_DESC_100;
            case BACKEND_CODE_101:
                getInstance().getClass();
                return BACKEND_DESC_101;
            case BACKEND_CODE_102:
                getInstance().getClass();
                return BACKEND_DESC_102;
            case BACKEND_CODE_103:
                getInstance().getClass();
                return BACKEND_DESC_103;
            case BACKEND_CODE_999:
                getInstance().getClass();
                return BACKEND_DESC_999;
            default:
                getInstance();
                return GENERAL_DESC_ERROR;
        }
    }
}
