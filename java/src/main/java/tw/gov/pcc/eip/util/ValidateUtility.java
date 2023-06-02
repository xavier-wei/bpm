package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;

public class ValidateUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidateUtility.class);

    /**
     * 身分證號有效字元檢查
     *
     * @param idNo
     * @return
     */
    public static boolean isValidIdNoChar(String idNo) {
        if (StringUtils.length(idNo) != 10) return false;
        String sIDN = "ABCDEFGHJKLMNPQRSTUVXYWZIO";
        String sNUM = "0123456789";
        // 首碼不是英文字母
        if (StringUtils.indexOf(sIDN, idNo.charAt(0)) == -1) {
            return false;
        }
        // 第二碼不是 1 或 2
        if (!StringUtils.equals(idNo.substring(1, 2), "1") && !StringUtils.equals(idNo.substring(1, 2), "2")) {
            return false;
        }
        // 其餘各碼須為數字
        for (int i = 2; i < idNo.length(); i++) {
            if (StringUtils.indexOf(sNUM, idNo.charAt(i)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 郵局帳號檢核
     *
     * @param branch
     * @param account
     * @return
     */
    public static boolean isValidPayEeacc(String branch, String account) {
        boolean isValid = false;
        int[] val = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < account.length(); i++) {
            String tmpStr = StringUtils.substring(account, i, i + 1);
            val[i] = Integer.valueOf(StringUtils.isNotBlank(tmpStr) ? tmpStr : "0");
        }
        if (StringUtils.equals(branch, "7000010") && account.length() <= 8) {
            if (val[7] == ((11 - ((val[0] * 2 + val[1] * 3 + val[2] * 4 + val[3] * 5 + val[4] * 6 + val[5] * 7 + val[6] * 8) % 11)) % 10)) {
                isValid = true;
            }
        } else if (StringUtils.equals(branch, "7000021") && account.length() <= 14) {
            if (val[6] == ((11 - ((val[0] * 2 + val[1] * 3 + val[2] * 4 + val[3] * 5 + val[4] * 6 + val[5] * 7) % 11)) % 10)) {
                if (val[13] == ((11 - ((val[7] * 2 + val[8] * 3 + val[9] * 4 + val[10] * 5 + val[11] * 6 + val[12] * 7) % 11)) % 10)) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }
}
