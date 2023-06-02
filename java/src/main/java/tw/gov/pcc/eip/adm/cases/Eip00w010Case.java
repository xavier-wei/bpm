package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.User_roles;
import tw.gov.pcc.eip.domain.Users;

/**
 * 
 * @ivan 
 */
@Data
@NoArgsConstructor
public class Eip00w010Case implements Serializable {

	private static final long serialVersionUID = 1L;

	List<Users> userList;

	private String user_id;
	
	private List<String> selectedUserid; 
}
