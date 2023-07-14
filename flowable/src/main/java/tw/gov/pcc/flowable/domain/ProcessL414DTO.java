package tw.gov.pcc.flowable.domain;

import lombok.Data;

import java.util.HashSet;

@Data
public class ProcessL414DTO {

    private String writer; // 填表人
    private String applier; // 申請人
    private String sectionChief; // 科長
    private String director; // 主管
    private HashSet<String> infoGroup; // 資推小組
    private String seniorTechSpecialist; //簡任技正/科長
    private HashSet<String> serverRoomOperator; // 機房操作人員
    private HashSet<String> reviewStaff; // 覆核人員
    private HashSet<String> serverRoomManager; // 機房主管

}
