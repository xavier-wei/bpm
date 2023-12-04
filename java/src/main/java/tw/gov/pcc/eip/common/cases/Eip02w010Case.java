package tw.gov.pcc.eip.common.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通訊錄查詢
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip02w010Case implements Serializable {

    private static final long serialVersionUID = -2803114521734202911L;

    private String user_name; // 姓名
    private String dept_id; // 部門代號
    private String titlename; // 職稱
    private String user_id; // 員工編號
    private String user_ename; // 英名姓名
    private String email; // 電子信箱

    private List<Option> dept = new ArrayList<>(); // 部門
    private List<Option> title = new ArrayList<>(); // 職稱

    @Data
    public static class Option {

        private String codeno;

        private String codename;
    }

    private List<addressBook> qryList;

    /** 通訊錄 */
    @Data
    public static class addressBook {
        private String user_id; // 員工編號
        private String user_name; // 姓名
        private String ename; // 英名姓名
        private String dept_id; // 部門代號
        private String dept_name; // 部門名稱
        private String orgname; // 機關名稱
        private String titlename; // 職稱
        private String email; // 電子信箱
        private String tel1; // 電話
        private String tel2; // 分機
    }

    private String on_off; // 進階查詢的開關
    private String groupStr; // 若查詢結果為同一部門時顯示
    private boolean checkAll = false; // 全選
}
