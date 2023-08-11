package tw.gov.pcc.eip.adm.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.gov.pcc.eip.adm.cases.Eip00w220Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.services.Eip00w210Service;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 網站導覽
 *
 * @author swho
 */
@Controller
@AllArgsConstructor
@Slf4j
public class Eip00w220Controller extends BaseController {
  public static final String CASE_KEY = "_eip00w220_caseData";
  private static final String QUERY_PAGE = "/eip00w220/eip00w220q"; // 選擇頁
  private Eip00w210Service eip00w210Service;

  @ModelAttribute(CASE_KEY)
  public Eip00w220Case getEip00w220Case() {
    Eip00w220Case caseData = new Eip00w220Case();
    caseData.setLevel(eip00w210Service.getLevel());
    return ObjectUtility.normalizeObject(caseData);
  }


  /**
   * 進入頁面
   *
   * @return 頁面
   */
  @RequestMapping(value = "/Common_sitemap.action")
  public String enter() {
    log.debug("導向 Common_sitemap ");
    return QUERY_PAGE;
  }
}
