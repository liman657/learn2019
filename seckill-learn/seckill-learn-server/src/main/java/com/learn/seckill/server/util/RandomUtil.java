package com.learn.seckill.server.util;
/**
 * Created by Administrator on 2019/6/20.
 */

import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数生成util
 * @Author:debug (SteadyJack)
 * @Date: 2019/6/20 21:05
 **/
public class RandomUtil {

    private static final SimpleDateFormat dateFormatOne=new SimpleDateFormat("yyyyMMddHHmmssSS");

    //高并发情况下，保证线程安全的ThreadLocalRandom
    private static final ThreadLocalRandom random=ThreadLocalRandom.current();

    /**
     * 生成订单编号-方式一
     * @return
     */
    public static String generateOrderCode(){
        //TODO:时间戳+N为随机数流水号
        return dateFormatOne.format(DateTime.now().toDate()) + generateNumber(4);
    }

    //N为随机数流水号
    public static String generateNumber(final int num){
        StringBuffer sb=new StringBuffer();
        for (int i=1;i<=num;i++){
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }


//    public static void main(String[] args) {
//        String salt="11299c42bf954c0abb373efbae3f6b26";
//        String password="debug";
//        System.out.println(new Md5Hash(password,salt));
//    }
}