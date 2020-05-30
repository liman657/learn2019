package com.learn.dateapi;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;

/**
 * autor:liman
 * createtime:2020/5/16
 * comment:java8 的localDate实例
 */
@Slf4j
public class LocalDateDemo {

    public static void main(String[] args) throws ParseException, InterruptedException {
//        Date date = new Date(116,2,18);
//        log.info("old date :{}",date);
//
//        //多线程下会有问题
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Date parseDate = sdf.parse("20180909");
//        log.info("simpleDate format :{}",parseDate);
        testLoaclDate();
        testLocalDateAndTime();
        testInstant();
    }

    private static void testLoaclDate(){
        LocalDate localDate = LocalDate.of(2016,12,12);
        log.info("localDate:{}",localDate);
        log.info("year of localDate:{}",localDate.getYear());
        log.info("month of localDate:{}",localDate.getMonth());
        log.info("month value of localDate:{}",localDate.getMonthValue());
        log.info("day of year of localDate:{}",localDate.getDayOfYear());
        log.info("day of month of localDate:{}",localDate.getDayOfMonth());
        log.info("day of week of localDate:{}",localDate.getDayOfWeek());
        log.info("now :{}",LocalDate.now());
    }

    private static void testLocalTime(){
        LocalTime time = LocalTime.now();
        log.info("get now hour:{}",time.getHour());
    }

    private static void testLocalDateAndTime(){
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();

        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        log.info("local date time : {}",localDateTime);
    }

    private static void testInstant() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(1000L);
        Instant end = Instant.now();
        Duration duration = Duration.between(start,end);
        log.info("总共耗时:{}",duration.toMillis());
    }

}
