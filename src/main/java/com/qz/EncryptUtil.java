package com.qz;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static final String ALGORITHM = "SHA-256";

    public static String encrypt(String source) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null != md) {
            md.update(source.getBytes());
            return getDigestStr(md.digest());
        }
        return null;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr;
        StringBuilder stb = new StringBuilder();
        for (byte origByte : origBytes) {
            tempStr = Integer.toHexString(origByte & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);
        }
        return stb.toString();
    }
}
