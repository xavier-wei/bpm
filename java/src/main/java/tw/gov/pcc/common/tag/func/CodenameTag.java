package tw.gov.pcc.common.tag.func;

import org.springframework.web.util.HtmlUtils;
import tw.gov.pcc.eip.dao.CodeDao;
import tw.gov.pcc.eip.domain.Code;
import tw.gov.pcc.eip.util.ObjectUtility;
import tw.gov.pcc.common.helper.SpringHelper;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Optional;

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
            CodeDao codeDao = SpringHelper.getBeanByClass(CodeDao.class);
            Optional<Code> code = codeDao.findByCodeKindCodeNo(codekind, codeno);
            if (code.isPresent()) {
                pageContext.getOut().write(ObjectUtility.normalizeObject(HtmlUtils.htmlEscape(code.get().getCodename())));
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
