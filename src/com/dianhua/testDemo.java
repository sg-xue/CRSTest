package com.dianhua;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testDemo {

	public static void main(String[] args) {
		String msg = "/s?wd=testng&pn=10&oq=testng&ie=utf-8&rsv_idx=1&rsv_pq=d616ddb40000ed62&rsv_t=359aXMJHQdVLaTdUQJijM3j4a1FZ0WClru3D0Ewz1oS2KOxAG6DJcUYk760";
//		String msg = "hello 123 world";
		Pattern pattern = Pattern.compile("(\\D+)(\\d+)(.*)");
//		Pattern pattern = Pattern.compile("^(\\w.*)");
		Matcher matcher = pattern.matcher(msg);
		System.out.println(matcher.find());
		System.out.println(matcher.group(2));

	}

}
