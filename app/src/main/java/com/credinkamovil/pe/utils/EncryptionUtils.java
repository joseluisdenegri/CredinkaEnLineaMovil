package com.credinkamovil.pe.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtils {
    private static String IV = "2_B4NC4_16_M0V1L";
    private static String PASSWORD = "S@3gur1d@d1nf@rm@c10n";
    private static String SALT = "CR3D1K9_8M0V1L";
    public String BM_EncryptValue(String raw) {
        try {
            Cipher c = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedVal = c.doFinal(getBytes(raw));
            String s = getString(Base64.encodeBase64(encryptedVal));
            return s;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public String BM_DecryptValue(String encrypted) throws Exception {
        byte[] decodedValue = Base64.decodeBase64(getBytes(encrypted));
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private String getString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }

    private byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    private Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = getBytes(IV);
        c.init(mode, generateKey(), new IvParameterSpec(iv));
        return c;
    }

    private Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = PASSWORD.toCharArray();
        byte[] salt = getBytes(SALT);

        KeySpec spec = new PBEKeySpec(password, salt, 1024, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }

    public String BM_RJD_Encriptar(String strEncriptar){
        String returnValue = "";
        try{
            Cipher oRijndael;
            int keySize = 32;
            int ivSize = 16;
            byte[] key = PASSWORD.getBytes(StandardCharsets.UTF_8);
            byte[] iv = "Cr3d1Nk@".getBytes(StandardCharsets.UTF_8);
            key = Arrays.copyOf(key, keySize);
            iv = Arrays.copyOf(iv, ivSize);
            oRijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            oRijndael.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] plainMessageBytes = strEncriptar.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = oRijndael.doFinal(plainMessageBytes);
            returnValue = getStringKey(Base64.encodeBase64(encryptedBytes));
        }catch (Exception ex){

        }
        return returnValue;
    }
    private String getStringKey(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, "UTF-8");
    }
}
