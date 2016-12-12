/*
 * Copyright (C) 2013 Credoo Inc. All rights reserved.
 */
package com.hmc.githubupload;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Static functions to simplifiy common {@link MessageDigest}
 * tasks. This class is thread safe.
 * 
 */
public final class Md5Utils {

    /** default constructor */
    private Md5Utils() {
    }

    /**
     * generate md5 signature of the string.
     *
     * @param s
     *            the string data.
     * @return md5 signature
     */
    public static String toMD5(String s) {
        if (s != null) {
            try {
                byte[] bs = s.getBytes("UTF-8");
                return encrypt(bs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * generate md5 signature of the string.
     *
     * @param s
     *            the string data.
     * @return md5 signature
     */
    @SuppressLint("NewApi")
    public static String toMD5UCS2(String s) {
        //LogUtil.d("handlePcPwd. toMD5UCS2. s length = " + s.length() + ", s = " + s);
        if (s != null) {
            try {
                byte[] bs = s.getBytes("UTF-8");
                // StringBuilder builder = new StringBuilder();
                // for (int i = 0; i < bs.length; i++) {
                // builder.append(bs[i] + " ");
                // }
                // LogUtil.d("handlePcPwd. toMD5UCS2. gbk length = " + bs.length
                // + ", gbk = " + builder.toString());
                bs = new String(bs, "UTF-8").getBytes("UTF-8");
                // builder.delete(0, builder.length());
                // for (int i = 0; i < bs.length; i++) {
                // builder.append(bs[i] + " ");
                // }
                // LogUtil.d("handlePcPwd. toMD5UCS2. ucs length = " + bs.length
                // + ", ucs = " + builder.toString());
                // if (s.length() * 2 < bs.length) {
                // bs = Arrays.copyOfRange(bs, bs.length - s.length() * 2,
                // bs.length);
                // }
                // builder.delete(0, builder.length());
                // for (int i = 0; i < bs.length; i++) {
                // builder.append(bs[i] + " ");
                // }
                // LogUtil.d("handlePcPwd. toMD5UCS2. ucs2 length = " +
                // bs.length + ", ucs2 = " + builder.toString());
                return encrypt(bs);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * generate md5 signature of the string.
     *
     * @param s
     *            the string data.
     * @return md5 signature
     */
    public static String toMD5NoEncode(String s) {
        if (s != null) {
            byte[] bs = s.getBytes();
            return encrypt(bs);
        }
        return null;
    }

    /**
     * generate md5 signature of the byte[].
     *
     * @param obj
     *            byte data
     * @return md5 signature
     */
    private static synchronized String encrypt(byte[] obj) {
        // StringBuilder builder = new StringBuilder();
        // for (int i = 0; i < obj.length; i++) {
        // builder.append(obj[i] + " ");
        // }
        // LogUtil.d("handlePcPwd. encrypt. bit length = " + obj.length +
        // ", bits = " + builder.toString());
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(obj);
            byte[] bs = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bs.length; i++) {
                sb.append(Integer.toHexString((0x000000ff & bs[i]) | 0xffffff00).substring(6));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a MD5 MessageDigest.
     *
     * @return An MD5 digest instance.
     */
    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    private static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 16 element
     * <code>byte[]</code>.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest
     */
    private static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(byte[] data) { // NO_UCD (unused code)
        return bytesToHexString(md5(data), null);
    }

    /**
     * Calculates the MD5 digest and returns the value as a 32 character hex
     * string.
     *
     * @param data
     *            Data to digest
     * @return MD5 digest as a hex string
     */
    public static String md5Hex(String data) {
        return bytesToHexString(md5(data), null);
    }

    /** */
    private static char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * change byte[] to hex string.
     *
     * @param bytes
     *            the bytes data
     * @param delimiter
     *            the delimiter
     * @return the hex string
     */
    private static String bytesToHexString(final byte[] bytes, Character delimiter) {
        StringBuffer hex = new StringBuffer(bytes.length * (delimiter == null ? 2 : 3));
        int nibble1, nibble2;
        for (int i = 0; i < bytes.length; i++) {
            nibble1 = (bytes[i] >>> 4) & 0xf;
            nibble2 = bytes[i] & 0xf;
            if (i > 0 && delimiter != null) {
                hex.append(delimiter.charValue());
            }
            hex.append(hexChars[nibble1]);
            hex.append(hexChars[nibble2]);
        }
        return hex.toString();
    }
}