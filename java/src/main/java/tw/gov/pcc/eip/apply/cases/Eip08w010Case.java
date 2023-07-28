package tw.gov.pcc.eip.apply.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Itemcode;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.domain.Users;

/**
 * 
 * @ivan 
 */
@Data
@NoArgsConstructor
public class Eip08w010Case implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Itemcode> mainKindList;
	
	private List<Itemcode> detailKindList;
	
	private List<Itemcode> resultKindList;
	
	private String mainkindno;
	
	private String mainkindname;
	
	private String detailkindno;
	
	private String addMainItemno;
	
	private String addMainItemname;
	
	private String addDetailItemno;
	
	private String addDetailItemname;

}
