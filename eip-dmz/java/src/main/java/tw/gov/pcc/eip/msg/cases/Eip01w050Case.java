package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 輿情專區
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w050Case implements Serializable {

    private static final long serialVersionUID = -6209324226632288263L;

    private List<Eip01wPopCase> qryList;

    private String fseq;

    private String seq;

    private String filename;

    private String subject;

}
