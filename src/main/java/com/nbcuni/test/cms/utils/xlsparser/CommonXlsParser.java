package com.nbcuni.test.cms.utils.xlsparser;

import com.nbcuni.test.webdriver.Utilities;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class CommonXlsParser {

    private CommonXlsParser(){
        super();
    }

    public static String[][] parse(String filePath, String sheetName) {
        String[][] data = null;
        try {
            FileInputStream file = new FileInputStream(new File(filePath));

            // Get the workbook instance for XLS file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(sheetName);
            data = new String[sheet.getLastRowNum() + 1][];
            int rowNum = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // For each row, iterate through each columns
                short lastCell = row.getLastCellNum();
                data[rowNum] = new String[lastCell];
                for (short i = 0; i < lastCell; i++) {
                    Cell cell = row.getCell(i);
                    data[rowNum][i] = cell.getStringCellValue();
                }
                rowNum++;
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            Utilities.logSevereMessageThenFail("Excel file is not found");
            e.printStackTrace();
        } catch (IOException e) {
            Utilities.logSevereMessageThenFail("Error during parsing");
            e.printStackTrace();
        }
        return data;
    }
}