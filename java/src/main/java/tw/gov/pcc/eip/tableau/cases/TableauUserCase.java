package tw.gov.pcc.eip.tableau.cases;

import lombok.Data;
import lombok.NoArgsConstructor;
import tw.gov.pcc.eip.domain.Eipcode;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @Susan
 */
@Data
@NoArgsConstructor
public class TableauUserCase implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 使用者
	 */
	private String user;

	public interface Query {
	}
}
