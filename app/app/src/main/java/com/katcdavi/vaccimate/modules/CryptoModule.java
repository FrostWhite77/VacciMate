package com.katcdavi.vaccimate.modules;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CryptoModule
{
    public CryptoModule() {

    }

    public String encrypt(String data) {
        return data;
    }

    public String decrypt(String data) {
        return data;
    }

    @SuppressLint("NewApi")
    public static String hash(String data, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String pinToSecret(String nationalId, String username, Date birthDate, String pin) {
        String salt = CryptoModule.hash(nationalId + username + (new SimpleDateFormat("ddMMyyyy").format(birthDate)), "");
        String secret = CryptoModule.hash(pin, salt);
        return secret;
    }

    public static String pinToSecret(UserDataModule userData, String pin) {
        return CryptoModule.pinToSecret(userData.getNationalId(), userData.getUsername(), userData.getBdate(), pin);
    }
}
