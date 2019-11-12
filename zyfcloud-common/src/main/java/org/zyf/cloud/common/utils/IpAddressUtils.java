package org.zyf.cloud.common.utils;

import org.apache.commons.lang3.text.CompositeFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyf.cloud.common.constant.fun.CommonConstant;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;

/**
 * 描述：公用IpAddress
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:34
 */
public class IpAddressUtils {
    private static Logger log = LogManager.getLogger();


    public static int resolveIp(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            if ((address instanceof Inet6Address)) {
                return CommonConstant.IPConstants.IPV6;
            }
            if ((address instanceof Inet4Address)) {
                return CommonConstant.IPConstants.IPV4;
            }
            return CommonConstant.IPConstants.OTHER;
        } catch (UnknownHostException e) {
            log.error("getByName error for input ipAddress: " + ipAddress, e);
        }
        return CommonConstant.IPConstants.OTHER;
    }

    public static String getIpForUrl(String ipAddress) {
        int ipType = resolveIp(ipAddress);
        if (CommonConstant.IPConstants.IPV4 == ipType) {
            return ipAddress;
        }
        if (CommonConstant.IPConstants.IPV6 == ipType) {
            return "[" + ipAddress + "]";
        }
        return ipAddress;
    }

    public static String formatIp(String ip) {
        int temp = resolveIp(ip);
        if (CommonConstant.IPConstants.IPV4 == temp) {
            return formatIpv4Ip(ip);
        }
        if (CommonConstant.IPConstants.IPV6 == temp) {
            return formatIpv6Ip(ip);
        }
        return ip;
    }

    private static String formatIpv4Ip(String input) {
        try {
            MessageFormat parser = new MessageFormat("{0,number,integer}.{1,number,integer}.{2,number,integer}.{3,number,integer}");
            MessageFormat formatter = new MessageFormat("{0,number,000}.{1,number,000}.{2,number,000}.{3,number,000}");
            return new CompositeFormat(parser, formatter).reformat(input);
        } catch (ParseException e) {
            log.error("reformaterror for input string: " + input, e);
        }
        return input;
    }

    private static String formatIpv6Ip(String ipv6Ip) {
        StringBuilder builder = new StringBuilder();
        if (ipv6Ip.indexOf(".") > 0) {
            int temp = ipv6Ip.lastIndexOf(":");
            String subStringPrefix = ipv6Ip.substring(0, temp + 1);
            builder.append(subStringPrefix);
            String subStringSuffix = ipv6Ip.substring(temp + 1);
            String[] ipsSuffix = subStringSuffix.split("\\.", -1);
            for (int i = 0; i < 4; i += 2) {
                String temp1 = ipsSuffix[i];
                temp1 = formatNum(Integer.toHexString(Integer.parseInt(temp1)), 2);
                String temp2 = ipsSuffix[(i + 1)];
                temp2 = formatNum(Integer.toHexString(Integer.parseInt(temp2)), 2);
                builder.append(temp1).append(temp2);
                if (i == 0) {
                    builder.append(":");
                }
            }
        } else {
            builder.append(ipv6Ip);
        }
        return formatIpv6IpWithoutDot(builder.toString());
    }

    private static String formatIpv6IpWithoutDot(String ipv6IpWithoutDot) {
        StringBuilder builder = new StringBuilder();
        String[] tempIps = ipv6IpWithoutDot.split(":", -1);
        boolean isFirst = true;
        for (int i = 0; i < tempIps.length; i++) {
            String temp = tempIps[i];
            if (("".equals(temp)) && (isFirst)) {
                getMiddle0Address(builder, 8 - (tempIps.length - 1));
                isFirst = false;
            } else {
                builder.append(formatNum(temp, 4));
                if (i != tempIps.length - 1) {
                    builder.append(":");
                }
            }
        }
        return builder.toString();
    }

    private static void getMiddle0Address(StringBuilder builder, int number) {
        for (int i = 0; i < number; i++) {
            builder.append("0000").append(":");
        }
    }

    private static String formatNum(String number, int length) {
        StringBuilder builder = new StringBuilder();
        int numLen = number.length();
        for (int i = 0; i < length - numLen; i++) {
            builder.append("0");
        }
        builder.append(number);
        return builder.toString();
    }

    public static String ipSimpleFormat(String ip) {
        if (ZyfStringUtils.isEmpty(ip)) {
            return "";
        }
        StringBuffer trueIP = new StringBuffer();
        try {
            String[] ips = ip.split("\\.");
            DecimalFormat df = new DecimalFormat("000");
            for (int i = 0; i < ips.length - 1; i++) {
                int p = Integer.parseInt(ips[i].trim());
                trueIP.append(df.format(p)).append(".");
            }
            trueIP.append(df.format(Integer.parseInt(ips[(ips.length - 1)])));
        } catch (Exception e) {
            log.error("Format ip error for input string: " + ip, e);
            return "";
        }
        return trueIP.toString();
    }
}
