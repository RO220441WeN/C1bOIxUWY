// 代码生成时间: 2025-08-30 02:54:36
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ExcelGenerator is a class that generates Excel files using the PlayFramework and Apache POI.
 * It provides a simple interface to create Excel sheets and write data into them.
 */
public class ExcelGenerator {

    private Workbook workbook;
    private Sheet sheet;
    private int currentRow = 0;

    /**
     * Initializes a new ExcelWorkbook instance.
     */
    public ExcelGenerator() {
        workbook = new XSSFWorkbook(); // Use XSSFWorkbook for .xlsx files
    }

    /**
     * Creates a new sheet in the Excel workbook.
     *
     * @param sheetName The name of the sheet.
     */
    public void createSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    /**
     * Writes a header row to the current sheet.
     *
     * @param headers The header values.
     */
    public void writeHeaderRow(String[] headers) {
        Row headerRow = sheet.createRow(currentRow++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    /**
     * Writes a data row to the current sheet.
     *
     * @param data The data values.
     */
    public void writeDataRow(String[] data) {
        Row dataRow = sheet.createRow(currentRow++);
        for (int i = 0; i < data.length; i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(data[i]);
        }
    }

    /**
     * Writes a list of data rows to the current sheet.
     *
     * @param dataList The list of data rows.
     */
    public void writeDataList(List<String[]> dataList) {
        for (String[] data : dataList) {
            writeDataRow(data);
        }
    }

    /**
     * Saves the Excel workbook to a file.
     *
     * @param filePath The path to save the file.
     * @throws IOException If an I/O error occurs.
     */
    public void saveToFile(String filePath) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
    }

    /**
     * Closes the workbook and releases system resources.
     */
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            // Handle the exception or log it
            e.printStackTrace();
        }
    }

    // Example usage
    public static void main(String[] args) {
        ExcelGenerator generator = new ExcelGenerator();
        try {
            generator.createSheet(