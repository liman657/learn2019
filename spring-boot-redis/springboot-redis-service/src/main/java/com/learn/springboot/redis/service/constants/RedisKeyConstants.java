package com.learn.springboot.redis.service.constants;


/**
 * 系统常量配置
 **/
public class RedisKeyConstants {

    public static final String RedisStringPrefix="SpringBootRedis:String:";

    public static final String RedisListPrefix="SpringBootRedis:List:User:"+"User_Prod_";

    public static final String RedisListNoticeKey="SpringBootRedis:List:Queue:Notice";

    public static final String RedisSetKey="SpringBoot:Redis:Set:User:Email";

    public static final String RedisSetProblemKey="SpringBoot:Redis:Set:Problem";

    public static final String RedisSetProblemsKey="SpringBoot:Redis:Set:Problems";

    public static final String RedisSortedSetKey1 ="SpringBootRedis:SortedSet:PhoneFare:key1";

    public static final String RedisSortedSetKey2 ="SpringBootRedis:SortedSet:PhoneFare:key2";

    public static final String RedisHashKeyConfig ="SpringBootRedis:Hash:Key:SysConfig";


    public static final String RedisCacheBeatLockKey="SpringBootRedis:LockKey:";
}























