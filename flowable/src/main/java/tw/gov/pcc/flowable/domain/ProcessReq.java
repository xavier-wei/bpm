package tw.gov.pcc.flowable.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.flowable.service.dto.ProcessReqDTO;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessReq {

    private String processKey;
    private HashMap<String, Object> variables;

    public ProcessReq(ProcessReqDTO dto) {
        this.processKey=ProcessEnum.getProcessKeyByKey(dto.getFormName());
        this.variables = dto.getVariables();
    }

}
