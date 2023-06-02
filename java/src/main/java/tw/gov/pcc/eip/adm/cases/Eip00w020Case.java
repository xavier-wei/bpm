package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
public class Eip00w020Case implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid;//員工編號
	private String deptid;//部門代號
	private List<Users> userList;
	
	private String userStatus;
	private Users users;
	
	
	public void settingCase(Users users) {
		this.userid = users.getUser_id();
		this.deptid = users.getDept_id();
		this.userStatus = users.getAcnt_is_valid();
	}
	
}
