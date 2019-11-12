package org.zyf.cloud.common.utils;

import java.nio.ByteOrder;

/**
 * 描述：公用ByteUtils
 *
 * @author yanfengzhang
 * @date 2019-11-12 13:55
 */
public class ByteUtils {
    public static byte[] int2bytes(int namevalue, int size, ByteOrder order) {
        byte[] intbyte = new byte[size];
        if (ByteOrder.BIG_ENDIAN == order) {
            for (int i = intbyte.length - 1; i > -1; i--) {
                intbyte[i] = Integer.valueOf(namevalue & 0xFF).byteValue();
                namevalue >>= 8;
            }
        } else if (ByteOrder.LITTLE_ENDIAN == order) {
            for (int i = 0; i < intbyte.length; i++) {
                intbyte[i] = Integer.valueOf(namevalue & 0xFF).byteValue();
                namevalue >>= 8;
            }
        }
        return intbyte;
    }

    public static byte[] String2bytes(String namevalue, int size) {
        String newStr = namevalue == null ? "" : namevalue;
        if (getStringLength(newStr) > size) {
            newStr = getString(newStr, 0, size);
        }
        while (getStringLength(newStr) < size) {
            newStr = newStr + "";
        }
        return newStr.getBytes();
    }

    public static String getString(String str, int start, int end) {
        if ((str == null) || (getStringLength(str) <= start)) {
            return "";
        }
        if (getStringLength(str) < end) {
            end = getStringLength(str);
        }
        return new String(str.getBytes(), start, end - start).trim();
    }

    public static int getStringLength(String str) {
        if (str == null) {
            return 0;
        }
        byte[] bytes = str.getBytes();
        return bytes.length;
    }

    public static byte[] getbyte(byte[] packetbody, int start, int length) {
        byte[] b = new byte[length];
        System.arraycopy(packetbody, start, b, 0, length);
        return b;
    }

    public static String byte2String(byte[] b) {
        int size = b.length;
        byte[] temp = new byte[size];
        for (int i = 0; i < size; i++) {
            if (b[i] == 0) {
                break;
            }
            temp[i] = b[i];
        }
        return new String(temp).trim();
    }

    public static int bytes2int(byte[] b, ByteOrder order) {
        int result = 0;
        if (order == ByteOrder.BIG_ENDIAN) {
            for (int i = 0; i < b.length; i++) {
                result <<= 8;
                result += (Byte.valueOf(b[i]).intValue() & 0xFF);
            }
        } else if (order == ByteOrder.LITTLE_ENDIAN) {
            for (int i = b.length - 1; i >= 0; i--) {
                result <<= 8;
                result += (Byte.valueOf(b[i]).intValue() & 0xFF);
            }
        }
        return result;
    }
}
