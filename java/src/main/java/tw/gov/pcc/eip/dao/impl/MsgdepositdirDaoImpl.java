package tw.gov.pcc.eip.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.common.framework.dao.BaseDao;
import tw.gov.pcc.eip.dao.MsgdepositdirDao;
import tw.gov.pcc.eip.domain.Msgdepositdir;

/**
 * 訊息檔案存放目錄表
 * 
 * @author vita
 *
 */
@DaoTable(MsgdepositdirDao.TABLE_NAME)
@Repository
public class MsgdepositdirDaoImpl extends BaseDao<Msgdepositdir> implements MsgdepositdirDao {

    @Override
    public int insert(Msgdepositdir m) {
        return super.insert(m);
    }

    @Override
    public int update(Msgdepositdir m, String fileseq) {
        m.setFileseq(fileseq);
        return super.updateByPK(m);
    }

    @Override
    public int delete(Msgdepositdir m) {
        return super.deleteByPK(m);
    }

    @Override
    public Msgdepositdir findbyPk(String fileseq) {
        Msgdepositdir m = new Msgdepositdir();
        m.setFileseq(fileseq);
        return this.selectDataByPrimaryKey(m);
    }

    @Override
    public Msgdepositdir selectDataByPrimaryKey(Msgdepositdir t) {
        return super.selectByPK(t);
    }

    @Override
    public List<Msgdepositdir> getTree(String dept) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT EXISTHIER, "); // 檔案存放父階層
        sql.append("        DEPT ATTRIBUTYPE, "); // 部門代號(只有在第一層部門資料夾有值)
        sql.append("        SORTORDER, "); // 排序位置
        sql.append("        FILEPATH, "); // 檔案路徑
        sql.append("        FILENAME1 FILENAME, "); // 檔案/資料夾名稱
        sql.append("        ASCII(EXISTHIER) ORDERBYUSE ");
        sql.append("   FROM UFN_GET_FILEDIR(:dept) ");
        sql.append("  ORDER BY ORDERBYUSE ");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdepositdir.class));
    }

    @Override
    public List<Msgdepositdir> getTreeByAttr(String attr, String path) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT EXISTHIER, "); // 檔案存放父階層
        sql.append("        SORTORDER, "); // 排序位置
        sql.append("        FILEPATH, "); // 檔案路徑
        sql.append("        FILENAME1 FILENAME, "); // 檔案/資料夾名稱
        sql.append("        ASCII(EXISTHIER) ORDERBYUSE");
        sql.append("   FROM UFN_GET_DIR(:attr, :path) ");
        sql.append("  ORDER BY ORDERBYUSE, SORTORDER ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("path", path);
        return getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdepositdir.class));
    }

    @Override
    public String getNextFseq() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT NEXT VALUE FOR MSGDIRSEQ ");
        return getNamedParameterJdbcTemplate().queryForObject(sql.toString(), new HashMap<String, String>(),
                String.class);
    }

    @Override
    public Msgdepositdir getDefaultPath(String attr, String deptId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT *  ");
        sql.append("   FROM MSGDEPOSITDIR  ");
        sql.append("  WHERE ATTRIBUTYPE = :attr ");
        sql.append("    AND FILENAME = (SELECT DEPT_NAME  ");
        sql.append("                      FROM DEPTS  ");
        sql.append("                     WHERE IS_VALID = 'Y'  ");
        sql.append("                       AND DEPT_ID = :deptId) ");
        sql.append("    AND SORTORDER = '1';  ");
        Map<String, Object> params = new HashMap<>();
        params.put("attr", attr);
        params.put("deptId", deptId);
        return Optional.ofNullable(getNamedParameterJdbcTemplate().query(sql.toString(), params,
                BeanPropertyRowMapper.newInstance(Msgdepositdir.class)).get(0)).orElse(null);
    }

}
