package tw.gov.pcc.eip.msg.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下載專區
 * 
 * @author vita
 *
 */
@Data
@NoArgsConstructor
public class Eip01w040Case implements Serializable {

    private static final long serialVersionUID = -4212326298581158962L;

    public List<Eip01wPopCase> qryList = null;

    public String treenode;

    public String path;

    public String keyword;

    public String key = "";

    private String fseq;

    private String seq;

    private String filename;

    private String subject;

}
