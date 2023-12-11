package tw.gov.pcc.eip.adm.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Users;
import tw.gov.pcc.eip.domain.User_auth_dept;

/**
 * 
 * @author ivan
 */
@Data
@NoArgsConstructor
public class Eip00w300Case implements Serializable {

	private static final long serialVersionUID = 1L;

	public interface Update {
		// validation group marker interface
	}
	private String user_id;
	
	private String user_name;

	private String dept_id;
	
	private String dept_name;
	
	private List<User_auth_dept> user_auth_deptList;
	
	private User_auth_dept queryUser_auth_dept;

	private List<Depts> deptList;

	private List<Users> userList;

	@Data
	public static class Option {

		private String codeno;

		private String codename;
	}
	private String unitOption; // *連絡單位： 請選擇

	private List<Option> unitOptions = new ArrayList<>(); // *指定人員單位

	private List<Option> personOptions = new ArrayList<>(); // *指定人員

}
