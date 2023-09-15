package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 線上報名Case
 * @author Weith
 */
@Data
public class Eip04w030Case implements Serializable {
    private static final long serialVersionUID = 32662991313020168L;

    private String orformno;

    private List<RegCase> regList;

    private List<RegCase> reghisList;

    private ContentCase regContent;

    private String filename;

    @Data
    public static class RegCase {
        private String orformno;
        private String regresult;
        private String topicname;
        private String status;
        private String regresultVal;
        private String statusVal;
        private boolean isfull;
    }

    @Data
    public static class ContentCase {
        private String regisqual;
        private String coursecode;
        private String classcode;
        private String topicname;
        private String address;
        private String regisdt;
        private String prodt;
        private String classhours;
        private String allowappnum;
        private String actualappnum;
        private String topicdesc;
        private String organizer;
        private String contacter;
        private String contactnum;
        private String fax;
        private String email;
        private String allowappway;
        private String ismeals;

        private String files;
    }
}
