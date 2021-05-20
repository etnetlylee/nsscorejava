package com.etnet.coresdk.ums.utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtils {
    private static final String TAG = "AESUtils";
    private static final String AES_ENCRYPTION_SCHEME = "AES/ECB/PKCS7Padding";
    private static final String ETNET_KEY = "1234567890123456";
    private static final String ENCODING = "UTF-8";
    private static final String SPEC = "AES";
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static final Logger logger = Logger.getLogger(AESUtils.class);

    private static String ENCRYPTION_KEY = "";

    public static void main(String args[]){
        // TODO: init config, log4j, API
        BasicConfigurator.configure();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        AESUtils aesUtils = new AESUtils();
        aesUtils.init("757ccd0cdc5c90eadbeeecf638dd0000050187a0cde5a9872cbab091ab73e553");
        String toBeEncryptKey = "ETP0202103KIMENG";
        System.out.println("toBeEncryptKey : [" + toBeEncryptKey + "]");
//		// decrypt key
        String tmp_key = aesUtils.encryptKey(toBeEncryptKey);
        System.out.println("encrypted key : [" + tmp_key + "]");
        aesUtils.init(tmp_key);
        System.out.println("decrypt key:[" + ENCRYPTION_KEY + "]");
        String m = "ETNet";
        System.out.println("Encryption key : [" + ENCRYPTION_KEY +"]");
    }

    public void init(String aesKey) {
        try {
            ENCRYPTION_KEY = decryptKey(aesKey);
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

    public boolean load(String aesKey) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            if (StringUtils.isBlank(aesKey)) {
                return false;
            } else {
                try {
                    init(aesKey);
                } catch (Exception e) {
                    logger.error(e, e);
                }
                return true;

            }
        } catch (Exception e) {
            logger.error(e, e);
            return false;
        }
    }

    public String encryptKey(String unencryptedString) {
        String encryptedString = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(ETNET_KEY.getBytes(CommonFunction.ENCODING), SPEC);
            Cipher cipher = Cipher.getInstance(AES_ENCRYPTION_SCHEME, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encryptedText = cipher.doFinal(unencryptedString.getBytes(CommonFunction.ENCODING));
            encryptedString = bytes2Hex(encryptedText);
        } catch (Exception e) {
            logger.error(e, e);
            return null;
        }
        return encryptedString;
    }

    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            logger.info("encryptedKey: " + ENCRYPTION_KEY);
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(CommonFunction.ENCODING), SPEC);
            Cipher cipher = Cipher.getInstance(AES_ENCRYPTION_SCHEME, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encryptedText = cipher.doFinal(unencryptedString.getBytes(CommonFunction.ENCODING));
            encryptedString = bytes2Hex(encryptedText);
            logger.info("exncyptedString:" + encryptedString );
        } catch (Exception e) {
            logger.error(e, e);
            return null;
        }
        return encryptedString;
    }

    public String decryptKey(String encryptedString) {
        String decryptedText = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(ETNET_KEY.getBytes(ENCODING), SPEC);
            Cipher cipher = Cipher.getInstance(AES_ENCRYPTION_SCHEME, "BC");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            logger.info(encryptedString.toCharArray().toString());
            byte[] encryptedText = hex2Bytes(encryptedString);
//            logger.info(encryptedText.toString());
            byte[] original = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(original);
            System.out.println(decryptedText);
            return decryptedText;
        } catch (Exception e) {
            logger.error(e, e);
            return null;
        }

    }

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPTION_KEY.getBytes(ENCODING), SPEC);
            Cipher cipher = Cipher.getInstance(AES_ENCRYPTION_SCHEME, "BC");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encryptedText = hex2Bytes(encryptedString);
            byte[] original = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(original);
            return decryptedText;
        } catch (Exception e) {
            logger.error(e, e);
            return null;
        }

    }

    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    public static String bytes2Hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hex2Bytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
