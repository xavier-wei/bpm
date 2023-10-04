package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.framework.validation.*;
import tw.gov.pcc.eip.util.DateUtility;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 線上報名列表-新增修改Case
 * @author Weith
 */
@Data
public class Eip04w020ModifyCase implements Serializable {
    private static final long serialVersionUID = 438472380298374902L;

    @RequiredString(label = "線上報名類別")
    private String orccode;

    @RequiredString(label = "課程類別代碼")
    private String courseclacode;

    private String coursecode;

    private String classcode;

    @RequiredString(label = "期別")
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

    @RequiredString(label = "辦理結束時間(時)")
    private String proendtHour;

    @RequiredString(label = "辦理結束時間(分)")
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
                .mapToInt(s -> Integer.parseInt(StringUtils.substringAfter(s,"-").substring(2)))
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

    @AssertTrue(message = "「報名開始時間(時)」格式不正確(0~23)")
    private boolean isRegisfmdtHourValid() {
        if (StringUtils.isNotBlank(this.regisfmdtHour)) {
            int iHour = Integer.parseInt(this.regisfmdtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「報名開始時間(分)」格式不正確(0~59)")
    private boolean isRegisfmdtMinuteValid() {
        if (StringUtils.isNotBlank(this.regisfmdtMinute)) {
            int iMinute = Integer.parseInt(this.regisfmdtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「報名結束時間(時)」格式不正確(0~23)")
    private boolean isRegisendtHourValid() {
        if (StringUtils.isNotBlank(this.regisendtHour)) {
            int iHour = Integer.parseInt(this.regisendtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「報名結束時間(分)」格式不正確(0~59)")
    private boolean isRegisendtMinuteValid() {
        if (StringUtils.isNotBlank(this.regisendtMinute)) {
            int iMinute = Integer.parseInt(this.regisendtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「辦理開始時間(時)」格式不正確(0~23)")
    private boolean isProfmdtHourValid() {
        if (StringUtils.isNotBlank(this.profmdtHour)) {
            int iHour = Integer.parseInt(this.profmdtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「辦理開始時間(分)」格式不正確(0~59)")
    private boolean isProfmdtMinuteValid() {
        if (StringUtils.isNotBlank(this.profmdtMinute)) {
            int iMinute = Integer.parseInt(this.profmdtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「辦理結束時間(時)」格式不正確(0~23)")
    private boolean isProendtHourValid() {
        if (StringUtils.isNotBlank(this.proendtHour)) {
            int iHour = Integer.parseInt(this.proendtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「辦理結束時間(分)」格式不正確(0~59)")
    private boolean isProendtMinuteValid() {
        if (StringUtils.isNotBlank(this.proendtMinute)) {
            int iMinute = Integer.parseInt(this.proendtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「報名開始時間」需小於「報名結束時間」")
    private boolean isRegisfmdtLessthanRegisendt() {
        // 如果其中一個欄位為NULL無法比較，讓其他驗證處理
        if (StringUtils.isBlank(this.regisfmdt) || StringUtils.isBlank(this.getRegisendt())) {
            return true;
        }
        if (isRegisfmdtHourValid() && isRegisfmdtMinuteValid() && isRegisendtHourValid() && isRegisendtMinuteValid()) {
            LocalDateTime fmdt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.regisfmdt) + this.regisfmdtHour + this.regisfmdtMinute);
            LocalDateTime endt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.regisendt) + this.regisendtHour + this.regisendtMinute);
            if (endt.isBefore(fmdt) || endt.isEqual(fmdt)) {
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message = "「辦理開始時間」需小於「辦理結束時間」")
    private boolean isProfmdtLessthanProendt() {
        // 如果其中一個欄位為NULL無法比較，讓其他驗證處理
        if (StringUtils.isBlank(this.profmdt) || StringUtils.isBlank(this.proendt)) {
            return true;
        }
        if (isProfmdtHourValid() && isProfmdtMinuteValid() && isProendtHourValid() && isProendtMinuteValid()) {
            LocalDateTime fmdt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.profmdt) + this.profmdtHour + this.profmdtMinute);
            LocalDateTime endt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.proendt) + this.proendtHour + this.proendtMinute);
            if (endt.isBefore(fmdt) || endt.isEqual(fmdt)) {
                return false;
            }
        }
        return true;
    }

    @AssertTrue(message = "僅允許上傳doc、docx、xls、xlsx、pdf及csv檔，請重新上傳")
    private boolean isValidExtension() {
        List<String> legalExtension = Arrays.asList("doc","docx","xlsx","xls","pdf","csv");
        boolean isAttach = false;
        for (MultipartFile file:getFiles()) {
            if (StringUtils.isNotBlank(file.getOriginalFilename())) {
                isAttach = true;
                break;
            }
        }
        if (isAttach) {
            for (MultipartFile file:getFiles()) {
                if (StringUtils.isNotBlank(file.getOriginalFilename()) && !legalExtension.contains(StringUtils.substringAfter(file.getOriginalFilename(),"."))) {
                    return false;
                }
            }
        }
        return true;
    }

}
