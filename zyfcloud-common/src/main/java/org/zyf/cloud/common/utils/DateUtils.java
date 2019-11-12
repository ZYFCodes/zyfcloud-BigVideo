package org.zyf.cloud.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

/**
 * 描述：封装DateUtils类
 *
 * @author yanfengzhang
 * @date 2019-11-12 14:05
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    private Logger log = LogManager.getLogger(this.getClass());
    private static boolean isSupportMultiTimezone = true;
    private static final String DEFAULT_DATE_FORMATTER = "yyyy.MM.dd HH:mm:ss";

    public static Date getCurrentUtcTime() {
        Calendar cal = getInstance();
        int zoneOffset = cal.get(ZONE_OFFSET);
        int dstOffset = cal.get(DST_OFFSET);

        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTime();
    }

    public static boolean isExpiredForUTC(String cmpDateStr) throws ParseException {
        Date currentTime = getCurrentUtcTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMATTER);
        Date cmpDate = formatter.parse(cmpDateStr);
        return currentTime.after(cmpDate);
    }

    public static boolean isExpired(String cmpDateStr) throws ParseException {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMATTER);
        Date cmpDate = formatter.parse(cmpDateStr);
        return currentTime.after(cmpDate);
    }

    private String convertTime(String time, String format, int zoneOffset, boolean toUTC) {
        if (ZyfStringUtils.isBlank(time)) {
            return time;
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);

            Date utcDate = formatter.parse(time);

            Calendar cal2 = getInstance();
            if (zoneOffset == -999) {
                int dstOffset = cal2.get(DST_OFFSET);
                zoneOffset = cal2.get(ZONE_OFFSET) + dstOffset;
            } else {
                zoneOffset *= 60000;
            }
            int mount = 0;
            if (toUTC) {
                mount = -zoneOffset;
            } else {
                mount = zoneOffset;
            }
            cal2.setTime(utcDate);
            cal2.add(MILLISECOND, mount);

            return formatter.format(cal2.getTime());
        } catch (ParseException e) {
            log.error("DateUtils convertTime parse time error!", e);
        }
        return time;
    }

    public String toLocalTime(String utctime, String format, int zoneOffset) {
        return convertTime(utctime, format, zoneOffset, false);
    }

    public String toUTCTime(String localtime, String format, int zoneOffset) {
        return convertTime(localtime, format, zoneOffset, true);
    }

    public static Date getLocalDate() {
        if (isSupportMultiTimezone) {
            return getCurrentUtcTime();
        }
        return new Date();
    }

    public static long getLocalTime() {
        if (isSupportMultiTimezone) {
            return getCurrentUtcTime().getTime();
        }
        return System.currentTimeMillis();
    }

    public String toLocalTime(String utctime) {
        if (isSupportMultiTimezone) {
            return toLocalTime(utctime, DEFAULT_DATE_FORMATTER, -999);
        }
        return utctime;
    }

    public String toLocalTime(String utctime, String format) {
        if (isSupportMultiTimezone) {
            return toLocalTime(utctime, format, -999);
        }
        return utctime;
    }

    public String toUTCTime(String localtime) {
        if (isSupportMultiTimezone) {
            return toUTCTime(localtime, DEFAULT_DATE_FORMATTER, -999);
        }
        return localtime;
    }

    public Date offsetOfCurrentTime(int type, int offset) {
        GregorianCalendar gc = (GregorianCalendar) getInstance();
        if (isSupportMultiTimezone) {
            Date date = getCurrentUtcTime();
            gc.setTime(date);
        } else {
            Date date = new Date();
            gc.setTime(date);
        }
        if (type == 0) {
            gc.add(MINUTE, offset);
        } else if (type == 1) {
            gc.add(HOUR_OF_DAY, offset);
        }
        return gc.getTime();
    }

    public Date offsetOfCurrentTime(Date date, int type, int offset) {
        GregorianCalendar gc = (GregorianCalendar) getInstance();
        gc.setTime(date);
        if (type == 0) {
            gc.add(MINUTE, offset);
        } else if (type == 1) {
            gc.add(HOUR_OF_DAY, offset);
        }
        return gc.getTime();
    }

    public boolean compareTime(String cmpDateStr) {
        Date currentTime;
        if (isSupportMultiTimezone) {
            currentTime = getCurrentUtcTime();
        } else {
            currentTime = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMATTER);
        Date cmpDate = null;
        try {
            cmpDate = formatter.parse(cmpDateStr);
        } catch (ParseException e) {
            log.error("DateUtils compareTime parse time error!", e);
            return true;
        }
        return currentTime.after(cmpDate);
    }

    public String date2String(Date date, String format) {
        if (date == null) {
            if (isSupportMultiTimezone) {
                date = getCurrentUtcTime();
            } else {
                date = new Date();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public boolean dateBefore(String srcdatestr, String desdatestr, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date srcdate = null;
        Date desdate = null;
        try {
            srcdate = format.parse(srcdatestr);
            desdate = format.parse(desdatestr);
        } catch (ParseException ex) {
            log.error("DateUtils dateBefore --- date format error, pattern should be " + pattern, ex);
            return false;
        }
        if (srcdate.equals(desdate)) {
            return true;
        }
        return srcdate.before(desdate);
    }
}
