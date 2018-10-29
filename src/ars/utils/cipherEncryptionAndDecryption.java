package ars.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class cipherEncryptionAndDecryption {
    public static String encrypt(String strClearText , String strKey){
        String strData="";
            try {
                SecretKeySpec sKeySpec = new SecretKeySpec(strKey.getBytes(),"Blowfish");
                Cipher cipher = Cipher.getInstance("Blowfish");
                cipher.init(Cipher.ENCRYPT_MODE,sKeySpec);
                byte[] encrypted = cipher.doFinal(strClearText.getBytes());
                strData = new String(encrypted);
            }catch (Exception e){
                e.getMessage();
            }
            return strData;
    }
    public static String decrypt(String strEncrypted , String strKey){
        String strData="";
        try {
            SecretKeySpec sKeySpec = new SecretKeySpec(strKey.getBytes(),"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE,sKeySpec);
            byte[] decrypted = cipher.doFinal(strEncrypted.getBytes());
            strData = new String(decrypted);
        }catch (Exception e){
            e.getMessage();
        }
        return strData;
    }
}
