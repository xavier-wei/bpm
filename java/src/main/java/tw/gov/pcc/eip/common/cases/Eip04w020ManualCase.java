package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.eip.framework.validation.*;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;

/**
 * 人工報名Case
 * @author Weith
 */
@Data
public class Eip04w020ManualCase implements Serializable {
    private static final long serialVersionUID = 12020329943266028L;

    public interface Single {
        // validation group marker interface
    }

    public interface Batch {
        // validation group marker interface
    }

    private String orformno;
    private String topicname;
    private String regType;
    @RequiredString(label = "報名方式", groups = {Single.class, Batch.class})
    private String regisway;
    private String adaccount;
    @RequiredString(label = "姓名", groups = {Single.class})
    private String regisname;
    @RequiredString(label = "身分證字號/護照號碼", groups = {Single.class})
    private String regisidn;
    @RequiredString(label = "性別", groups = {Single.class})
    private String regissex;
    @RequiredString(label = "生日", groups = {Single.class})
    @ChineseDate(label = "生日", groups = {Single.class})
    private String regisbrth;
    @RequiredString(label = "E-mail", groups = {Single.class})
    @EmailAddress(label = "E-mail", groups = {Single.class})
    private String regisemail;
    @RequiredString(label = "聯絡電話", groups = {Single.class})
    private String regisphone;
    @RequiredString(label = "職稱", groups = {Single.class})
    private String jogtitle;
    @RequiredString(label = "學分學位", groups = {Single.class})
    private String degreen;
    @RequiredString(label = "部門", groups = {Single.class})
    private String dept;
    private String regisaddres;
    @RequiredString(label = "用餐狀況", groups = {Single.class})
    private String mealstatus;
    private MultipartFile file;
    private String mode;

    @AssertTrue(message = "僅允許上傳csv檔!", groups = {Batch.class})
    private boolean isValidExtension() {
        if (StringUtils.isNotBlank(getFile().getOriginalFilename()) && !StringUtils.endsWith(getFile().getOriginalFilename(), "csv")) {
            return false;
        }
        return true;
    }
}
