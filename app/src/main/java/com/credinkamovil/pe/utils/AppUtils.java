package com.credinkamovil.pe.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.credinkamovil.pe.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class AppUtils {
    public static boolean isNumeric(String cadena) {
        try {
            if (cadena.matches("[0-9]*")) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static String getFechaHora() {
        String sFehcaHora = "";
        try {
            Date mDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS", new Locale("es", "ES"));
            sFehcaHora = simpleDateFormat.format(mDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sFehcaHora;
    }

    public static String getHora() {
        String sHora = "";
        try {
            Date mDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", new Locale("es", "ES"));
            sHora = simpleDateFormat.format(mDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sHora;
    }

    public static String getMacLocal() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    public static String getIpLocalAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("SocketException", ex.toString());
        }
        return "";
    }

    public static String formatStringDecimals(Double nValue) {
        DecimalFormatSymbols mDecimalFormatSymbols = new DecimalFormatSymbols();
        mDecimalFormatSymbols.setDecimalSeparator('.');
        mDecimalFormatSymbols.setGroupingSeparator(',');
        DecimalFormat mDecimalFormat = new DecimalFormat("###,##0.00", mDecimalFormatSymbols);
        return mDecimalFormat.format(nValue);
    }

    public static String downloadCronogramaPdf(InputStream mInputStream, Context context) {
        try {
            File pdfDirPath = new File(context.getFilesDir(), "pdfs");
            pdfDirPath.mkdirs();
            File myPath = new File(pdfDirPath, "CronogramaCredito.pdf");
            Uri contentUri = FileProvider.getUriForFile(context, context.getString(R.string.fileprovider), myPath);
            FileOutputStream fout = new FileOutputStream(myPath);
            try {
                byte[] data = new byte[4096];
                while (true) {
                    int count = mInputStream.read(data);
                    if (count != -1) {
                        fout.write(data, 0, count);
                    } else {
                        fout.flush();
                        fout.close();
                        mInputStream.close();
                        return contentUri.toString();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String downloadEstadoCuentaPdf(InputStream mInputStream, Context context) {
        try {
            String sNameFile = "";
            Date mDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss", new Locale("es", "ES"));
            String sFechaHora = simpleDateFormat.format(mDate);
            sNameFile = "EE_AHORRO_" + sFechaHora + ".pdf";
            File pdfDirPath = new File(context.getFilesDir(), "pdfs");
            pdfDirPath.mkdirs();
            File myPath = new File(pdfDirPath, sNameFile);
            Uri contentUri = FileProvider.getUriForFile(context, context.getString(R.string.fileprovider), myPath);
            FileOutputStream fout = new FileOutputStream(myPath);
            try {
                byte[] data = new byte[4096];
                while (true) {
                    int count = mInputStream.read(data);
                    if (count != -1) {
                        fout.write(data, 0, count);
                    } else {
                        fout.flush();
                        fout.close();
                        mInputStream.close();
                        return contentUri.toString();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
