package com.dianhua;

public class ExcelDemoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ExcelUtils excelData = new ExcelUtils("F:\\eclipse_workspace\\ExcelDataDriven\\testData.xlsx","Sheet1");
			System.out.println(excelData.getCellDataasstring(0,0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
