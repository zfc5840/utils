package com.java.utils.javaUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * 
 * Description: 日期工具类 All Rights Reserved.
 * 
 * @version 1.0 2013-7-30 下午4:05:04 by weihuining（hn.wei@zuche.com）创建
 */
public class DateHandleUtils {


	public static final String LANGUAGE_CN = "CN";
	public static final String LANGUAGE_EN = "EN";

	public static final String DAY_EN = "day";
	public static final String HOUR_EN = "hr";
	public static final String MINUTE_EN = "min";
	public static final String SECOND_EN = "s";

	public static final String DAY = "天";
	public static final String HOUR = "小时";
	public static final String MINUTE = "分";
	public static final String SECOND = "秒";
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_MIN = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_MILLISECOND = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 
	 * Description: 字符串日期转换为Date类型日期
	 * 
	 * @Version1.0 2013-7-30 下午4:05:20 by weihuining（hn.wei@zuche.com）创建
	 * @param str
	 * @param format
	 *            日期格式化的格式
	 * @return
	 */
	public static Date string2Date(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String date2String(Date date, String format) {
		if(date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 
	 * Description: 日期比较器
	 * 
	 * @Version1.0 2013-8-7 上午9:10:27 by weihuining（hn.wei@zuche.com）创建
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static boolean dateComparet(String date1, String date2, String format) {
		return string2Date(date1, format).getTime() == string2Date(date2,
				format).getTime();
	}

	/**
	 * 
	 * Description: 日期比较器
	 * 
	 * @Version1.0 2013-8-7 上午9:10:27 by weihuining（hn.wei@zuche.com）创建
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static boolean dateComparetGt(String date1, String date2,
			String format) {
		return string2Date(date1, format).getTime() > string2Date(date2, format)
				.getTime();
	}

	/**
	 * 
	 * Description: 日期 ++
	 * 
	 * @Version1.0 2013-8-7 上午9:12:25 by weihuining（hn.wei@zuche.com）创建
	 * @param date
	 * @param i
	 * @return
	 */
	public static String addDate(String date, int i) {
		Date d = string2Date(date, "yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, i);
		d = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(d);

	}

	public static String addDate(Date date, int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		date = cal.getTime();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);

	}

	public static String getDateByTime(String date, String hour, String min) {
		if (date == null || "".equals(date)) {
			return null;
		}
		Date d = string2Date(date, "yyyy-MM-dd");
		long l = d.getTime();
		if (StringUtils.isIntegerNum(hour)) {
			l = l + Integer.parseInt(hour) * 60 * 60 * 1000;
		}
		if (StringUtils.isIntegerNum(min)) {
			l = l + Integer.parseInt(min) * 60 * 1000;
		}

		return date2String(new Date(l), FORMAT);
	}

	/**
	 * 
	 * Description: 时长（秒） 转换成 格式 00天00小时00分钟00秒
	 * 
	 * @Version1.0 2013-9-30 上午11:58:16 by weihuining（hn.wei@zuche.com）创建
	 * @param timeLength
	 *            分钟
	 * @return
	 */
	public static String getActualTimeLength(String timeLength) {
		if (timeLength != null && !"".equals(timeLength)) {
			try {
				int i = new BigDecimal(timeLength).intValue() * 60;
				return getChineseDescFromSecond(i);
			} catch (NumberFormatException e) {
			e.printStackTrace();
				return timeLength;
			}
		} else {
			return "0小时";
		}
	}

	/**
	 * Description: 由秒数获取其时长的中文描述
	 * 
	 * @Version1.0 2014-12-2 上午9:20:35 by 王颖慧（yh.wang04@zuche.com）创建
	 * @param timeLength
	 * @return
	 */
	public static String getChineseDescFromSecond(int secondLength) {
		try {
			if (secondLength < 0) {
				return "0小时";
			}
			StringBuffer sb = new StringBuffer();
			if (secondLength >= 3600 * 24) {
				sb.append(secondLength / (3600 * 24) + "天");
				secondLength = secondLength % (3600 * 24);
			}
			if (secondLength >= 3600) {
				sb.append(secondLength / 3600 + "小时");
				secondLength = secondLength % 3600;
			}
			if (secondLength >= 60) {
				sb.append(secondLength / 60 + "分");
				secondLength = secondLength % 60;
			}
			if (secondLength > 0) {
				sb.append(secondLength + "秒");
			}
			return sb.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return String.valueOf(secondLength);
		}
	}

	/**
	 * 
	 * Description:
	 * 
	 * @Version1.0 2014-1-22 上午11:15:51 by weihuining（hn.wei@zuche.com）创建
	 * @param timeLength
	 * @param language
	 *            CN中文，EN英文
	 * @return
	 */
	public static String getActualTimeLength(String timeLength, String language) {
		String day = DAY;
		String hour = HOUR;
		String minute = MINUTE;
		String second = SECOND;
		String complex = "";
		if (LANGUAGE_EN.equalsIgnoreCase(language)) {
			day = DAY_EN;
			hour = HOUR_EN;
			minute = MINUTE_EN;
			second = SECOND_EN;
			complex = "s";
		}
		if (timeLength != null && !"".equals(timeLength)) {
			try {
				int i = Integer.parseInt(timeLength);
				if (i < 0) {
					return "0" + hour;
				}
				StringBuffer sb = new StringBuffer();
				if (i >= 3600 * 24) {
					int d = i / (3600 * 24);
					sb.append(d + day + (d > 1 ? complex : ""));
					i = i % (3600 * 24);
				}
				if (i >= 3600) {
					int h = i / 3600;
					sb.append(h + hour + (h > 1 ? complex : ""));
					i = i % 3600;
				}
				if (i >= 60) {
					int m = i / 60;
					sb.append(m + minute + (m > 1 ? complex : ""));
					i = i % 360;
				}
				if (i >= 60) {
					int s = i / 60;
					sb.append(s + second);
				} else {
					sb.append(i + second);
				}
				return sb.toString();
			} catch (NumberFormatException e) {
				e.printStackTrace();
				if (LANGUAGE_EN.equalsIgnoreCase(language)
						&& timeLength != null) {
					return timeLength.replace(DAY, DAY_EN + complex)
							.replace(MINUTE, MINUTE_EN + complex)
							.replace(SECOND, SECOND_EN)
							.replace(HOUR, HOUR_EN + complex);
				}
				return timeLength;
			}
		} else {
			return "0" + hour;
		}
	}

	/**
	 * Description: 判断当前时间与源时间相差小时数
	 * 
	 * @Version1.0 2014-1-15 下午3:18:39 by 王颖慧（yh.wang@zuche.com）创建
	 * @param date
	 * @return
	 */
	public static boolean checkAfterNowHours(Date date, int hours) {
		Date now = new Date();
		long dateTime = date.getTime();
		long nowTime = now.getTime();
		if (dateTime - nowTime < hours * 60 * 60 * 1000) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 
	 * Description: 到现在minutes分钟之内
	 * 
	 * @Version1.0 2014-7-19 上午11:30:20 by 魏爱国（ag.wei）创建
	 * @param date
	 * @param minutes
	 * @return true，minutes分钟之内;false,大于minutes分钟
	 */
	public static boolean checkBeforeNowMinutes(Date date, int minutes) {
		Date now = new Date();
		long dateTime = date.getTime();
		long nowTime = now.getTime();
		if (nowTime - dateTime < minutes * 60 * 1000) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Description: 添加小时数
	 * 
	 * @Version1.0 2014-1-20 上午11:02:00 by 王颖慧（yh.wang@zuche.com）创建
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, hour);
		return c.getTime();
	}

	public static int getHour4Time(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minute);
		return c.getTime();
	}

	// 根据日期取得星期几
	public static String getWeek(Date date) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

}
