package com.expedia.ExpediaMavenFramework.utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	static XSSFWorkbook ExcelWBook;
	static XSSFSheet ExcelWSheet;
	static DataFormatter formatter = new DataFormatter();
	
	public static void setExcelPath(String path, String sheetName){
		try{
			FileInputStream excelFile = new FileInputStream(path);
			ExcelWBook = new XSSFWorkbook(excelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static Object[][] getCellData(String indicatorName){
		Object[][] cellData = null;
		XSSFCell[] boundaryCells = getEndCells(indicatorName);
		int startRow = boundaryCells[0].getRowIndex()+1;
		int startCol = boundaryCells[0].getColumnIndex()+1;
		int endRow = boundaryCells[1].getRowIndex()-1;
		int endCol = boundaryCells[1].getColumnIndex()-1;
		cellData = new String[endRow-startRow+1][endCol-startCol+1];
		Cell dataCell;
		for(int i = startRow; i <= endRow; i++) {
			for(int j = startCol; j <= endCol; j++) {
				dataCell = ExcelWSheet.getRow(i).getCell(j);
				cellData[i-startRow][j-startCol] = formatter.formatCellValue(dataCell);
			}
		}
		return cellData;
	}
	public static XSSFCell[] getEndCells(String tableName) {
		String pos = "start";
		XSSFCell[] endCells = new XSSFCell[2];
		for(Row rows:ExcelWSheet) {
			for(Cell cells:rows) {
				if(tableName.equalsIgnoreCase(formatter.formatCellValue(cells))) {
					if(pos.equals("start")) {
						endCells[0] = (XSSFCell)cells;
						pos = "end";
					}
					else {
						endCells[1] = (XSSFCell)cells;
					}
				}
			}
		}
		return endCells;
	}

}
