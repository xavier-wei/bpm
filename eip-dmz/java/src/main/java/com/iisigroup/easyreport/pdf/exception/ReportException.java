package com.iisigroup.easyreport.pdf.exception;

/**
 * Easy Report PDF 的 Base Exception
 * 
 * @author Goston
 */
public class ReportException extends RuntimeException {

    private static final long serialVersionUID = 7835598168212047034L;

    public ReportException() {
        super();
    }

    public ReportException(Exception ex) {
        super(ex);
    }

    public ReportException(String message) {
        super(message);
    }

}
