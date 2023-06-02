package tw.gov.pcc.eip.common.cases;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 參數代號維護作業
 *
 * @author vita
 */
@Data
@NoArgsConstructor
public class Eipib0w050Case implements Serializable {
    private static final long serialVersionUID = 8618682968734864243L;
    public List<String> delete;//是否刪除
    private String codekind;//主代號類別-原本
    private String codeno;//主代號-原本
    private String afcodekind;//主代號類別-改後
    private String afcodeno;//主代號-改後
    private boolean doUpdate;//做修改
    private String scodekind;//副代號類別
    private String scodeno;//副代號
    private String codename;//主代號名稱
    private String staff;//異動者代號
    private LocalDateTime prcdat;//異動日期時間
    private String remark;//說明
    private List<Eipib0w050OptionCase> OptionList = new ArrayList<>();
    private List<Eipib0w050OptionCase> DetailList = new ArrayList<>();
}
