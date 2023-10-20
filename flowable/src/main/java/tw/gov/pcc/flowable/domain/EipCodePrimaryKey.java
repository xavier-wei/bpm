package tw.gov.pcc.flowable.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class EipCodePrimaryKey implements Serializable {
    private String codekind;
    private String codeno;
}
