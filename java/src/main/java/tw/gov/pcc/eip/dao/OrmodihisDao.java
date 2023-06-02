package tw.gov.pcc.eip.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import tw.gov.pcc.eip.domain.Ormodihis;

import java.util.List;

/**
 * 線上報名修改歷程檔Dao
 * @Author Weith
 */
public interface OrmodihisDao {

    public static final String TABLE_NAME = "ORMODIHIS";

    public Ormodihis findByPk(String orformno, String seqno);

    public int insertData(Ormodihis t);

    public int updateData(Ormodihis t, String orformno, String seqno);

    public int deleteData(Ormodihis t, String orformno, String seqno);

    public List<Ormodihis> getDataByOrformno(String orformno);

    public String getMaxSeqno(String orformno);

}
