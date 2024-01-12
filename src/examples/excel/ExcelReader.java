package examples.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private static final String FILE_PATH = "d:/test.xlsx";

    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream(FILE_PATH);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println("found cell with type:" + cell.getCellType());
                switch (cell.getCellType()) {
                    case _NONE -> {
                    }
                    case NUMERIC -> {
                        System.out.println(cell.getNumericCellValue());
                    }
                    case STRING -> {
                        System.out.println(cell.getStringCellValue());
                    }
                    case FORMULA -> {
                        System.out.println(cell.getCellFormula());
                    }
                    case BLANK -> {

                    }
                    case BOOLEAN -> {
                        System.out.println(cell.getBooleanCellValue());
                    }
                    case ERROR -> {
                        System.out.println(cell.getErrorCellValue());
                    }
                }
            }
        }
    }
}
