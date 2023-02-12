package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLXSReader {

	public String filepath;
	private FileInputStream fis = null;;
	private Workbook workbook = null;;
	private Sheet sheet = null;;
	private Row row = null;;
	private Cell cell = null;;
	public FileOutputStream fileOut = null;
	private String fileExtension = null;

	public XLXSReader(String filepath) throws IOException {

		this.filepath = filepath;
		fileExtension = filepath.substring(filepath.indexOf(".x"));

		try {
			fis = new FileInputStream(filepath);

			if (fileExtension.equals(".xlsx")) {

				workbook = new XSSFWorkbook(fis);

			} else if (fileExtension.equals(".xls")) {

				workbook = new HSSFWorkbook(fis);

			}

			sheet = workbook.getSheetAt(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}

	}

	// returns the row count in a sheet
	public int getRowCount(String sheetname) {

		int sheetIndex = workbook.getSheetIndex(sheetname);
		if (sheetIndex == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(sheetIndex);
			int rowsCount = sheet.getLastRowNum() + 1;
			return rowsCount;
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetname, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int sheetIndex = workbook.getSheetIndex(sheetname);
			if (sheetIndex == -1)
				return "";

			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(0);
			int colNum = -1;

			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().equals(colName))
					colNum = i;

			}

			if (colNum == -1)
				return "";

			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";

			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}

				return cellText;

			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";

		}

	}

	// returns the data from a cell
	public String getCellData(String sheetname, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int sheetIndex = workbook.getSheetIndex(sheetname);

			if (sheetIndex == -1)
				return "";

			sheet = workbook.getSheetAt(sheetIndex);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum - 1);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";

		}
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {

			if (rowNum <= 0)
				return false;

			int sheetIndex = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (sheetIndex == -1)
				return false;

			sheet = workbook.getSheetAt(sheetIndex);

			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			// cell style
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(filepath);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		// check if sheet exists
		if (!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}


	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 1; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}
}
