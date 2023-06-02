package tw.gov.pcc.eip.framework.report;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import com.iisigroup.easyreport.xls.XlsReport;

import tw.gov.pcc.eip.util.NumberUtility;

/**
 * Report Base for Excel Report
 *
 * @author Goston
 */
public class ExcelReportBase extends XlsReport {
    
    public void setCellValue(Row row, Integer cellPosition, Object cellValue) {
        setCellValue(row, cellPosition, String.valueOf(cellValue), row.getCell(cellPosition).getCellStyle());
    }
    
    /**
     * 若轉數值轉換異常(非數值或null)則以字串格式印出空白，否則該cell以數值型態印出
     * @param row
     * @param cellPosition
     * @param cellValue
     * @param cellStyle
     */
    public void setCellStringOrNumValue(Row row, Integer cellPosition, String cellValue, CellStyle cellStyle) {
        try {
            setCellValue(row, cellPosition, NumberUtility.toDouble(cellValue),cellStyle);
        }catch (Exception  e) {
            setCellValue(row, cellPosition, "",cellStyle);
        }
    }
}
