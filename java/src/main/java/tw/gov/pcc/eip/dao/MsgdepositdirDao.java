package tw.gov.pcc.eip.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.Msgdepositdir;

/**
 * 訊息檔案存放目錄表
 * 
 * @author vita
 *
 */
@DaoTable(MsgdepositdirDao.TABLE_NAME)
@Repository
public interface MsgdepositdirDao {

    public static final String TABLE_NAME = "MSGDEPOSITDIR";

    public int insert(Msgdepositdir m);

    public int update(Msgdepositdir m, String fileseq);

    public int delete(Msgdepositdir m);

    public Msgdepositdir findbyPk(String fileseq);

    /**
     * 依登入者部門取得有權限查詢之目錄
     * 
     * @param dept
     * @return
     */
    public List<Msgdepositdir> getTree(String dept);

    /**
     * 取得該屬性下的<b>所有</b>階層目錄<br>
     * 若path有值則取該path的<b>下一層</b>目錄
     * 
     * @param attr
     * @param path
     * @return
     */
    public List<Msgdepositdir> getTreeByAttr(String attr, String path);

    /**
     * 取得Msgdepositdir的下一流水號(fseq)
     * 
     * @return
     */
    public String getNextFseq();

    /**
     * 取預設目錄
     * 
     * @param attr
     * @param deptId
     * @return
     */
    public Msgdepositdir getDefaultPath(String attr, String deptId);

}
