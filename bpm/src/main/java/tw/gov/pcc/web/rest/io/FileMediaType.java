package tw.gov.pcc.web.rest.io;

import org.springframework.http.MediaType;

/**
 * 檢核用的 MediaType。
 */
public class FileMediaType extends MediaType {

    public FileMediaType(String type, String subtype) {
        super(type, subtype);
    }

    /**
     * Public constant media type for {@code application/pdf}.
     */
    public static final MediaType APPLICATION_PDF;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_PDF}.
     */
    public static final String APPLICATION_PDF_VALUE = "application/pdf";

    /**
     * Public constant media type for {@code application/msword}.
     */
    public static final MediaType APPLICATION_DOC;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_DOC}.
     */
    public static final String APPLICATION_DOC_VALUE = "application/msword";

    /**
     * Public constant media type for {@code application/vnd.openxmlformats-officedocument.wordprocessingml.document}.
     */
    public static final MediaType APPLICATION_DOCX;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_DOCX}.
     */
    public static final String APPLICATION_DOCX_VALUE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    /**
     * Public constant media type for {@code application/vnd.oasis.opendocument.text}.
     */
    public static final MediaType APPLICATION_ODT;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_ODT}.
     */
    public static final String APPLICATION_ODT_VALUE = "application/vnd.oasis.opendocument.text";

    /**
     * Public constant media type for {@code application/vnd.ms-excel}.
     */
    public static final MediaType APPLICATION_XLS;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_XLS}.
     */
    public static final String APPLICATION_XLS_VALUE = "application/vnd.ms-excel";

    /**
     * Public constant media type for {@code application/vnd.openxmlformats-officedocument.spreadsheetml.sheet}.
     */
    public static final MediaType APPLICATION_XLSX;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_XLSX}.
     */
    public static final String APPLICATION_XLSX_VALUE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Public constant media type for {@code application/vnd.oasis.opendocument.spreadsheet}.
     */
    public static final MediaType APPLICATION_ODS;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_ODS}.
     */
    public static final String APPLICATION_ODS_VALUE = "application/vnd.oasis.opendocument.spreadsheet";

    /**
     * Public constant media type for {@code application/zip}.
     */
    public static final MediaType APPLICATION_ZIP;

    /**
     * A String equivalent of {@link FileMediaType#APPLICATION_ZIP}.
     */
    public static final String APPLICATION_ZIP_VALUE = "application/zip";

    /**
     * Public constant media type for {@code image/jpeg}.
     */
    public static final MediaType IMAGE_JPEG;

    /**
     * A String equivalent of {@link FileMediaType#IMAGE_JPEG}.
     */
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";

    /**
     * Public constant media type for {@code image/png}.
     */
    public static final MediaType IMAGE_PNG;

    /**
     * A String equivalent of {@link FileMediaType#IMAGE_PNG}.
     */
    public static final String IMAGE_PNG_VALUE = "image/png";

    /**
     * Public constant media type for {@code image/gif}.
     */
    public static final MediaType IMAGE_GIF;

    /**
     * A String equivalent of {@link FileMediaType#IMAGE_PNG}.
     */
    public static final String IMAGE_GIF_VALUE = "image/gif";

    /**
     * Public constant media type for {@code video/mp4}.
     */
    public static final MediaType VIDEO_MP4;

    /**
     * A String equivalent of {@link FileMediaType#VIDEO_MP4}.
     */
    public static final String VIDEO_MP4_VALUE = "video/mp4";

    static {
        APPLICATION_PDF = MediaType.valueOf(APPLICATION_PDF_VALUE);
        APPLICATION_DOC = MediaType.valueOf(APPLICATION_DOC_VALUE);
        APPLICATION_DOCX = MediaType.valueOf(APPLICATION_DOCX_VALUE);
        APPLICATION_ODT = MediaType.valueOf(APPLICATION_ODT_VALUE);
        APPLICATION_XLS = MediaType.valueOf(APPLICATION_XLS_VALUE);
        APPLICATION_XLSX = MediaType.valueOf(APPLICATION_XLSX_VALUE);
        APPLICATION_ODS = MediaType.valueOf(APPLICATION_ODS_VALUE);
        APPLICATION_ZIP = MediaType.valueOf(APPLICATION_ZIP_VALUE);
        IMAGE_JPEG = MediaType.valueOf(IMAGE_JPEG_VALUE);
        IMAGE_PNG = MediaType.valueOf(IMAGE_PNG_VALUE);
        IMAGE_GIF = MediaType.valueOf(IMAGE_GIF_VALUE);
        VIDEO_MP4 = MediaType.valueOf(VIDEO_MP4_VALUE);
    }
}
