package tw.gov.pcc.utils;

import java.time.LocalDate;

public class SeqNumber {

    // 傳入參數可接受 null 或"112010001"
    public String getNewSeq(String lastFormId) {
        LocalDate localDate = LocalDate.now();
        String year = String.valueOf(localDate.getYear() - 1911);
        String mon = String.valueOf(localDate.getMonthValue()).length() > 1 ? String.valueOf(localDate.getMonthValue()) : "0" + localDate.getMonthValue();
        if (lastFormId != null) {
            String lastSeq = lastFormId.split("-")[1];
            String lastSeqYYMM = lastSeq.substring(0, 5);
            if (lastSeqYYMM.equals(year + mon)) {
                int num = Integer.parseInt(lastSeq.substring(5)) + 1;
                String newSeq = String.valueOf(num);
                switch (newSeq.length()) {
                    case 1:
                        newSeq = "000" + newSeq;
                        break;
                    case 2:
                        newSeq = "00" + newSeq;
                        break;
                    case 3:
                        newSeq = "0" + newSeq;
                        break;
                    case 4:
                        break;
                }

                return lastSeqYYMM + newSeq;
            } else {
                return year + mon + "0001";
            }
        } else {
            return year + mon + "0001";
        }
    }
}
