package com.dianhua;

import java.math.BigInteger;

public class ExcelDemoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ExcelUtils excelData = new ExcelUtils("simInfo.xlsx","Sheet1");
			BigInteger num = BigInteger.valueOf((long) excelData.getCellDataasnumber(6,4));
			System.out.println(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
