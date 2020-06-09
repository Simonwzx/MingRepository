package cn.xiuminglee.jt809.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wangzongxiang
 * @date 2020/6/9 11:18 上午
 */
public class DateUtil {

    /**
     * 获取当前时刻的UTC时间
     *
     * @return
     */
    public static long nowUtcTime() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    public static LocalDateTime utcTime2LocalDateTime(long utcTime) {
        return LocalDateTime.ofEpochSecond(utcTime, 0, ZoneOffset.of("+8"));
    }

    public static void main(String[] args) {
        long l = LocalDateTime.of(2010, 1, 10, 9, 7, 54)
                .toEpochSecond(ZoneOffset.of("+8"));
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(1263085674, 0, ZoneOffset.of("+8"));
        System.out.println(localDateTime);
    }
}
