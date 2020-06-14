package com.learn.timeapi;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Date;

/**
 * autor:liman
 * createtime:2020/5/30
 * comment:LocalDate和LocalTime还有LocalDateAndTime的使用实例
 */
@Slf4j
public class LocalDateAndLocalTime {

    public static void main(String[] args) {
        testLocalDate();
        testLocalTime();
        testTrans2LocalDateOrLocalTime();
        testLocalDateTime();
        testDurationAndPeriod();
        testDecodeTimeFormat();
        testSelfDateFormat();
//        dateOriginal();
    }

    /**
     * 测试原来的操作
     */
    public static void dateOriginal(){
        Date date = new Date(120,2,18);
        log.info("old date : {}",date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        for(int i=0;i<30;i++){
            new Thread(()->{
                for(int x = 0;x<100;x++){
                    Date parseDate = null;
                    try{
                        parseDate = sdf.parse("20200819");
                    }catch (Exception e){
                        log.error("error:{}",e);
                    }
                    log.info("parseDate:{}",parseDate);
                }
            }).start();
        }
    }

    /**
     * 测试LocalDate
     */
    public static void testLocalDate(){
        LocalDate localDate = LocalDate.of(2020,5,29);
        int year  = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

//        int year = localDate.get(ChronoField.YEAR);
//        int month = localDate.get(ChronoField.MONTH_OF_YEAR);
//        int day = localDate.get(ChronoField.DAY_OF_MONTH);

        log.info("year:{},month:{},day:{}",year,month,day);
    }

    /**
     * 测试LocalTime
     */
    public static void testLocalTime(){
        LocalTime localTime = LocalTime.of(20,25,32);
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();

        log.info("hour:{},minute:{},second:{}",hour,minute,second);
    }

    public static void testLocalDateTime(){
        LocalDate localDateOne = LocalDate.of(2020,5,20);
        LocalTime localTime = LocalTime.of(10,10,10);
        //LocalDate通过atTime完成拼接
        LocalDateTime localDateTime = localDateOne.atTime(localTime);
        log.info("combine result : {}",localDateTime);
        //LocalTime通过atDate完成拼接
        LocalDate localDateTwo = LocalDate.of(2020,6,7);
        LocalDateTime localDateTimeTwo = localTime.atDate(localDateTwo);
        log.info("combine result two : {}",localDateTimeTwo);

        LocalDateTime localDateTimeSelf = LocalDateTime.of(2020,5,20,12,10,10);
        log.info("self localDateTime:{}",localDateTimeSelf);

        LocalDateTime localDateTimeNow = LocalDateTime.now();
        log.info("localDateTime now:{}",localDateTimeNow);
    }

    public static void testTrans2LocalDateOrLocalTime(){
        LocalDate localDate = LocalDate.parse("2020-09-09");
        LocalTime localTime = LocalTime.parse("12:20:20");
        log.info("transfer result : date={},time={}",localDate,localTime);
    }

    /**
     * duration和period
     */
    public static void testDurationAndPeriod(){
        LocalTime time1 = LocalTime.of(10,10,10);
        LocalTime time2 = LocalTime.of(12,12,12);
        Duration duration = Duration.between(time1,time2);

        log.info("duration:{}",duration.get(ChronoUnit.SECONDS));

        LocalDate date1 = LocalDate.of(2000,10,10);
        LocalDate date2 = LocalDate.of(2002,12,18);
        Period period = Period.between(date1,date2);
        log.info("period,year:{},month:{},day:{}",period.get(ChronoUnit.YEARS),period.get(ChronoUnit.MONTHS),period.get(ChronoUnit.DAYS));
    }

    /**
     * 打印输出和解析日期
     */
    public static void testDecodeTimeFormat(){
//        LocalDate localDate = LocalDate.of(2001,10,10);
//        String dateFormat01 = localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
//        String dateFormat02 = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
//        log.info("baseIsoDate:{},isoLocalDate:{}",dateFormat01,dateFormat02);
//
//        LocalDate localDateNew = LocalDate.of(2020,10,20);
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        String formateDate = localDateNew.format(dateTimeFormatter);
//        LocalDate finalDate = LocalDate.parse(formateDate,dateTimeFormatter);
//        log.info("finalDate:{}",finalDate);

        LocalDate date = LocalDate.of(2020,9,9);
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info("本身转换的日期：{}，{}",s1,s2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date1 = LocalDate.parse("2015-02-19",DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate date2 = LocalDate.parse("20150908",DateTimeFormatter.BASIC_ISO_DATE);
        log.info("转化后的日期：{}，{}",date1,date2);
    }

    public static void testSelfDateFormat(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date1 = LocalDate.of(2020,10,2);
        String formattedDate = date1.format(dateTimeFormatter);
        LocalDate date2 = LocalDate.parse(formattedDate,dateTimeFormatter);
        log.info("date2:{}",date2);
    }

}
