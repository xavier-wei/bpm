package tw.gov.pcc.flowable.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(schema = "dbo",name = "EIPCODE")
@IdClass(EipCodePrimaryKey.class)
public class EipCode implements Serializable {

    @Id
    private String codekind;
    @Id
    private String codeno;
    private String scodekind;
    private String scodeno;
    private String codename;
    private String staff;
    private Timestamp prcdat;
    private String remark;
}
