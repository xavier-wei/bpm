package tw.gov.pcc.utils;

import java.time.LocalDate;

public class FlowNumber {

    public  String getFlowNum(String lastFlowNum) {
        LocalDate localDate = LocalDate.now();
        String year = String.valueOf(localDate.getYear()-1911);
        String mon = String.valueOf(localDate.getMonthValue()).length()>1? String.valueOf(localDate.getMonthValue()) :"0"+ localDate.getMonthValue();
        if (lastFlowNum != null) {
            String lastFlowNumYYMM = lastFlowNum.substring(0,5);
            if (lastFlowNumYYMM.equals(year + mon)) {
                int num= Integer.parseInt(lastFlowNum.substring(5))+1;
                String flowNumber = String.valueOf(num);
                switch (flowNumber.length()) {
                    case 1:
                        flowNumber = "000" + flowNumber;
                        break;
                    case 2:
                        flowNumber = "00" + flowNumber;
                        break;
                    case 3:
                        flowNumber = "0" + flowNumber;
                        break;
                    case 4:
                        break;
                }

                return lastFlowNumYYMM + flowNumber;
            }else {
                return year + mon + "0001";
            }
        }else {
            return year + mon + "0001";
        }
    }
}
