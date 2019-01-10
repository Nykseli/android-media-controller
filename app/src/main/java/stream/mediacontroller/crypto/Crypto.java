package stream.mediacontroller.crypto;

import android.util.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import stream.mediacontroller.util.PreferenceStorage;

public class Crypto {


    public static String decryptCFB(byte[] crypted){

        byte[] decrypted = {};

        byte[] key = PreferenceStorage.SECURITY_CURRENT_ENCRYPTION_KEY.getBytes();
        byte[] decodedBytes = Base64.decode(crypted, Base64.DEFAULT);
        byte[] ivBytes = Arrays.copyOf(decodedBytes, 16);
        byte[] messageBytes = Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length);

        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            decrypted = cipher.doFinal(messageBytes);
        }catch (Exception err){
            err.printStackTrace();
        }

        return new String(decrypted);
    }

    private static String addPadding(String plainText){
        int paddingAmount = 16 - (plainText.length() % 16);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<paddingAmount; i++){
            builder.append("0");
        }
        plainText += builder.toString();
        return plainText;
    }

    public static byte[] cryptCFB(String plainText){
        plainText = addPadding(plainText);

        byte[] key = PreferenceStorage.SECURITY_CURRENT_ENCRYPTION_KEY.getBytes();

        byte[] crypted = {};
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");

        try{
            Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] cryptMessage = cipher.doFinal(plainText.getBytes());
            byte[] messageBytes = new byte[iv.length + cryptMessage.length];
            System.arraycopy(iv, 0, messageBytes, 0, iv.length);
            System.arraycopy(cryptMessage, 0, messageBytes, iv.length, cryptMessage.length);
            crypted = Base64.encode(messageBytes, Base64.DEFAULT );
        }catch (Exception err){
            err.printStackTrace();
        }

        return  crypted;
    }
}
