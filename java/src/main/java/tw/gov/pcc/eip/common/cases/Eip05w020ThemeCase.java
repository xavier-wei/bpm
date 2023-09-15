package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tw.gov.pcc.common.util.DateUtil;
import tw.gov.pcc.eip.framework.validation.ChineseDate;
import tw.gov.pcc.eip.framework.validation.RequiredString;
import tw.gov.pcc.eip.util.DateUtility;

import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 意見調查主題列表-新增修改意見調查主題Case
 * @author Weith
 */
@Data
public class Eip05w020ThemeCase implements Serializable {
    private static final long serialVersionUID = 94375143983132377L;
    private String osformno;
    @RequiredString(label = "意見調查類別")
    private String osccode;
    @RequiredString(label = "主題名稱")
    private String topicname;
    @RequiredString(label = "開始時間")
    @ChineseDate(label = "開始時間")
    private String osfmdt;
    @RequiredString(label = "開始時間(時)")
    private String osfmdtHour;
    @RequiredString(label = "開始時間(分)")
    private String osfmdtMinute;
    private String fullosfmdt;
    @RequiredString(label = "結束時間")
    @ChineseDate(label = "結束時間")
    private String osendt;
    @RequiredString(label = "結束時間(時)")
    private String osendtHour;
    @RequiredString(label = "結束時間(分)")
    private String osendtMinute;
    private String fullosendt;
    @RequiredString(label = "說明")
    private String topicdesc;
    @RequiredString(label = "主辦單位")
    private String organizer;
    @RequiredString(label = "使用者填寫後送出之提示訊息")
    private String promptmsg;
    @RequiredString(label = "是否立即呈現投票統計結果")
    private String isdisstatic;
    @RequiredString(label = "是否限制一次作答")
    private String islimitone;
    @RequiredString(label = "是否匿名投票")
    private String isanonymous;
    private List<String> limitvote;
    private String mailsubject;//調查通知信件主旨
    private String mailmsg;//調查通知信件內容
    private String status;
    private String creuser;
    private String credt;
    private String upduser;
    private String upddt;
    private String mode;

    @AssertTrue(message = "「投票對象限制」為必填")
    private boolean isLimitvoteEmpty() {
        return !CollectionUtils.isEmpty(getLimitvote());
    }


    @AssertTrue(message = "「開始時間(時)」格式不正確(0~23)")
    public boolean isOsfmdtHourValid() {
        if (StringUtils.isNotBlank(this.osfmdtHour)) {
            int iHour = Integer.parseInt(this.osfmdtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「開始時間(分)」格式不正確(0~59)")
    public boolean isOsfmdtMinuteValid() {
        if (StringUtils.isNotBlank(this.osfmdtMinute)) {
            int iMinute = Integer.parseInt(this.osfmdtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「結束時間(時)」格式不正確(0~23)")
    public boolean isOsendtHourValid() {
        if (StringUtils.isNotBlank(this.osendtHour)) {
            int iHour = Integer.parseInt(this.osendtHour);
            return iHour <= 23;
        }
        return true;
    }

    @AssertTrue(message = "「結束時間(分)」格式不正確(0~59)")
    public boolean isOsendtMinuteValid() {
        if (StringUtils.isNotBlank(this.osendtMinute)) {
            int iMinute = Integer.parseInt(this.osendtMinute);
            return iMinute <= 59;
        }
        return true;
    }

    @AssertTrue(message = "「開始時間」需小於「結束時間」")
    private boolean isOsfmdtLessthanOsendt() {
        // 如果其中一個欄位為NULL無法比較，讓其他驗證處理
        if (StringUtils.isBlank(this.osfmdt) || StringUtils.isBlank(this.osendt)) {
            return true;
        }
        // 時、分皆合法才檢查日期起迄
        if (isOsfmdtHourValid() && isOsfmdtMinuteValid() && isOsendtHourValid() && isOsendtMinuteValid()) {
            LocalDateTime fmdt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.osfmdt) + this.osfmdtHour + this.osfmdtMinute);
            LocalDateTime endt = DateUtil.toLocalDateTime(DateUtility.changeDateTypeToWestDate(this.osendt) + this.osendtHour + this.osendtMinute);
            return !endt.isBefore(fmdt) && !endt.isEqual(fmdt);
        }
        return true;
    }

}
