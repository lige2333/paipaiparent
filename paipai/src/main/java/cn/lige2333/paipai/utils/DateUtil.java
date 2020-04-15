package cn.lige2333.paipai.utils;

import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	/**
	 * 得到一天的最后一秒钟
	 * 
	 * @param d
	 * @return
	 */
	public static Date endOfDay(Date d) {
		return DateUtils.addSeconds(
				DateUtils.addDays(DateUtils.truncate(d, Calendar.DATE), 1), -1);
	}

	/**
	 * 两个时间的间隔秒
	 * 
	 * @return
	 */
	public static long secondsBetween(Date d1, Date d2) {
		return Math.abs((d1.getTime() - d2.getTime()) / 1000);
	}
	public static long secondsBetweenNotAbs(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime()) / 1000;
	}
}