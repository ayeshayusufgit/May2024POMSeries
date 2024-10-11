package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;//This has to be imported for Sheet from apache poi

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	private static final String TEST_DATA_SHEET_PATH="./src/test/resources/testdata/opencarttestdata.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] geTesttData(String sheetName){
		System.out.println("Reading data from sheet:"+sheetName);
		Object[][] data=null;
		
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);//This is to make connection with the file
			book = WorkbookFactory.create(ip);
			//loads the excel file in the memory, so that it can be directly read from the memory
			
			sheet=book.getSheet(sheetName);//goes to the specific sheet
			
			//the rows and columns are put into a 2-d object array
			data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int i=0;i<sheet.getLastRowNum();i++) {
				for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
					
					data[i][j]=sheet.getRow(i+1).getCell(j).toString();//row starts from the 2nd row
					//sheet.getRow(i+1).getCell(j); => will return data in the form of excel data
					//using toString() it has to be converted into string data
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}
