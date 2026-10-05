package utilies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExelFileManager {
    private static final Logger logger =
            LogManager.getLogger(ExelFileManager.class);

    public XSSFWorkbook workbook;
    public XSSFSheet sheet;

    public ExelFileManager(String filepath, String sheetName) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            logger.info("File opened successfully");

            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);

            logger.info("Sheet opened successfully");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error opening Excel file");
        }
    }

    public int getRowCount() {
        logger.debug("Getting Row Count");
        return sheet.getPhysicalNumberOfRows();
    }

    public int getColumnCount() {
        logger.debug("Getting Column Count");
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public String getFormula(int rowIndex, int columnIndex) {
        logger.debug("Getting Formula");
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        return cell.getCellFormula();
    }

    public String getSpecificCellValue(int rowIndex, int columnIndex) {
        logger.debug("Getting Specific Cell Value");
        Cell cell = sheet.getRow(rowIndex).getCell(columnIndex);
        return cell.getStringCellValue();
    }
}