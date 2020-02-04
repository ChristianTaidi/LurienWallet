package com.christian.lurienwallet.demo.helpers;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import static android.provider.Telephony.Carriers.PASSWORD;

public class CipherHelper {

    private static String SALT = "some-hash-salt";
    private static String IV = "IV_VALUE_16_BYTE";


    public static String encrypt(String pwd){
        try{
            Cipher c = getCipher(Cipher.ENCRYPT_MODE);
            byte[] encryptedVal =c.doFinal(getBytes(pwd));
            String s = Base64.encodeToString(encryptedVal,Base64.DEFAULT);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeAndDecryot(String encrypted) throws Exception {
        byte[] decodedValue = Base64.decode(getBytes(encrypted),Base64.DEFAULT);
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue);
    }

    private static Cipher getCipher(int mode) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = getBytes(IV);
        c.init(mode, generateKey(), new IvParameterSpec(iv));
        return c;
    }

    private static Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = PASSWORD.toCharArray();
        byte[] salt = getBytes(SALT);

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }

    private static byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }
}
