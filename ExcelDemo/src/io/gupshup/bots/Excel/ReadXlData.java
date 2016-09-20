package io.gupshup.bots.Excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadXlData 
{
	
	
public static void main(String[] args)
{
	ReadXlData rd = new ReadXlData();
	try {
		rd.readExcel("tabish");
	} catch (Exception e) 
	{
		
		e.printStackTrace();
	} 
	
}
	
	public ArrayList<String[]> readExcel(String name) throws BiffException, IOException {
		
		String FilePath = "C:\\Users\\Intern7\\Documents\\demo1.xls";
		FileInputStream fs = new FileInputStream(FilePath);
		Workbook wb = Workbook.getWorkbook(fs);

		// TO get the access to the sheet
		Sheet sh = wb.getSheet("Sheet1");

		// To get the number of rows present in sheet
		int totalNoOfRows = sh.getRows();

		// To get the number of columns present in sheet
		int totalNoOfCols = sh.getColumns();

		
		
		ArrayList<String[]> data1 = new ArrayList<String[]>();
			for (int col = 1; col < 2; col++) 
			{
				for (int row = 1; row < totalNoOfRows; row++) 
				{
					
					Cell cell =sh.getCell(col, row);
				if(cell.getContents().equals(name))
				{
				
					String data[]= new String[4];// no of column in excel
				for(int k=0; k<totalNoOfCols; k++)
				{
					data[k]=sh.getCell(k, row).getContents();
					
				
				}
				data1.add(data);
				
				}
				
				}
			
		}
			// To get the value of particular column from all table
			for(String[] str : data1)
			{
				System.out.println(str[2]);
				//where 2 is the no of cloumn.
			}
			
			/*// To get the value of row from array list
			String[] row = data1.get(0);
			for(String getrow : row)
			{
				System.out.print("printing row \t\t"+getrow+"\t\t");
				
			}*/
			
		return data1;
	}
	
}
