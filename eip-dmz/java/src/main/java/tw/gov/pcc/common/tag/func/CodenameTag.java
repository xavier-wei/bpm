package tw.gov.pcc.common.tag.func;

import java.util.Optional;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.util.HtmlUtils;

import tw.gov.pcc.common.helper.SpringHelper;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.Eipcode;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 畫面代碼顯示
 */
public class CodenameTag extends TagSupport {
    private static final long serialVersionUID = 1L;
    private String codekind;
    private String codeno;

    @Override
    public int doStartTag() {
        try {
            //todo 緩存
            EipcodeDao eipcodeDao = SpringHelper.getBeanByClass(EipcodeDao.class);
            Optional<Eipcode> eipcode = eipcodeDao.findByCodeKindCodeNo(codekind, codeno);
            if (eipcode.isPresent()) {
                pageContext.getOut().write(ObjectUtility.normalizeObject(HtmlUtils.htmlEscape(eipcode.get().getCodename())));
            }
            return SKIP_BODY;
        } catch (final java.lang.Throwable $ex) {
            throw new RuntimeException($ex);
        }
    }

    public void setCodekind(final String codekind) {
        this.codekind = codekind;
    }

    public void setCodeno(final String codeno) {
        this.codeno = codeno;
    }
}
