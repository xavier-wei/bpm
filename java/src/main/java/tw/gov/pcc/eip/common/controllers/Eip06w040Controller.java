package tw.gov.pcc.eip.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip06w040Case;
import tw.gov.pcc.eip.services.Eip06w040Service;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;

/**
 * 會議室活動報表列印作業
 * @author 2201009
 */

@Controller
public class Eip06w040Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip06w040Controller.class);

    public static final String CASE_KEY = "_eipi06w040Controller_caseData";
    private static final String QUERY_PAGE =  "/eip06w040/eip06w040l"; //新增會議(非管理員)

    @Autowired
    private Eip06w040Service eip06w040Service;


    @ModelAttribute(CASE_KEY)
    public Eip06w040Case getEip06w040Case(){
        Eip06w040Case caseData = new Eip06w040Case();
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip06w040_enter.action")
    public String enter() {
        log.debug("導向會議室活動報表列印作業");
        return QUERY_PAGE;
    }

    /**
     * 列印會議室活動
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip06w040_print.action")
    public ModelAndView print(@Validated @ModelAttribute(CASE_KEY) Eip06w040Case caseData, BindingResult result) {

        try {
            if(result.hasErrors()){
                return new ModelAndView(QUERY_PAGE);
            }
            Eip06w040Case returnCase = eip06w040Service.print(caseData);
            ByteArrayOutputStream baos = returnCase.getBaos();
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                return new ModelAndView(QUERY_PAGE);
            }

            String filename = "會議室活動_" + DateUtility.getNowChineseDate() + ".xls";
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.EXCEL_FILE));
        } catch (Exception e) {
            log.error("會議室活動報表 - 下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
            return new ModelAndView(QUERY_PAGE);
        }
    }
}
