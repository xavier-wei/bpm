package tw.gov.pcc.service.dto;

import java.io.Serializable;
import java.util.HashMap;

public class ProcessReqDTO implements Serializable {
    private String formName;
    private HashMap<String, Object> variables;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public HashMap<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Object> variables) {
        this.variables = variables;
    }

}
