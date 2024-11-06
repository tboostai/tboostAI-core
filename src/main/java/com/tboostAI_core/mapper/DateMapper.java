package com.tboostAI_core.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateMapper {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    /**
     * 将 ISO 8601 格式的日期字符串转换为 Date 对象
     * @param dateString 日期字符串，格式如 "2024-10-13T21:09:35.000Z"
     * @return Date 对象或 null
     */
    @Named("stringToDate")
    public Date stringToDate(String dateString) {
        return stringToDate(dateString, DEFAULT_FORMATTER);
    }

    /**
     * 将指定格式的日期字符串转换为 Date 对象
     * @param dateString 日期字符串
     * @param formatter 日期格式
     * @return Date 对象或 null
     */
    @Named("stringToDateWithFormatter")
    public Date stringToDate(String dateString, DateTimeFormatter formatter) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse date: " + dateString, e);
        }
    }

    /**
     * 将 ISO 8601 格式的日期字符串转换为 Timestamp 对象
     * @param isoDate ISO 8601 日期字符串，格式如 "2024-10-13T21:09:35.000Z"
     * @return Timestamp 对象
     */
    @Named("isoToTimestamp")
    public static Timestamp parseIsoToTimestamp(String isoDate) {
        return parseToTimestamp(isoDate, DEFAULT_FORMATTER);
    }

    /**
     * 将指定格式的日期字符串转换为 Timestamp 对象
     * @param dateString 日期字符串
     * @param formatter 日期格式
     * @return Timestamp 对象
     */
    @Named("customToTimestamp")
    public static Timestamp parseToTimestamp(String dateString, DateTimeFormatter formatter) {
        if (dateString == null || dateString.isEmpty()) {
            throw new IllegalArgumentException("The date string cannot be null or empty.");
        }
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
            return Timestamp.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse date: " + dateString, e);
        }
    }

    /**
     * 将 LocalDateTime 转换为 Timestamp 对象
     * @param localDateTime LocalDateTime 对象
     * @return Timestamp 对象
     */
    @Named("localDateTimeToTimestamp")
    public static Timestamp fromLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }
}