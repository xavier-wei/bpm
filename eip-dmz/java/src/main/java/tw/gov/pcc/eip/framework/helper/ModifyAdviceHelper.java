package tw.gov.pcc.eip.framework.helper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.util.ExceptionUtil;

import java.util.List;

/**
 * 針對LOG做處理(新增、修改、刪除)
 * 由於底層處理變數方式不一樣，配合分開處理
 * MMACCESSLG.IDN,
 * MMACCESSLG.UBNO,
 * APLOG.IDNO
 *
 * @author swho
 */
public class ModifyAdviceHelper {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ModifyAdviceHelper.class);

    /*
    依照順序找稽核欄位
     */
    public static String catchFieldString(String catchField, String pkField) {
        try {
            List<String> stringList = QueryAdviceHelper.ALL_STRING_LIST_MAP.get(catchField);
            if (CollectionUtils.isEmpty(stringList)) {
                return StringUtils.EMPTY;
            }
            for (String key : stringList) {
                String pick = pkField;
                String value = StringUtils.EMPTY;
                int len = StringUtils.length(key) + 2;
                int pos = StringUtils.indexOfIgnoreCase("," + pkField + "=", "," + key + "=");
                if (pos > -1) {
                    pick = StringUtils.substring("," + pick, pos + len);
                    pos = StringUtils.indexOfIgnoreCase(pick, ",");
                    if (pos == -1) {
                        value = pick;
                    } else {
                        value = StringUtils.substring(pick, 0, pos);
                    }
                }
                if (StringUtils.isNotBlank(value)) {
                    return value; //找到欄位並且有值才算數
                }
            }
        } catch (Exception e) {
            log.error("Modify log取值錯誤:" + ExceptionUtil.getStackTrace(e));
        }
        return StringUtils.EMPTY;
    }
}
