package tw.gov.pcc.eip.services;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.common.cases.Eip02w010Case;
import tw.gov.pcc.eip.common.cases.Eip02w010Case.addressBook;
import tw.gov.pcc.eip.dao.DeptsDao;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.dao.UsersDao;
import tw.gov.pcc.eip.domain.Depts;
import tw.gov.pcc.eip.domain.Eipcode;

/**
 * 通訊錄查詢
 * 
 * @author vita
 *
 */
@Service
@Slf4j
public class Eip02w010Service {
    @Autowired
    private EipcodeDao eipCodeDao;
    @Autowired
    private DeptsDao deptsDao;
    @Autowired
    private UsersDao usersDao;

    /**
     * 初始化選單內容
     * 
     * @param caseData
     */
    public void initOptions(Eip02w010Case caseData) {
        Comparator<Eipcode> customComparator = new Comparator<Eipcode>() {
            @Override
            public int compare(Eipcode o1, Eipcode o2) {
                if (Integer.parseInt(o1.getCodeno()) > Integer.parseInt(o2.getCodeno()))
                    return 1;
                else
                    return -1;
            }
        };
        // 部門
        List<Eip02w010Case.Option> contactunits = deptsDao.getEip01wDepts().stream()
                .filter(f -> !"00".equals(f.getDept_id())).map(m -> {
                    Eip02w010Case.Option option = new Eip02w010Case.Option();
                    option.setCodeno(m.getDept_id());
                    option.setCodename(m.getDept_name());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setDept(contactunits);
        // 職稱
        List<Eip02w010Case.Option> titles = eipCodeDao.findByCodeKind("TITLE").stream()
                .sorted(customComparator)
                .map(m -> {
                    Eip02w010Case.Option option = new Eip02w010Case.Option();
                    option.setCodeno(m.getCodeno());
                    option.setCodename(m.getCodename());
                    return option;
                }).collect(Collectors.toCollection(ArrayList::new));
        caseData.setTitle(titles);
    }

    /**
     * 初始查詢
     * 
     * @param caseData
     */
    public void initQuery(Eip02w010Case caseData, String deptId) {
        List<addressBook> addressBook = usersDao.getEip02wUsers(deptId, null, null, null, null, null);
        String copyStr = addressBook.stream().map(m -> {
            return String.format("%s<%s>", m.getUser_name(), m.getEmail());
        }).collect(Collectors.joining(";"));
        String deptName = "";
        Optional<Depts> opt = Optional.ofNullable(deptsDao.findByPk(deptId));
        if (opt.isPresent()) {
            deptName = opt.get().getDept_name();
        }
        String groupStr = deptId + "：" + deptName;
        caseData.setQryList(addressBook); // 查詢結果
        caseData.setCopyStr(copyStr); // 複製按鈕
        caseData.setGroupStr(groupStr); // 查詢結果為同一部門時顯示
    }

    /**
     * 查詢
     * 
     * @param caseData
     */
    public void query(Eip02w010Case caseData) {
        List<addressBook> addressBook = usersDao.getEip02wUsers(trimToNull(caseData.getDept_id()),
                trimToNull(caseData.getUser_name()), trimToNull(caseData.getUser_id()),
                trimToNull(caseData.getUser_ename()), trimToNull(caseData.getEmail()),
                trimToNull(caseData.getTitlename()));
        String copyStr = addressBook.stream().map(m -> {
            return String.format("%s<%s>", m.getUser_name(), m.getEmail());
        }).collect(Collectors.joining(";"));
        caseData.setQryList(addressBook); // 查詢結果
        caseData.setCopyStr(copyStr); // 複製按鈕
        String deptId = trimToNull(caseData.getDept_id());
        if (deptId != null) {
            String deptName = "";
            Optional<Depts> opt = Optional.ofNullable(deptsDao.findByPk(deptId));
            if (opt.isPresent()) {
                deptName = opt.get().getDept_name();
            }
            String groupStr = deptId + "：" + deptName;
            caseData.setGroupStr(groupStr); // 查詢結果為同一部門時顯示
        }
    }
}
