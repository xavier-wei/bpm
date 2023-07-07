package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Eipcode;
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

	private List<Users> userList;
	private Users users;
	private List<Eipcode> titleidList;
	private List<Eipcode> deptList;
	
	private String user_id;//使用者代號
	private String dept_id;//部門代號
	private String email;//電子信箱
	private String emp_id;//員工編號
	private String user_name;//使用者姓名
	private String tel1;//連絡電話
	private String tel2;//分機
	private String title_id;//職稱代號
	private String line_token;
	private String userStatus;
	
	
	
	public void settingCase(Users users) {
		this.user_id = users.getUser_id();
		this.dept_id = users.getDept_id();
		this.email = users.getEmail();
		this.emp_id = users.getEmp_id();
		this.user_name = users.getUser_name();
		this.tel1 = users.getTel1();
		this.tel2 = users.getTel2();
		this.title_id = users.getTitle_id();
		this.line_token = users.getLine_token();
		this.userStatus = users.getAcnt_is_valid();
		this.users = users;
	}
	
}
