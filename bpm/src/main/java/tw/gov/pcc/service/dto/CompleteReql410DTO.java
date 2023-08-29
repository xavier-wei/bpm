package tw.gov.pcc.service.dto;

import java.util.HashMap;
import java.util.List;

public class CompleteReql410DTO {


    private BpmIsmsL410DTO bpmIsmsL410DTO;

//    hrSys-人事差勤系統
//    adSys-ad系統帳號
//    meetingRoom-會議室管理系統
//    odAys-公文系統帳號
//    emailSys-電子郵件帳號
//    pccWww-全球資訊網帳號
//    pccHome-會內資訊網站帳號
//    pccPis-政府電子採購網帳號
//    otherSys1-其他系統1帳號
//    otherSys2-其他系統2帳號
//    otherSys3-其他系統3帳號
    private List<HashMap<String, HashMap<String, Object>>> variables;

    public BpmIsmsL410DTO getBpmIsmsL410DTO() {
        return bpmIsmsL410DTO;
    }

    public void setBpmIsmsL410DTO(BpmIsmsL410DTO bpmIsmsL410DTO) {
        this.bpmIsmsL410DTO = bpmIsmsL410DTO;
    }

    public List<HashMap<String, HashMap<String, Object>>> getVariables() {
        return variables;
    }

    public void setVariables(List<HashMap<String, HashMap<String, Object>>> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "CompleteReql410DTO{" +
            "bpmIsmsL410DTO=" + bpmIsmsL410DTO +
            ", variables=" + variables +
            '}';
    }
}
