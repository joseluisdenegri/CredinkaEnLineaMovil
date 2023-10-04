package com.credinkamovil.pe.utils;

import android.app.Activity;
import android.util.Log;

import com.credinkamovil.pe.R;

public class AppResourceUtils extends Activity {
    public static final String TAG = AppResourceUtils.class.getCanonicalName();
    public static TimerSessionCount mSessionTimer;

    public static boolean validateNumberCard(String numberCard) {
        return numberCard.length() >= 15;
    }

    public static String getFormatNumber(String numberCard, Activity activity) {
        int size = numberCard.length();
        if (size < 7) {
            return numberCard;
        }
        String numberCard2 = numberCard.substring(0, 10) +
                activity.getString(R.string.point) +
                activity.getString(R.string.point) +
                activity.getString(R.string.point) +
                activity.getString(R.string.point) +
                activity.getString(R.string.point) +
                activity.getString(R.string.point) +
                numberCard.substring(16, size);
        if (numberCard2.length() == 15) {
            return numberCard2.substring(0, (size + 1) / 4) +
                    AppConstantes.SPACE + numberCard2.substring((size + 1) / 4, (size + 1) / 2) +
                    AppConstantes.SPACE + numberCard2.substring((size + 1) / 2, ((size + 1) * 3) / 4) +
                    AppConstantes.SPACE + numberCard2.substring((size * 4) / 5, size);
        }
        return numberCard2.substring(0, size / 4) +
                AppConstantes.SPACE + numberCard2.substring(size / 4, size / 2) +
                AppConstantes.SPACE + numberCard2.substring(size / 2, (size * 3) / 4) +
                AppConstantes.SPACE + numberCard2.substring((size * 3) / 4, size);
    }

    public static String getPartialAccount(String cardNumber) {
        int size = cardNumber.length();
        String partial = AppConstantes.ZERO;
        String cardNumber2 = cardNumber.substring(0, 10) + partial + partial + partial + partial + partial + partial + cardNumber.substring(16, size);
        if (cardNumber2.length() == 15) {
            return cardNumber2.substring(0, (size + 1) / 4) + cardNumber2.substring((size + 1) / 4, (size + 1) / 2) + cardNumber2.substring((size + 1) / 2, ((size + 1) * 3) / 4) + cardNumber2.substring((size * 4) / 5, size);
        }
        return cardNumber2.substring(0, size / 4) + cardNumber2.substring(size / 4, size / 2) + cardNumber2.substring(size / 2, (size * 3) / 4) + cardNumber2.substring((size * 3) / 4, size);
    }

    public static void restartCountDown(Activity activity) {
        Log.i(TAG, "restartCountDown start!");
        if (mSessionTimer != null) {
            mSessionTimer.cancel();
            mSessionTimer = null;
            Log.i(TAG, "restartCountDown end!");
        }
        long tiempo = (long) (activity.getResources().getInteger(R.integer.mobile_banking_sesion) * 1000);
        Log.d(TAG, String.format("Time session (seg): %s", new Object[]{Long.valueOf(tiempo)}));
        mSessionTimer = new TimerSessionCount(tiempo, 1000, activity);
        mSessionTimer.start();
    }

    public static TimerSessionCount getSessionTimerCount() {
        return mSessionTimer;
    }

    public static void cancelSessionTimer() {
        if (mSessionTimer != null) {
            mSessionTimer.cancel();
            mSessionTimer = null;
        }
    }
}
