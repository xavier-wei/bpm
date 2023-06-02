package com.iisigroup.easyreport.pdf;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.security.ExternalDecryptionProcess;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.cert.Certificate;

/**
 * 此 Class 僅供 Easy Report 內部使用
 *
 * @author Goston
 */
public class EasyReportPdfReader extends PdfReader {

    public EasyReportPdfReader(byte[] file) throws IOException {
        super(file);
    }

    public EasyReportPdfReader(byte[] pdfIn, byte[] ownerPassword) throws IOException {
        super(pdfIn, ownerPassword);
    }

    public EasyReportPdfReader(String filename, Certificate certificate, Key certificateKey, String certificateKeyProvider) throws IOException {
        super(filename, certificate, certificateKey, certificateKeyProvider);
    }

    public EasyReportPdfReader(String filename, Certificate certificate, ExternalDecryptionProcess externalDecryptionProcess) throws IOException {
        super(filename, certificate, externalDecryptionProcess);
    }

    public EasyReportPdfReader(byte[] pdfIn, Certificate certificate, ExternalDecryptionProcess externalDecryptionProcess) throws IOException {
        super(pdfIn, certificate, externalDecryptionProcess);
    }

    public EasyReportPdfReader(InputStream inputStream, Certificate certificate, ExternalDecryptionProcess externalDecryptionProcess) throws IOException {
        super(inputStream, certificate, externalDecryptionProcess);
    }

    public EasyReportPdfReader(URL url) throws IOException {
        super(url);
    }

    public EasyReportPdfReader(URL url, byte[] ownerPassword) throws IOException {
        super(url, ownerPassword);
    }

    public EasyReportPdfReader(InputStream is, byte[] ownerPassword) throws IOException {
        super(is, ownerPassword);
    }

    public EasyReportPdfReader(InputStream is) throws IOException {
        super(is);
    }

    public EasyReportPdfReader(RandomAccessFileOrArray raf, byte[] ownerPassword) throws IOException {
        super(raf, ownerPassword);
    }

    public EasyReportPdfReader(RandomAccessFileOrArray raf, byte[] ownerPassword, boolean partial) throws IOException {
        super(raf, ownerPassword, partial);
    }

    public EasyReportPdfReader(PdfReader reader) {
        super(reader);
    }

    public EasyReportPdfReader(String filename) throws IOException {
        super(filename);
    }

    public EasyReportPdfReader(String filename, byte[] ownerPassword) throws IOException {
        super(filename, ownerPassword);
    }

    public EasyReportPdfReader(String filename, byte[] ownerPassword, boolean partial) throws IOException {
        super(filename, ownerPassword, partial);
    }

    public void decryptOnPurpose() {
        encrypted = false;
    }

}
