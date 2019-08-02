package com.rakesh.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFXmlColumnPr;

public class ExcelReader {
	
	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet;
	private XSSFRow row;
	private XSSFCell cell;
	
	public ExcelReader(String path) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//returns the row count in the sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1) {
			return 0;
		}else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}
	
	//returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		
		try {
			if(rowNum <= 0)
				return "";
			int col_Num = -1;
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for(int i=0; i<row.getLastCellNum();i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					break;
				}
			}
			
			if(col_Num == -1)
				return "";
			
			row = sheet.getRow(rowNum-1);
			if(row == null)
				return "";
			cell = row.getCell(col_Num);
			
			if(cell == null)
				return "";
			
			if(cell.getCellType() == cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC || cell.getCellType() == cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = cal.DAY_OF_MONTH + "/" + 
								cal.MONTH + 1 + "/" +
								String.valueOf(cal.YEAR).substring(2);
					
				}
				return cellText;
			}else if(cell.getCellType() == cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		}catch(Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist";
		}
		
		
	}
	
	
	//returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		
		try {
			if(rowNum <= 0)
				return "";
			if(colNum < 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			row = sheet.getRow(rowNum-1);
			if(row == null)
				return "";
			cell = row.getCell(colNum);
			
			if(cell == null)
				return "";
			
			if(cell.getCellType() == cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType() == cell.CELL_TYPE_NUMERIC || cell.getCellType() == cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = cal.DAY_OF_MONTH + "/" + 
								cal.MONTH + 1 + "/" +
								String.valueOf(cal.YEAR).substring(2);
					
				}
				return cellText;
			}else if(cell.getCellType() == cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		}catch(Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist";
		}
		
		
	}
	
	
	//returns true if the value is set successfully or false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		
		try {
			if(rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			if(index == -1)
				return false;
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			
			int col_Num = -1;
			for(int i=0; i<row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
					break;
				}
			}
			
			if(col_Num == -1)
				return false;
			
			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(rowNum - 1);
			if(row == null)
				sheet.createRow(rowNum - 1);
			
			cell = row.getCell(col_Num);
			if(cell == null)
				row.createCell(col_Num);
			
			cell.setCellValue(data);
			
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	//returns true if the value is set successfully or false
		public boolean setCellData(String sheetName, int colNum, int rowNum, String data) {
			
			try {
				if(rowNum <= 0)
					return false;
				if(colNum <= 0)
					return false;
				
				int index = workbook.getSheetIndex(sheetName);
				if(index == -1)
					return false;
				
				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(0);
				
				
				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(rowNum - 1);
				if(row == null)
					sheet.createRow(rowNum - 1);
				
				cell = row.getCell(colNum);
				if(cell == null)
					row.createCell(colNum);
				
				cell.setCellValue(data);
				
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				fileOut.close();
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
		
		
		// returns true if data is set successfully else false
		public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
			
			try{
			fis = new FileInputStream(path); 
			workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
			}
			
			if(colNum==-1)
				return false;
			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);
				
		    cell.setCellValue(data);
		    XSSFCreationHelper createHelper = workbook.getCreationHelper();

		    //cell style for hyperlinks
		    
		    CellStyle hlink_style = workbook.createCellStyle();
		    XSSFFont hlink_font = workbook.createFont();
		    hlink_font.setUnderline(XSSFFont.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
		    //hlink_style.setWrapText(true);

		    XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
		    link.setAddress(url);
		    cell.setHyperlink(link);
		    cell.setCellStyle(hlink_style);
		      
		    fileOut = new FileOutputStream(path);
			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		
		//returns true if the sheet is created successfully else false
		public boolean addSheet(String sheetName) {
			
			try {
				workbook.createSheet(sheetName);
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				fileOut.close();
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		//returns true if the sheet is removed successfully else false if it does not exist
		public boolean removeSheet(String sheetName) {
			
			int index = workbook.getSheetIndex(sheetName);
			if(index == -1)
				return false;
			
			try {
				fileOut = new FileOutputStream(path);
				workbook.removeSheetAt(index);
				workbook.write(fileOut);
				fileOut.close();
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		
		
		//returns true if the column is created successfully
		public boolean addColumn(String sheetName, String colName) {
			
			try {
				
				fileOut = new FileOutputStream(path);
				int index = workbook.getSheetIndex(sheetName);
				if(index == -1)
					return false;
				
				sheet = workbook.getSheetAt(index);
				
				XSSFCellStyle  style = workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				row = sheet.getRow(0);
				if(row == null)
					sheet.createRow(0);
				
				if(row.getLastCellNum() == -1)
					cell = row.createCell(0);
				else
					cell = row.createCell(row.getLastCellNum());
				
				cell.setCellValue(colName);
				cell.setCellStyle(style);
				
				workbook.write(fileOut);
				fileOut.close();
				return true;
				
				
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		
		//removes a column and all its contents
		public boolean removeColumn(String sheetName, int colNum) {
			
			try {
				
				if(colNum <= 0)
					return false;
				
				int index = workbook.getSheetIndex(sheetName);
				if(index == -1)
					return false;
				
				sheet = workbook.getSheetAt(index);
				
				for(int i=0; i<sheet.getLastRowNum(); i++) {
					row = sheet.getRow(i);
					cell = row.getCell(colNum);
					if(row!=null) {
						row.removeCell(cell);
					}
				}
				
				fileOut = new FileOutputStream(path);
				workbook.write(fileOut);
				fileOut.close();
				return true;
				
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		
		//find whether sheet exists
		public boolean isSheetExist(String sheetname) {
			int index = workbook.getSheetIndex(sheetname);
			if(index == -1) {
				index = workbook.getSheetIndex(sheetname.toUpperCase());
				if(index == -1)
					return false;
				else
					return true;
			}else
				return true;
		}
		
		
		//returns number os columns in a sheet
		public int getColumnCount(String sheetName) {
			if(!isSheetExist(sheetName))
				return -1;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			if(row == null)
				return -1;
			
			return row.getLastCellNum();
		}
		
		
		//String sheetName, String testCaseName,String keyword ,String URL,String message
		public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,int index,String url,String message){
			
			
			url=url.replace('\\', '/');
			if(!isSheetExist(sheetName))
				 return false;
			
		    sheet = workbook.getSheet(sheetName);
		    
		    for(int i=2;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)){
		    		
		    		setCellData(sheetName, screenShotColName, i+index, message,url);
		    		break;
		    	}
		    }


			return true; 
		}
		
		//return row number for a cell with specific value
		public int getCellRowNum(String sheetName,String colName,String cellValue){
			
			for(int i=2;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
		    		return i;
		    	}
		    }
			return -1;
			
		}
		
		
		// to run this on stand alone
		public static void main(String arg[]) throws IOException{
			
			
			ExcelReader datatable = null;
			

				 datatable = new ExcelReader("C:\\Rakesh\\Personal\\Courses\\Eclipse workspace\\DataDrivenFramework\\src\\test\\resources\\excel\\testData.xlsx");
					for(int col=0 ;col< datatable.getColumnCount("TC5"); col++){
						System.out.println(datatable.getCellData("TC5", col, 1));
					}
		}
		
	
}
