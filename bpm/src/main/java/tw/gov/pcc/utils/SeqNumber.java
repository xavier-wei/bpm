package tw.gov.pcc.utils;

import java.time.LocalDate;

public class SeqNumber {

    // 傳入參數可接受 null 或"11201-0001" 回傳 新流水號 11201-0002如果跨月了則會回傳112(當月)-0001
    public static String getNewSeq(String lastFormId) {
        LocalDate localDate = LocalDate.now();
        String year = String.valueOf(localDate.getYear() - 1911);
        String mon = String.valueOf(localDate.getMonthValue()).length() > 1 ? String.valueOf(localDate.getMonthValue()) : "0" + localDate.getMonthValue();
        if (lastFormId != null) {
            String[] lastFormIdFragment = lastFormId.split("-");
            String lastSeq = lastFormIdFragment[2];
            String lastSeqYYMM = lastFormIdFragment[1];
            if (lastSeqYYMM.equals(year + mon)) {
                int num = Integer.parseInt(lastSeq) + 1;
                String newSeq = String.format("%04d", num);
                return lastSeqYYMM +"-"+ newSeq;
            }
        }
        return  year + mon+ "-0001";
    }
}
