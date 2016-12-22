package com.dianhua;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testDemo {

	public static void main(String[] args) throws InterruptedException, ParseException {
//		String msg = "/s?wd=testng&pn=10&oq=testng&ie=utf-8&rsv_idx=1&rsv_pq=d616ddb40000ed62&rsv_t=359aXMJHQdVLaTdUQJijM3j4a1FZ0WClru3D0Ewz1oS2KOxAG6DJcUYk760";
//		Pattern pattern = Pattern.compile("(\\D+)(\\d+)(.*)");
//		Matcher matcher = pattern.matcher(msg);
//		System.out.println(matcher.find());
//		System.out.println(matcher.group(2));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String startDate = df.format(new Date());
		Thread.sleep(2000);
		String bdate = df.format(new Date());
		java.util.Date now = df.parse(bdate);
		java.util.Date date=df.parse(startDate);
		long tempTime =now.getTime()-date.getTime();
		long day = tempTime /(24*60*60*1000);
		long hour = (tempTime/(60*60*1000)-day*24);
		long min = ((tempTime/(60*1000))-day*24*60-hour*60);
		long second = (tempTime/1000-day*24*60*60-hour*60*60-min*60);
		System.out.println(""+day+"天"+hour+"小时"+min+"分"+second+"秒");
	}

}
