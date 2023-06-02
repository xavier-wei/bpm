package tw.gov.pcc.eip.framework.spring.support;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;
import tw.gov.pcc.eip.util.HttpUtility;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * 用於 Controller 回傳檔案用
 *
 * @author Goston
 */
public class FileOutputView extends AbstractView {
    /**
     * PDF 檔的預設 ContentType
     */
    public static final String PDF_FILE = "application/pdf";
    /**
     * Excel 檔的預設 ContentType
     */
    public static final String EXCEL_FILE = "application/vnd.ms-excel";
    /**
     * 未知檔案類型時的預設 ContentType
     */
    public static final String GENERAL_FILE = "application/octet-stream";
    public static final String ODS_FILE = " application/vnd.oasis.opendocument.spreadsheet";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileOutputView.class);
    private final ByteArrayOutputStream baoOutput;
    private final String filename;
    private final String contentType;

    /**
     * Constructor
     *
     * @param baoOutput   含檔案內容的 ByteArrayOutputStream
     * @param filename    檔名 (直接傳入即可，不需依不同瀏覽器進行相關轉碼等動作)
     * @param contentType 檔案的 ContentType
     */
    public FileOutputView(ByteArrayOutputStream baoOutput, String filename, String contentType) {
        this.baoOutput = baoOutput;
        this.filename = filename;
        this.contentType = contentType;
    }

    @Override
    @Deprecated
    protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 設定回傳回 Client 端之檔案大小, 若不設定,
        // 若 User 使用的瀏覽器為 IE 則可能收到空白的畫面
        response.setHeader("Content-disposition", "attachment; filename=\"" + FilenameUtils.normalize(HttpUtility.getFilenameByBrowser(StringUtils.defaultString(filename, ""))) + "\"");
        response.setContentType(contentType); // 設定MIME
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentLength(baoOutput.size());

        // Flush byte array to servlet output stream.
        ServletOutputStream out = response.getOutputStream();
        try {
            IOUtils.write(baoOutput.toByteArray(), out);
            baoOutput.writeTo(out);
            out.flush();
        } finally {
            IOUtils.closeQuietly(out);
        }

        // Flush to HTTP response.
        //writeToResponse(response, baoOutput);
    }

}
