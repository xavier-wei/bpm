package com.iisigroup.easyreport.pdf.utility;

import com.iisigroup.easyreport.pdf.EasyReportPdfReader;
import com.iisigroup.easyreport.pdf.Helper.EnvHelper;
import com.iisigroup.easyreport.pdf.exception.ReportException;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 提供 PDF 檔常用功能，如：拆檔、併檔、上鎖、套表等
 *
 * @author Goston
 */
public class PdfUtility {

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param fieldNames   欄位名稱（若找不到指定之欄位，則不會套值）
     * @param fieldValues  欄位值
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static byte[] templateReport(byte[] templateFile, String[] fieldNames, List<String> fieldValues) throws ReportException {
        try {
            if (fieldNames == null || fieldValues == null || fieldNames.length == 0 || fieldValues.size() == 0)
                throw new ReportException("套表之欄位名稱及欄位值 List 不得為 null");

            if (fieldNames.length != fieldValues.size())
                throw new ReportException("套表之欄位名稱及欄位值 List.size() 不同");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (int i = 0; i < fieldNames.length; i++) {
                form.setField(StringUtils.defaultString(fieldNames[i]), StringUtils.defaultString(fieldValues.get(i)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param fieldNames   欄位名稱（若找不到指定之欄位，則不會套值）
     * @param fieldValues  欄位值
     * @param copy         <code>PdfSmartCopy</code> Object
     * @throws ReportException
     */
    public static void templateReport(byte[] templateFile, String[] fieldNames, List<String> fieldValues, PdfSmartCopy copy) throws ReportException {
        try {
            if (fieldNames == null || fieldValues == null || fieldNames.length == 0 || fieldValues.size() == 0)
                throw new ReportException("套表之欄位名稱及欄位值 List 不得為 null");

            if (fieldNames.length != fieldValues.size())
                throw new ReportException("套表之欄位名稱及欄位值 List.size() 不同");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (int i = 0; i < fieldNames.length; i++) {
                form.setField(StringUtils.defaultString(fieldNames[i]), StringUtils.defaultString(fieldValues.get(i)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            template = new PdfReader(outFile.toByteArray());
            copy.addPage(copy.getImportedPage(template, 1));
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param fieldNames   欄位名稱（若找不到指定之欄位，則不會套值）
     * @param fieldValues  欄位值
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static byte[] templateReport(byte[] templateFile, List<String> fieldNames, List<String> fieldValues) throws ReportException {
        try {
            if (fieldNames == null || fieldValues == null || fieldNames.size() == 0 || fieldValues.size() == 0)
                throw new ReportException("套表之欄位名稱及欄位值 List 不得為 null");

            if (fieldNames.size() != fieldValues.size())
                throw new ReportException("套表之欄位名稱及欄位值 List.size() 不同");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (int i = 0; i < fieldNames.size(); i++) {
                form.setField(StringUtils.defaultString(fieldNames.get(i)), StringUtils.defaultString(fieldValues.get(i)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param fieldNames   欄位名稱（若找不到指定之欄位，則不會套值）
     * @param fieldValues  欄位值
     * @param copy         <code>PdfSmartCopy</code> Object
     * @throws ReportException
     */
    public static void templateReport(byte[] templateFile, List<String> fieldNames, List<String> fieldValues, PdfSmartCopy copy) throws ReportException {
        try {
            if (fieldNames == null || fieldValues == null || fieldNames.size() == 0 || fieldValues.size() == 0)
                throw new ReportException("套表之欄位名稱及欄位值 List 不得為 null");

            if (fieldNames.size() != fieldValues.size())
                throw new ReportException("套表之欄位名稱及欄位值 List.size() 不同");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (int i = 0; i < fieldNames.size(); i++) {
                form.setField(StringUtils.defaultString(fieldNames.get(i)), StringUtils.defaultString(fieldValues.get(i)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            template = new PdfReader(outFile.toByteArray());
            copy.addPage(copy.getImportedPage(template, 1));
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param map          <code>HashMap</code> 其 Key 為欄位名稱，Value 為欄位值
     * @return 套表完成之新檔案
     * @throws ReportException
     */
    public static byte[] templateReport(byte[] templateFile, HashMap<String, String> map) throws ReportException {
        try {
            if (map == null)
                throw new ReportException("套表之欄位名稱及欄位值不得為 null");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (String key : map.keySet()) {
                if (key != null)
                    form.setField(key, StringUtils.defaultString(map.get(key)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 套表
     *
     * @param templateFile Template 檔
     * @param map          <code>HashMap</code> 其 Key 為欄位名稱，Value 為欄位值
     * @param copy         <code>PdfSmartCopy</code> Object
     * @throws ReportException
     */
    public static void templateReport(byte[] templateFile, HashMap<String, String> map, PdfSmartCopy copy) throws Exception {
        try {
            if (map == null)
                throw new Exception("套表之欄位名稱及欄位值不得為 null");

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader template = new PdfReader(templateFile);

            PdfStamper stamp = new PdfStamper(template, outFile);
            AcroFields form = stamp.getAcroFields();

            for (String key : map.keySet()) {
                if (key != null)
                    form.setField(key, StringUtils.defaultString(map.get(key)));
            }

            stamp.setFormFlattening(true);
            stamp.close();
            outFile.close();

            template = new PdfReader(outFile.toByteArray());
            copy.addPage(copy.getImportedPage(template, 1));
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 檔上鎖 - 只允許列印
     *
     * @param file         欲上索之來源檔案
     * @return 上鎖後之新檔案，若傳入的 <code>file</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static byte[] lockFile(byte[] file, String userPassword) throws ReportException {
        return lockFile(file, userPassword, PdfWriter.ENCRYPTION_AES_128);
    }

    /**
     * PDF 檔上鎖 - 只允許列印
     *
     * @param file         欲上索之來源檔案
     * @param encryption   加密演算法 <code>PdfWriter.ENCRYPTION_AES_128</code>、<code>PdfWriter.ENCRYPTION_AES_256</code>、<code>PdfWriter.STANDARD_ENCRYPTION_40</code>、<code>PdfWriter.STANDARD_ENCRYPTION_128</code>
     * @return 上鎖後之新檔案，若傳入的 <code>file</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static byte[] lockFile(byte[] file, String userPassword, int encryption) throws ReportException {
        try {
            if (file == null)
                return null;

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            byte[] userPass = null;
            if (StringUtils.isNotBlank(userPassword))
                userPass = userPassword.getBytes();

            byte[] ownerPass = null;
            if (StringUtils.isNotBlank(EnvHelper.getOwnerPassword()))
                ownerPass = EnvHelper.getOwnerPassword().getBytes();

            PdfReader reader = new PdfReader(file);
            PdfStamper stamper = new PdfStamper(reader, outFile);
            stamper.setEncryption(userPass, ownerPass, PdfWriter.ALLOW_PRINTING, encryption | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 檔上鎖 - 只能看連列印也不行
     *
     * @param file         欲上索之來源檔案
     * @return 上鎖後之新檔案，若傳入的 <code>file</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static byte[] lockFileReadOnly(byte[] file, String userPassword) throws ReportException {
        return lockFileReadOnly(file, userPassword, PdfWriter.ENCRYPTION_AES_128);
    }

    /**
     * PDF 檔上鎖 - 只能看連列印也不行
     *
     * @param file         欲上索之來源檔案
     * @param encryption   加密演算法 <code>PdfWriter.ENCRYPTION_AES_128</code>、<code>PdfWriter.ENCRYPTION_AES_256</code>、<code>PdfWriter.STANDARD_ENCRYPTION_40</code>、<code>PdfWriter.STANDARD_ENCRYPTION_128</code>
     * @return 上鎖後之新檔案，若傳入的 <code>file</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static byte[] lockFileReadOnly(byte[] file, String userPassword, int encryption) throws ReportException {
        try {
            if (file == null)
                return null;

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            byte[] userPass = null;
            if (StringUtils.isNotBlank(userPassword))
                userPass = userPassword.getBytes();

            byte[] ownerPass = null;
            if (StringUtils.isNotBlank(EnvHelper.getOwnerPassword()))
                ownerPass = EnvHelper.getOwnerPassword().getBytes();

            PdfReader reader = new PdfReader(file);
            PdfStamper stamper = new PdfStamper(reader, outFile);
            stamper.setEncryption(userPass, ownerPass, PdfWriter.ALLOW_SCREENREADERS, encryption | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 檔解鎖
     *
     * @param file         欲解鎖之來源檔案
     * @return 解後之新檔案
     * @throws ReportException
     */
    public static byte[] unlockFile(byte[] file, String userPassword) throws ReportException {
        try {
            if (file == null)
                return null;

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            EasyReportPdfReader.unethicalreading = true;
            EasyReportPdfReader reader = new EasyReportPdfReader(file, userPassword.getBytes());
            reader.decryptOnPurpose();
            PdfStamper stamper = new PdfStamper(reader, outFile);
            stamper.close();
            reader.close();
            outFile.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 拆檔
     *
     * @param pdfFile      要進行拆檔的 PDF 檔
     * @param pagesPerFile 每個檔幾頁
     * @return 拆檔完成的檔案，若傳入的 <code>pdfFile</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static List<byte[]> splitFile(byte[] pdfFile, int pagesPerFile) throws ReportException {
        try {
            if (pdfFile == null)
                return null;

            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            return splitFile(pdfFile, pagesPerFile, 1, n);
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 拆檔
     *
     * @param pdfFile      要進行拆檔的 PDF 檔
     * @param pagesPerFile 每個檔幾頁
     * @param fromPage     開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @param toPage       結束頁數，設為 <code>-1</code> 表檔案的最後一頁；若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是結束頁數
     * @return 拆檔完成的檔案，若傳入的 <code>pdfFile</code> 為 <code>null</code>，則回傳的結果亦是 <code>null</code>
     * @throws ReportException
     */
    public static List<byte[]> splitFile(byte[] pdfFile, int pagesPerFile, int fromPage, int toPage) throws ReportException {
        try {
            if (pdfFile == null)
                return null;

            PdfReader reader = new PdfReader(pdfFile);

            List<byte[]> returnList = new ArrayList<byte[]>();

            Document document = new Document(); // 原先是直接給 Null，但 Fortify 不喜歡，故隨便給初始值
            ByteArrayOutputStream outFile = new ByteArrayOutputStream(); // 原先是直接給 Null，但 Fortify 不喜歡，故隨便給初始值
            PdfSmartCopy writer = new PdfSmartCopy(document, outFile); // 原先是直接給 Null，但 Fortify 不喜歡，故隨便給初始值

            int printPages = 0; // 已印頁數

            int n = reader.getNumberOfPages();

            if (fromPage <= 0)
                fromPage = 1;

            if (toPage <= 0)
                toPage = n;

            if (fromPage > n)
                fromPage = n;

            if (toPage > n)
                toPage = n;

            int temp;

            if (fromPage > toPage) {
                temp = fromPage;
                fromPage = toPage;
                toPage = temp;
            }

            for (int i = fromPage; i <= toPage; i++) {
                if (i == fromPage) {
                    outFile = new ByteArrayOutputStream();
                    document = new Document(reader.getPageSizeWithRotation(i));
                    writer = new PdfSmartCopy(document, outFile);
                }

                document.open();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                writer.addPage(page);

                printPages++;

                if (i == toPage || (printPages % pagesPerFile == 0)) {
                    document.close();
                    writer.close();
                    outFile.close();
                    returnList.add(outFile.toByteArray());

                    printPages = 0;

                    if (i != toPage) {
                        outFile = new ByteArrayOutputStream();
                        document = new Document(reader.getPageSizeWithRotation(i));
                        writer = new PdfSmartCopy(document, outFile);
                    }
                }
            }

            return returnList;
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 拆檔 - 直接輸出成檔案<br>
     * 若傳入的 <code>pdfFile</code> 為 <code>null</code>，則不會輸出任何檔案
     *
     * @param pdfFile      要進行拆檔的 PDF 檔
     * @param outputPath   輸出目錄
     * @param fileName     檔名（不含 <code>.pdf</code>，程式會自動為檔案加入檔案流水號）
     * @param pagesPerFile 每個檔幾頁
     * @throws ReportException
     */
    public static void splitFile(byte[] pdfFile, String outputPath, String fileName, int pagesPerFile) throws ReportException {
        try {
            if (pdfFile == null)
                return;

            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            splitFile(pdfFile, outputPath, fileName, pagesPerFile, 1, n);
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 拆檔 - 直接輸出成檔案<br>
     * 若傳入的 <code>pdfFile</code> 為 <code>null</code>，則不會輸出任何檔案
     *
     * @param pdfFile      要進行拆檔的 PDF 檔
     * @param outputPath   輸出目錄
     * @param fileName     檔名（不含 <code>.pdf</code>，程式會自動為檔案加入檔案流水號）
     * @param pagesPerFile 每個檔幾頁
     * @param fromPage     開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @param toPage       結束頁數，設為 <code>-1</code> 表檔案的最後一頁；若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是結束頁數
     * @throws ReportException
     */
    public static void splitFile(byte[] pdfFile, String outputPath, String fileName, int pagesPerFile, int fromPage, int toPage) throws ReportException {
        FileOutputStream fos = null;
        try {
            List<byte[]> list = PdfUtility.splitFile(pdfFile, pagesPerFile, fromPage, toPage);
            if (list != null) {
                String length = String.valueOf(String.valueOf(list.size()).length());
                for (int i = 0; i < list.size(); i++) {
                    try {
                        String outFile = outputPath + fileName + String.format("%0" + length + "d", i + 1) + ".pdf";
                        byte[] file = list.get(i);
                        fos = new FileOutputStream(FilenameUtils.getBaseName(outFile));
                        fos.write(file);
                        fos.flush();
                    }
                    finally {
                        safeCloseOutputStream(fos);
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
        finally {
            safeCloseOutputStream(fos);
        }
    }

    private static void safeCloseOutputStream(final OutputStream os) {
        if (os != null) {
            try {
                os.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * PDF 併檔
     *
     * @param file1 欲合併的檔案 1
     * @param file2 欲合併的檔案 2
     * @return 以兩個檔案合併而成之新 PDF 檔案<br>
     * 若傳入的 <code>file1</code> 為 <code>null</code>，則回傳的結果是 <code>file2</code><br>
     * 若傳入的 <code>file2</code> 為 <code>null</code>，則回傳的結果是 <code>file1</code><br>
     * 若傳入的 <code>file1</code> 及 <code>file2</code> 皆為 <code>null</code>，則回傳的結果是 <code>null</code><br>
     * @throws ReportException
     */
    public static byte[] mergeFile(byte[] file1, byte[] file2) throws ReportException {
        try {
            if (file1 == null && file2 == null)
                return null;
            else if (file1 == null)
                return file2;
            else if (file2 == null)
                return file1;

            PdfReader readerFile1 = new PdfReader(file1);
            PdfReader readerFile2 = new PdfReader(file2);
            int n1 = readerFile1.getNumberOfPages();
            int n2 = readerFile2.getNumberOfPages();
            return mergeFile(file1, file2, 1, n1, 1, n2);
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 併檔
     *
     * @param file1         欲合併的檔案 1
     * @param file2         欲合併的檔案 2
     * @param fromFile1Page 開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @param fromFile2Page 開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @return 以指定頁數起算，合併而成之新 PDF 檔案<br>
     * 若傳入的 <code>file1</code> 為 <code>null</code>，則回傳的結果是 <code>file2</code><br>
     * 若傳入的 <code>file2</code> 為 <code>null</code>，則回傳的結果是 <code>file1</code><br>
     * 若傳入的 <code>file1</code> 及 <code>file2</code> 皆為 <code>null</code>，則回傳的結果是 <code>null</code><br>
     * @throws ReportException
     */
    public static byte[] mergeFile(byte[] file1, byte[] file2, int fromFile1Page, int fromFile2Page) throws ReportException {
        try {
            if (file1 == null && file2 == null)
                return null;
            else if (file1 == null)
                return file2;
            else if (file2 == null)
                return file1;

            PdfReader readerFile1 = new PdfReader(file1);
            PdfReader readerFile2 = new PdfReader(file2);
            int n1 = readerFile1.getNumberOfPages();
            int n2 = readerFile2.getNumberOfPages();
            return mergeFile(file1, file2, fromFile1Page, n1, fromFile2Page, n2);
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

    /**
     * PDF 併檔
     *
     * @param file1         欲合併的檔案 1
     * @param file2         欲合併的檔案 2
     * @param fromFile1Page 開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @param toFile1Page   結束頁數，設為 <code>-1</code> 表檔案的最後一頁；若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是結束頁數
     * @param fromFile2Page 開始頁數，若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是開始頁數
     * @param toFile2Page   結束頁數，設為 <code>-1</code> 表檔案的最後一頁；若大於 PDF 檔的頁數，則會以 PDF 檔的頁數當成是結束頁數
     * @return 以指定頁數合併而成之新 PDF 檔案<br>
     * 若傳入的 <code>file1</code> 為 <code>null</code>，則回傳的結果是 <code>file2</code><br>
     * 若傳入的 <code>file2</code> 為 <code>null</code>，則回傳的結果是 <code>file1</code><br>
     * 若傳入的 <code>file1</code> 及 <code>file2</code> 皆為 <code>null</code>，則回傳的結果是 <code>null</code><br>
     * @throws ReportException
     */
    public static byte[] mergeFile(byte[] file1, byte[] file2, int fromFile1Page, int toFile1Page, int fromFile2Page, int toFile2Page) throws ReportException {
        try {
            if (file1 == null && file2 == null)
                return null;
            else if (file1 == null)
                return file2;
            else if (file2 == null)
                return file1;

            ByteArrayOutputStream outFile = new ByteArrayOutputStream();

            PdfReader readerFile1 = new PdfReader(file1);
            PdfReader readerFile2 = new PdfReader(file2);
            Document document = new Document(readerFile1.getPageSizeWithRotation(fromFile1Page));

            PdfSmartCopy copy = new PdfSmartCopy(document, outFile);

            int n = readerFile1.getNumberOfPages();

            if (fromFile1Page <= 0)
                fromFile1Page = 1;

            if (toFile1Page <= 0)
                toFile1Page = n;

            if (fromFile1Page > n)
                fromFile1Page = n;

            if (toFile1Page > n)
                toFile1Page = n;

            int temp;

            if (fromFile1Page > toFile1Page) {
                temp = fromFile1Page;
                fromFile1Page = toFile1Page;
                toFile1Page = temp;
            }

            n = readerFile2.getNumberOfPages();

            if (fromFile2Page <= 0)
                fromFile2Page = 1;

            if (toFile2Page <= 0)
                toFile2Page = n;

            if (fromFile2Page > n)
                fromFile2Page = n;

            if (toFile2Page > n)
                toFile2Page = n;

            if (fromFile2Page > toFile2Page) {
                temp = fromFile2Page;
                fromFile2Page = toFile2Page;
                toFile2Page = temp;
            }

            document.open();

            for (int i = fromFile1Page; i <= toFile1Page; i++) {
                PdfImportedPage page = copy.getImportedPage(readerFile1, i);
                copy.addPage(page);
            }

            for (int i = fromFile2Page; i <= toFile2Page; i++) {
                PdfImportedPage page = copy.getImportedPage(readerFile2, i);
                copy.addPage(page);
            }

            document.close();

            return outFile.toByteArray();
        }
        catch (Exception e) {
            throw new ReportException(e);
        }
    }

}
