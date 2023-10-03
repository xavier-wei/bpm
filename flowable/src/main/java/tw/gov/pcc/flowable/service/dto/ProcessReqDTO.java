package tw.gov.pcc.flowable.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.HashMap;


@Data
public class ProcessReqDTO {
    @NotNull
    private String formName; // 表單名稱

    private HashMap<String, Object> variables;


}
