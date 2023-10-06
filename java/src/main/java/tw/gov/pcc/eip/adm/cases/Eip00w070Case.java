package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Roles;
import tw.gov.pcc.eip.domain.Users;

/**
 * 
 * @ivan 
 */
@Data
@NoArgsConstructor
public class Eip00w070Case implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Roles> rolesList;
	private String role_id;
	private String role_desc;	
	private List<Users> usersList;
	private List<Depts> deptsList;
	private String dept;

    private String item_id;
    private String item_name;
    private String hyperlink;
    private List<String> selectedIdlist;
}
