package tw.gov.pcc.eip.common.cases;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 線上報名列表-報名審核/時數認證共用Case
 * @author Weith
 */
@Data
public class Eip04w020VerifyCase implements Serializable {
    private static final long serialVersionUID = 3748203724922921123L;

    private String seqno;

    private String regisname;

    private String regisphone;

    private String regisemail;

    private String company;

    private String dept;

    private String isPay;

    private String isPass;

    private String isNotify;

    private String certihours;

    private List<String> checkList;

    private String selAllmk;

}
