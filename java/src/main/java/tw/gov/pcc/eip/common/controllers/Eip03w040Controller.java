package tw.gov.pcc.eip.common.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.eip.common.cases.Eip03w040Case;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.services.Eip03w040Service;
import tw.gov.pcc.eip.util.DateUtility;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

import java.io.ByteArrayOutputStream;

/**
 * 重要列管事項_件數統計
 * @author 2201009
 */

@Controller
public class Eip03w040Controller extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(Eip03w040Controller.class);

    public static final String CASE_KEY = "_eip03w040CController_caseData";
    private static final String QUERY_PAGE =  "/eip03w040/eip03w040q";
    private static final String DETAIL_PAGE =  "/eip03w040/eip03w040x";

    @Autowired
    private Eip03w040Service eip03w040Service;


    @ModelAttribute(CASE_KEY)
    public Eip03w040Case getEip03w040Case(){
        Eip03w040Case caseData = new Eip03w040Case();
        eip03w040Service.initDataList(caseData);
        return ObjectUtility.normalizeObject(caseData);
    }

    /**
     * 進入頁面
     *
     */
    @RequestMapping("/Eip03w040_enter.action")
    public String enter() {
        log.debug("導向重要列管事項_件數統計作業");
        return QUERY_PAGE;
    }



    @RequestMapping("/Eip03w040_queryIssue.action")
    public String queryIssue(@ModelAttribute(CASE_KEY) Eip03w040Case caseData){
        eip03w040Service.queryIssue(caseData);
        return DETAIL_PAGE;
    }

    /**
     * 列印會議室活動
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip03w040_print.action")
    public ModelAndView print(@Validated @ModelAttribute(CASE_KEY) Eip03w040Case caseData) {

        try {
            Eip03w040Case returnCase = eip03w040Service.print(caseData);
            ByteArrayOutputStream baos = returnCase.getBaos();
            if (baos == null) {
                log.info("查無報表資料");
                setSystemMessage(getQueryEmptyMessage());
                return new ModelAndView(QUERY_PAGE);
            }

            String filename = (caseData.getStatus().equals("closed")? "已結案" : "未結案") + "件數統計報表_" + DateUtility.getNowChineseDate() + "_" + caseData.getTrkObj() + ".xls";
            return new ModelAndView(new FileOutputView(baos, filename, FileOutputView.EXCEL_FILE));
        } catch (Exception e) {
            log.error("件數統計報表 - 下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
            return new ModelAndView(QUERY_PAGE);
        }
    }
}
