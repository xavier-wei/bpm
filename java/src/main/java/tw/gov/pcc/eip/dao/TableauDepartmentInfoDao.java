package tw.gov.pcc.eip.dao;

import org.springframework.stereotype.Repository;
import tw.gov.pcc.common.annotation.DaoTable;
import tw.gov.pcc.eip.domain.TableauDepartmentInfo;

import java.util.List;


@DaoTable(TableauDepartmentInfoDao.TABLE_NAME)
@Repository
public interface TableauDepartmentInfoDao {

    String TABLE_NAME = "PWC_TB_TABLEAU_DEPARTMENT_INFO";

    List<TableauDepartmentInfo> selectByDepartmentId(String deptId);
}