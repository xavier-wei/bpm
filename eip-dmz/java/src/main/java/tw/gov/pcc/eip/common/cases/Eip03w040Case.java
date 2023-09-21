package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 重要列管事項_件數統計
 * @author 2201009
 */
@Data
@NoArgsConstructor
public class Eip03w040Case implements Serializable {

//    初始頁
    private String trkObj; //列管對象(處室)
    private String cnt; // 案件數
    private String cnt_cls; // 已結案件數統計
    private String cnt_opn; // 未結案數統計
    private String status; // 搜尋案件狀態
    private List<Eip03w040Case> eip03w040CaseList = new ArrayList<>();
    private String dept_name; // 處室名稱

//    查詢表頁
    private String trkId; //列管編號
    private String trkFrom; //交辦來源
    private String trkCont; //列管事項
    private String prcSts; //辦理情形
    private String rptCont; //
    private String endDt; //列管迄日
    private LocalDateTime supDt; //解列日期
    private String fmtSupDt;
    private String supAgree; //
    private List<Eip03w040Case> eip03w040CaseDetailList = new ArrayList<>();

//    報表
    private ByteArrayOutputStream baos; // 報表內容
}

