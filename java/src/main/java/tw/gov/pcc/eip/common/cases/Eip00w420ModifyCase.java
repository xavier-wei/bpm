package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.framework.validation.NotEmpty;
import tw.gov.pcc.eip.framework.validation.RequiredInteger;
import tw.gov.pcc.eip.framework.validation.RequiredString;
import tw.gov.pcc.eip.util.DateUtility;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 線上報名列表-新增修改Case
 * @author Weith
 */
@Data
public class Eip00w420ModifyCase implements Serializable {
    private static final long serialVersionUID = 438472380298374902L;

    @RequiredString(label = "線上報名類別")
    private String orccode;

    @RequiredString(label = "課程類別代碼")
    private String courseclacode;

    private String coursecode;

    private String classcode;

    @RequiredString(label = "課程類別代碼")
    private String period;

    @RequiredString(label = "名稱")
    private String topicname;

    @ChineseDate(label = "報名開始日期")
    @RequiredString(label = "報名開始日期")
    private String regisfmdt;

    public String getRegfyyyymmdd() {
        return DateUtility.changeDateTypeToWestDate(this.regisfmdt);
    }

    public String getRegeyyyymmdd() {
        return DateUtility.changeDateTypeToWestDate(this.regisendt);
    }

    public String getProfyyyymmdd() { return DateUtility.changeDateTypeToWestDate(this.profmdt); }

    public String getProeyyyymmdd() {
        return DateUtility.changeDateTypeToWestDate(this.proendt);
    }

    @ChineseDate(label = "報名結束日期")
    @RequiredString(label = "報名結束日期")
    private String regisendt;

    @RequiredString(label = "報名開始時間(時)")
    private String regisfmdtHour;

    @RequiredString(label = "報名開始時間(分)")
    private String regisfmdtMinute;

    @RequiredString(label = "報名結束時間(時)")
    private String regisendtHour;

    @RequiredString(label = "報名結束時間(分)")
    private String regisendtMinute;

    @RequiredString(label = "主辦單位")
    private String organizer;

    @RequiredString(label = "聯絡人")
    private String contacter;

    @RequiredString(label = "聯絡電話")
    private String contactnum;

    private String fax;

    private String email;

    @RequiredString(label = "地點")
    private String addres;

    @RequiredString(label = "上課縣市")
    private String country;

    @ChineseDate(label = "辦理開始日期")
    @RequiredString(label = "辦理開始日期")
    private String profmdt;

    @ChineseDate(label = "辦理結束日期")
    @RequiredString(label = "辦理結束日期")
    private String proendt;

    @RequiredString(label = "辦理開始時間(時)")
    private String profmdtHour;

    @RequiredString(label = "辦理開始時間(分)")
    private String profmdtMinute;

    @RequiredString(label = "辦理開始時間(時)")
    private String proendtHour;

    @RequiredString(label = "辦理開始時間(分)")
    private String proendtMinute;

    @RequiredString(label = "接受報名人數")
    private String acceptappnum;

    @RequiredString(label = "允許報名人數")
    private String allowappnum;

    private List<String> allowappway;

    private String fee;

    private String account;

    private String ismeals;

    @RequiredString(label = "上課時數")
    private String classhours;

    private String classhoursUnit;

    @RequiredString(label = "認證時數")
    private String certihours;

    private String certihoursType1;

    private String certihoursType2;

    private String certihoursType3;

    private String lecturercode;

    private String passmsg;

    private String rejectmst;

    private List<String> regisqual;

    private String topicdesc;

    private String remark;

    private String subject;

    private String mode;

    private String orformno;

    private MultipartFile[] files;

    @AssertTrue(message = "「報名資格」為必填")
    private boolean isRegisqualEmpty() {
        if (CollectionUtils.isEmpty(getRegisqual())) {
            return false;
        }
        return true;
    }

    @AssertTrue(message = "「上課時數」需大於等於「認證時數」")
    private boolean isClasshoursGreaterCertihours() {
        // 如果其中一個欄位為NULL無法比較，讓其他驗證處理
        if (StringUtils.isBlank(getClasshours()) || StringUtils.isBlank(getCertihours())) {
            return true;
        }
        int hours = Integer.parseInt(getClasshours());
        int certihours = Arrays.stream(getCertihours().split(","))
                .mapToInt(s -> Integer.parseInt(s.substring(2)))
                .sum();
        if ("H".equals(getClasshoursUnit()) && hours < certihours) {
            return false;
        } else if ("D".equals(getClasshoursUnit()) && hours * 8 < certihours) {
            return false;
        } else if ("C".equals(getClasshoursUnit()) && hours * 18 < certihours) {
            return false;
        }
        return true;
    }

}
