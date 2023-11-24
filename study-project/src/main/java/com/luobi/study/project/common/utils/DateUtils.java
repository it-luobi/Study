package com.luobi.study.project.common.utils;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_WITH_MS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String TIME_START_OF_DAY = " 00:00:00";
    public static final String TIME_END_OF_DAY = " 23:59:59";
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YM = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YMD = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final DateTimeFormatter DATE_TIME_FORMATTER_YMDT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean isValid(String date, String format) {
        if (date == null || format == null) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String format(String format) {
        if (format == null) {
            throw new IllegalArgumentException();
        }
        return format(new Date(), format);
    }

    public static String format(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    public static String format(Date date, String format) {
        if (date == null || format == null) {
            throw new IllegalArgumentException();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        return simpleDateFormat.format(date);
    }

    public static Date today() {
        return new Date();
    }

    public static Date addMonth(Date date, int difference) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, difference);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int difference) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, difference);
        return calendar.getTime();
    }

    public static String format(String dateString, String fromFormat, String toFormat) {
        if (StringUtils.isEmpty(dateString)) {
            return dateString;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromFormat, Locale.KOREA);
        try {
            Date date = simpleDateFormat.parse(dateString);
            return format(date, toFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parse(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    public static Date getStartOfDay(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }

    public static Date getBeginningOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getEndOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        c.add(Calendar.MILLISECOND, -1);
        return c.getTime();
    }

    public static List<YearMonth> getMonthsBetweenTwoMonths(@NonNull String startYearMonthStr,
                                                            @NonNull String endYearMonthStr, @NonNull String pattern) {

        YearMonth startYearMonth = parseYm(startYearMonthStr, pattern);
        YearMonth endYearMonth = parseYm(endYearMonthStr, pattern);
        return getMonthsBetweenTwoMonths(startYearMonth, endYearMonth);

    }

    public static List<YearMonth> getMonthsBetweenTwoMonths(@NonNull YearMonth startYearMonth,
                                                            @NonNull YearMonth endYearMonth) {

        if (startYearMonth.isAfter(endYearMonth)) {
            throw new IllegalArgumentException("The startYearMonthStr cannot be after the endYearMonthStr.");
        }
        List<YearMonth> months = new ArrayList<>();

        for (YearMonth currentMonth = startYearMonth; !currentMonth.isAfter(
                endYearMonth); currentMonth = currentMonth.plusMonths(1)) {
            months.add(currentMonth);
        }

        return months;

    }

    public static YearMonth parseYm(@NonNull String ym, @NonNull String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return YearMonth.parse(ym, formatter);
    }

    public static YearMonth parseYm(@NonNull String ym) {
        return YearMonth.parse(ym, DATE_TIME_FORMATTER_YM);
    }

    public static boolean firstDayOfThisMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return StringUtils.equals("01", formatter.format(LocalDate.now()));
    }

    public static String removeShortHorizontalLine(String date) {
        if (StringUtils.isNotEmpty(date)) {
            date = date.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        }
        return date;
    }

    public static String addDateSymbol(String date) {
        if (StringUtils.isNotEmpty(date)) {
            date = date.trim();
            if (StringUtils.isNotEmpty(date)) {
                StringBuilder stringBuilder = new StringBuilder(date);
                stringBuilder.trimToSize();
                if (0 > date.indexOf("-")) {
                    stringBuilder.insert(4, "-").insert(7, "-");
                }
                if (stringBuilder.length() > 10 && 0 > stringBuilder.indexOf(":")) {
                    stringBuilder.insert(10, " ").insert(13, ":").insert(16, ":");
                }
                date = stringBuilder.toString();
            }
        }
        return date;
    }

    public static String convertYmToBeginningOfMonthYmd(String ym) {
        return parseYm(ym).atDay(1).format(DATE_TIME_FORMATTER_YMD);
    }

    public static String convertYmToEndOfMonthYmd(String ym) {
        return parseYm(ym).atEndOfMonth().format(DATE_TIME_FORMATTER_YMD);
    }

}
