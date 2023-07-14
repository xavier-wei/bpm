package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import tw.gov.pcc.eip.framework.validation.RequiredString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 會議室參數維護
 * @author 2207003
 */
@Data
public class Eip06w050Case implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> meetingCodeList;//畫面所選表單編號列表

    @RequiredString(label = "類別")
    private String  itemTyp; //物件類別  A：餐點  B：物品  D：預約天數  F：會議室  FX：會議室(禁用)
    private Map<String, String> itemTypMap; //物件類別下拉式選單內容

    @RequiredString(label = "編號")
    private String itemId;
    @RequiredString(label = "名稱")
    private String itemName;
    private Integer qty;

    public String mode;//A、新增 U修改 Q查詢畫面


    @Data
    public static class MeetingCodeCase {
        private String  itemTyp;
        private String itemId;
        private String itemName;
        private Integer qty;
    }

    private List<MeetingCodeCase> meetingCodeCaseList = new ArrayList<>();

}
