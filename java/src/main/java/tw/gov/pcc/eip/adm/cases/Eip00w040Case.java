package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Depts;

/**
 * 
 * @author ivan
 */
@Data
@NoArgsConstructor
public class Eip00w040Case implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dept_id;
	
	private String dept_name;
	
	private String dept_desc;
	
	private List<Depts> deptList;
	
	private Depts queryDepts;
}
