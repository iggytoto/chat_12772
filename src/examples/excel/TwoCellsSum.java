package examples.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class TwoCellsSum {

    private static final String FILE_PATH = "d:/test.xlsx";

    public static void main(String[] args) throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("sum example");
        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(1);

        headerCell = header.createCell(1);
        headerCell.setCellValue(2);

        headerCell = header.createCell(2);
        headerCell.setCellFormula("A1+B1");


        FileOutputStream outputStream = new FileOutputStream(FILE_PATH);
        workbook.write(outputStream);
        workbook.close();
    }
}
