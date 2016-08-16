package com.google.android.vending.licensing;

import com.google.android.vending.licensing.util.Base64;
import com.google.android.vending.licensing.util.Base64DecoderException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESObfuscator implements Obfuscator {
    private static final byte[] IV;
    private Cipher mDecryptor;
    private Cipher mEncryptor;

    static {
        IV = new byte[]{(byte) 16, (byte) 74, (byte) 71, (byte) -80, (byte) 32, (byte) 101, (byte) -47, (byte) 72, (byte) 117, (byte) -14, (byte) 0, (byte) -29, (byte) 70, (byte) 65, (byte) -12, (byte) 74};
    }

    public AESObfuscator(byte[] salt, String applicationId, String deviceId) {
        try {
            SecretKey secret = new SecretKeySpec(SecretKeyFactory.getInstance("PBEWITHSHAAND256BITAES-CBC-BC").generateSecret(new PBEKeySpec((applicationId + deviceId).toCharArray(), salt, 1024, 256)).getEncoded(), "AES");
            this.mEncryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.mEncryptor.init(1, secret, new IvParameterSpec(IV));
            this.mDecryptor = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.mDecryptor.init(2, secret, new IvParameterSpec(IV));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("Invalid environment", e);
        }
    }

    public String obfuscate(String original, String key) {
        if (original == null) {
            return null;
        }
        try {
            return Base64.encode(this.mEncryptor.doFinal(("com.android.vending.licensing.AESObfuscator-1|" + key + original).getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid environment", e);
        } catch (GeneralSecurityException e2) {
            throw new RuntimeException("Invalid environment", e2);
        }
    }

    public String unobfuscate(String obfuscated, String key) throws ValidationException {
        if (obfuscated == null) {
            return null;
        }
        try {
            String result = new String(this.mDecryptor.doFinal(Base64.decode(obfuscated)), "UTF-8");
            if (result.indexOf("com.android.vending.licensing.AESObfuscator-1|" + key) == 0) {
                return result.substring("com.android.vending.licensing.AESObfuscator-1|".length() + key.length(), result.length());
            }
            throw new ValidationException("Header not found (invalid data or key):" + obfuscated);
        } catch (Base64DecoderException e) {
            throw new ValidationException(e.getMessage() + ":" + obfuscated);
        } catch (IllegalBlockSizeException e2) {
            throw new ValidationException(e2.getMessage() + ":" + obfuscated);
        } catch (BadPaddingException e3) {
            throw new ValidationException(e3.getMessage() + ":" + obfuscated);
        } catch (UnsupportedEncodingException e4) {
            throw new RuntimeException("Invalid environment", e4);
        }
    }
}
