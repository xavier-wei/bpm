package com.iisigroup.easyreport.xls.utility;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtility {

    /**
     * 讀入已存在之EXCEL檔
     *
     * @param filePath
     */
    public static Workbook readExcelFile(String filePath) {
        try {
            Workbook workbook = WorkbookFactory.create(new File(FilenameUtils.getBaseName(filePath)));
            return workbook;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 將兩個EXCEL檔案合併
     *
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static Workbook mergeFiles(Workbook sourceFile, Workbook targetFile) {
        try {
            if (!((sourceFile instanceof HSSFWorkbook) && (targetFile instanceof HSSFWorkbook)) && !((sourceFile instanceof XSSFWorkbook) && (targetFile instanceof XSSFWorkbook))) {
                throw new Exception("僅支援同格式 (同為xls或同為xlsx) 的檔案合併");
            }

            for (int i = 0; i < sourceFile.getNumberOfSheets(); i++) {
                Sheet sourceSheet = sourceFile.getSheetAt(i);
                Sheet targetSheet = targetFile.createSheet(sourceSheet.getSheetName());
                copyRows(sourceSheet, targetSheet, sourceSheet.getFirstRowNum(), sourceSheet.getLastRowNum());
            }

            return targetFile;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return targetFile;
        } catch (Exception e) {
            e.printStackTrace();
            return targetFile;
        }
    }

    /**
     * 複製行
     *
     * @param sourceSheet
     * @param targetSheet
     * @param sourceStartRow
     * @param sourceEndRow
     */
    private static void copyRows(Sheet sourceSheet, Sheet targetSheet, int sourceStartRow, int sourceEndRow) {
        Row sourceRow = null;
        Row targetRow = null;
        Cell sourceCell = null;
        Cell targetCell = null;
        CellRangeAddress region = null;
        CellType cType;
        int i;
        int j;
        int targetRowFrom;
        int targetRowTo;

        if ((sourceStartRow == -1) || (sourceEndRow == -1)) {
            return;
        }
        // 拷貝合並的單元格
        for (i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            region = sourceSheet.getMergedRegion(i);
            if ((region.getFirstRow() >= sourceStartRow) && (region.getLastRow() <= sourceEndRow)) {
                targetRowFrom = region.getFirstRow() - sourceStartRow;
                targetRowTo = region.getLastRow() - sourceStartRow;
                region.setFirstRow(targetRowFrom);
                region.setLastRow(targetRowTo);
                targetSheet.addMergedRegion(region);
            }
        }
        // 設置列寬
        for (i = sourceStartRow; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow != null) {
                for (j = 0; j < sourceRow.getLastCellNum(); j++) {
                    if (sourceSheet.getColumnWidth(j) <= 8) {
                        targetSheet.setColumnWidth(j, (short) 2500);
                    } else {
                        targetSheet.setColumnWidth(j, sourceSheet.getColumnWidth(j));
                    }
                }
                break;
            }
        }
        // 拷貝行並填充數据
        for (; i <= sourceEndRow; i++) {
            sourceRow = sourceSheet.getRow(i);
            if (sourceRow == null) {
                continue;
            }
            targetRow = targetSheet.createRow(i - sourceStartRow);
            targetRow.setHeight(sourceRow.getHeight());
            for (j = 0; j < sourceRow.getLastCellNum(); j++) {
                sourceCell = sourceRow.getCell(j);
                if (sourceCell == null) {
                    continue;
                }
                targetCell = targetRow.createCell(j);
                // targetCell.setEncoding(sourceCell.getEncoding());

                CellStyle origStyle = sourceCell.getCellStyle();

                CellStyle newStyle = (targetSheet.getWorkbook()).createCellStyle();
                newStyle.cloneStyleFrom(origStyle);
                targetCell.setCellStyle(newStyle);

                cType = sourceCell.getCellType();
                targetCell.setCellType(cType);
                switch (cType) {
                    case BOOLEAN:
                        targetCell.setCellValue(sourceCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        // parseFormula這個函數的用途在后面說明
                        targetCell.setCellFormula(parseFormula(sourceCell.getCellFormula()));
                        break;
                    case NUMERIC:
                        targetCell.setCellValue(sourceCell.getNumericCellValue());
                        break;
                    case STRING:
                        targetCell.setCellValue(sourceCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    /**
     * 公式的問題。POI對Excel公式的支持是相當好的，<br>
     * 但是我發現一個問題，如果公式里面的函數不帶參數， <br>
     * 比如now()或today()，那麼你通過getCellFormula()取出來的值就是now(ATTR(semiVolatile)) <br>
     * 和today(ATTR(semiVolatile))，這樣的值寫入Excel是會出錯的， <br>
     * 這也是上面copyRow的函數在寫入公式前要調用parseFormula的原因， <br>
     * parseFormula這個函數的功能很簡單，就是把ATTR(semiVolatile)刪掉
     *
     * @param pPOIFormula String
     * @return String
     */
    private static String parseFormula(String pPOIFormula) {
        final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
        StringBuffer result = null;
        int index;

        result = new StringBuffer();
        index = pPOIFormula.indexOf(cstReplaceString);
        if (index >= 0) {
            result.append(pPOIFormula, 0, index);
            result.append(pPOIFormula.substring(index + cstReplaceString.length()));
        } else {
            result.append(pPOIFormula);
        }

        return result.toString();
    }

}
