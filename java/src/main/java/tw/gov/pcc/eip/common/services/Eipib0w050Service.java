package tw.gov.pcc.eip.common.services;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.common.cases.Eipib0w050Case;
import tw.gov.pcc.eip.common.cases.Eipib0w050OptionCase;
import tw.gov.pcc.eip.dao.CodeDao;
import tw.gov.pcc.eip.domain.Code;
import tw.gov.pcc.eip.framework.domain.UserBean;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 參數代號維護作業
 *
 * @author vita
 */
@Service
public class Eipib0w050Service {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Eipib0w050Service.class);
    @Autowired
    UserBean userData;
    @Autowired
    private CodeDao codeDao;

    public void initSelectList(Eipib0w050Case caseData) {
        List<Code> selectList = codeDao.findAllCodeKind();
        caseData.setOptionList(selectList.stream().map(Eipib0w050OptionCase::new).collect(Collectors.toList()));
    }

    public void getCodeDetail(Eipib0w050Case caseData) {
        List<Code> selectList = codeDao.findByCodeKind(caseData.getCodekind());
        caseData.setDetailList(selectList.stream().map(Eipib0w050OptionCase::new).collect(Collectors.toList()));
        if (selectList.size() == 0) {
            caseData.setCodekind(null);
            caseData.setCodeno(null);
            caseData.setCodename(null);
            caseData.setScodekind(null);
            caseData.setScodeno(null);
            caseData.setRemark(null);
        }
    }

    public void insertCode(Eipib0w050Case caseData) {
        //檢查是否已存在
        Optional<Code> optional = codeDao.findByCodeKindCodeNo(caseData.getCodekind(), caseData.getCodeno());
        if (optional.isPresent()) {
            throw new RuntimeException();
        }
        Code code = new Code();
        code.setCodekind(caseData.getCodekind());
        code.setCodeno(caseData.getCodeno());
        code.setCodename(caseData.getCodename());
        code.setScodekind(caseData.getScodekind());
        code.setScodeno(caseData.getScodeno());
        code.setRemark(caseData.getRemark());
        code.setStaff(userData.getUserId());
        code.setPrcdat(LocalDateTime.now());
        codeDao.insertCode(code);
    }

    public void updateCode(Eipib0w050Case caseData) {
        Code code = new Code();
        code.setCodekind(caseData.getCodekind());
        code.setCodeno(caseData.getCodeno());
        code.setAfcodeno(caseData.getAfcodeno());
        code.setCodename(caseData.getCodename());
        code.setScodekind(caseData.getScodekind());
        code.setScodeno(caseData.getScodeno());
        code.setRemark(caseData.getRemark());
        code.setStaff(userData.getUserId());
        code.setPrcdat(LocalDateTime.now());
        codeDao.updateCode(code);
        if (!StringUtils.equals(caseData.getCodekind(), caseData.getAfcodekind())) {
            Optional<Code> optional = codeDao.findByCodeKindPresent(caseData.getAfcodekind());
            if (optional.isPresent()) {
                throw new RuntimeException();
            }
            code.setAfcodekind(caseData.getAfcodekind());
            codeDao.updateCode_batch(code);
            caseData.setCodekind(caseData.getAfcodekind());
        }
    }

    public void deleteCode(Eipib0w050Case caseData) {
        List<String> list = caseData.getDelete();
        for (String s : list) {
            Code code = new Code();
            code.setCodekind(caseData.getCodekind());
            code.setCodeno(s);
            codeDao.deleteCode(code);
        }
    }
}
