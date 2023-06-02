package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Systems;

/**
 * 
 * @author 
 */
@Data
@NoArgsConstructor
public class Eip00w030Case implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sys_id;
	
	private String sys_name;

	private String url;

	private String sort_order;
	
	private Systems querySystems;
	
	private List<Systems> systemLits;
	
}
