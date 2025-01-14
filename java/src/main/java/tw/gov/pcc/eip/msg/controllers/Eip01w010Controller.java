package tw.gov.pcc.eip.msg.controllers;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cronutils.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.controllers.BaseController;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.msg.cases.Eip01w010Case;
import tw.gov.pcc.eip.msg.cases.Eip01w010Case.Detail;
import tw.gov.pcc.eip.msg.cases.Eip01wFileCase;
import tw.gov.pcc.eip.services.Eip01w010Service;
import tw.gov.pcc.eip.services.Eip01wFileService;
import tw.gov.pcc.eip.util.ExceptionUtility;
import tw.gov.pcc.eip.util.ObjectUtility;

/**
 * 訊息上稿
 * 
 * @author vita
 *
 */
@Controller
@Slf4j
public class Eip01w010Controller extends BaseController {

    public static final String CASE_KEY = "_eip01w010Controller_casekey";
    private static final String MAIN_PAGE = "/eip01w010/eip01w010q";
    private static final String EDIT_PAGE = "/eip01w010/eip01w010a";

    @Autowired
    private UserBean userData;
    @Autowired
    private Eip01w010Service eip01w010Service;
    @Autowired
    private Eip01wFileService eip01wFileService;

    /**
     * 進入頁面
     *
     * @return
     */
    @RequestMapping("/Eip01w010_enter.action")
    public ModelAndView enter(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_enter 訊息上稿");
        eip01w010Service.initOptions(caseData);
        eip01w010Service.setDefaultValue(caseData, userData);
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 透過ajax 依畫面選擇的屬性 查詢訊息類別選單
     * 
     * @param attr 屬性
     * @return
     */
    @RequestMapping("/Eip01w010_getOptionData.action")
    @ResponseBody
    public Map<String, String> getOptionData(@RequestParam("attr") String attr) {
        log.info("透過ajax 依畫面選擇的屬性 查詢訊息類別選單");
        return ObjectUtility.normalizeObject(eip01w010Service.getMsgtype(attr));
    }

    /**
     * 透過ajax 依畫面選擇的部門 查詢使用者
     * 
     * @param deptid 部門
     * @return
     */
    @RequestMapping("/Eip01w010_getUsersData.action")
    @ResponseBody
    public Map<String, String> getUsersData(@RequestParam("deptid") String deptid) {
        log.info("透過ajax 依畫面選擇的部門 查詢使用者選單");
        return ObjectUtility.normalizeObject(eip01w010Service.getUsers(deptid));
    }

    /**
     * 新增按鈕
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w010_edit.action")
    public ModelAndView edit(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_enter 訊息上稿 新增/修改");
        eip01w010Service.initOptions(caseData);
        eip01w010Service.setDefaultValue(caseData, userData);
        return new ModelAndView(EDIT_PAGE);
    }

    /**
     * 查詢按鈕
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w010_queryList.action")
    public ModelAndView queryList(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_enter 訊息上稿 新增/修改");
        eip01w010Service.initOptions(caseData);
        eip01w010Service.getQueryList(caseData, userData.getDeptId());
        List<Detail> result = caseData.getQueryList();
        if (CollectionUtils.isEmpty(result)) {
            setSystemMessage(getQueryEmptyMessage());
            return new ModelAndView(MAIN_PAGE);
        } else if (result.size() == 1 && !caseData.isKeep()) {
            caseData.setFseq(result.get(0).getFseq());
            return query(caseData);
        } else {
            return new ModelAndView(MAIN_PAGE);
        }
    }

    /**
     * 單筆查詢
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w010_query.action")
    public ModelAndView query(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_enter 訊息上稿 查詢");
        eip01w010Service.initOptions(caseData);
        try {
            eip01w010Service.getOne(caseData, userData.getDeptId());
            return new ModelAndView(EDIT_PAGE);
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getQueryFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 儲存
     * 
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip01w010_save.action")
    public ModelAndView save(@Validated(Eip01w010Case.Update.class) @ModelAttribute(CASE_KEY) Eip01w010Case caseData,
            BindingResult result) {
        log.debug("導向 Eip01w010_enter 訊息上稿 新增/修改");
        eip01w010Service.initOptions(caseData);
        eip01w010Service.checkOptions(caseData, result);
        if (result.hasErrors()) {
            return new ModelAndView(EDIT_PAGE);
        }
        boolean isNewCase = StringUtils.isEmpty(caseData.getFseq());
        try {
            if (isNewCase) {
                eip01w010Service.insert(caseData, userData.getUserId());
            } else {
                eip01w010Service.update(caseData, userData.getUserId());
            }
            eip01w010Service.insertDataUploadFiles(caseData);
            eip01w010Service.callSp_Eip01w010();
            setSystemMessage(isNewCase ? getSaveSuccessMessage() : getUpdateSuccessMessage());
            return new ModelAndView(EDIT_PAGE);
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(isNewCase ? getSaveFailMessage() : getUpdateFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 新增/修改後 上稿
     * 
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip01w010_msg.action")
    public ModelAndView uploadMsg(
            @Validated(Eip01w010Case.Update.class) @ModelAttribute(CASE_KEY) Eip01w010Case caseData,
            BindingResult result) {
        log.debug("導向 Eip01w010_enter 訊息上稿 上稿");
        eip01w010Service.initOptions(caseData);
        eip01w010Service.checkOptions(caseData, result);
        if (result.hasErrors()) {
            return new ModelAndView(EDIT_PAGE);
        }
        boolean isNewCase = StringUtils.isEmpty(caseData.getFseq());
        try {
            if (isNewCase) {
                eip01w010Service.insert(caseData, userData.getUserId());
            } else {
                eip01w010Service.update(caseData, userData.getUserId());
            }
            eip01w010Service.insertDataUploadFiles(caseData);
            eip01w010Service.updStatus(caseData, "1");
            eip01w010Service.callSp_Eip01w010();
            setSystemMessage(isNewCase ? getSaveSuccessMessage() : getUpdateSuccessMessage());
            return new ModelAndView(EDIT_PAGE);
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(isNewCase ? getSaveFailMessage() : getUpdateFailMessage());
        }
        return new ModelAndView(MAIN_PAGE);
    }

    /**
     * 上稿按鈕
     * 
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip01w010_btnup.action")
    public ModelAndView btnUpload(
            @Validated(Eip01w010Case.Check.class) @ModelAttribute(CASE_KEY) Eip01w010Case caseData,
            BindingResult result) {
        log.debug("導向 Eip01w010_enter 訊息上稿 上稿按鈕");
        eip01w010Service.initOptions(caseData);
        if (result.hasErrors()) {
            return queryList(caseData);
        }
        try {
            eip01w010Service.updStatus(caseData, "1");
            eip01w010Service.callSp_Eip01w010();
            eip01w010Service.getQueryList(caseData, userData.getDeptId());
            caseData.setKeep((caseData.getQueryList() != null && caseData.getQueryList().size() == 1) ? true : false);
//            caseData.setStatus("1");
//            caseData.setStatusText(eip01w010Service.changeStatusText("1", true));
            setSystemMessage(getUpdateSuccessMessage());
            return new ModelAndView(MAIN_PAGE);
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return new ModelAndView(MAIN_PAGE);
        }
    }

    /**
     * 刪除按鈕
     * 
     * @param caseData
     * @param result
     * @return
     */
    @RequestMapping("/Eip01w010_btndel.action")
    public ModelAndView btnDelete(
            @Validated(Eip01w010Case.Check.class) @ModelAttribute(CASE_KEY) Eip01w010Case caseData,
            BindingResult result) {
        log.debug("導向 Eip01w010_enter 訊息上稿 刪除按鈕");
        eip01w010Service.initOptions(caseData);
        String returnPage = "".equals(caseData.getFseq()) ? MAIN_PAGE : EDIT_PAGE;
        if (result.hasErrors()) {
            return queryList(caseData);
        }
        try {
            eip01w010Service.updStatus(caseData, "X"); // 邏輯註銷
            eip01w010Service.getQueryList(caseData, userData.getDeptId());
            caseData.setKeep((caseData.getQueryList() != null && caseData.getQueryList().size() == 1) ? true : false);
//            caseData.setStatus("X");
//            caseData.setStatusText(eip01w010Service.changeStatusText("X", true));
            setSystemMessage(getUpdateSuccessMessage());
            if ("".equals(caseData.getFseq())) {
                return new ModelAndView(MAIN_PAGE); // 列表
            } else if (!"".equals(caseData.getFseq()) && "D".equals(caseData.getMode())) {
                caseData.setFseq("");
                caseData.setMode("Q");
                return new ModelAndView(MAIN_PAGE); // 列表
            } else {
                caseData.setFseq("");
                caseData.setMode("");
                return enter(caseData); // Q I 主頁
            }
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getUpdateFailMessage());
            return new ModelAndView(returnPage);
        }
    }

    /**
     * 刪除附檔
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w010_delFile.action")
    public ModelAndView deleteFile(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_delFile 訊息上稿 刪除附檔");
        eip01w010Service.initOptions(caseData);
        try {
            eip01w010Service.deleteFile(caseData.getFseq(), caseData.getSeq());
            eip01w010Service.getOne(caseData, userData.getDeptId());
        } catch (Exception e) {
            log.error("訊息上稿 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage(getDeleteFailMessage());
        }
        return new ModelAndView(EDIT_PAGE);
    }

    /**
     * 取得樹
     * 
     * @param attr
     * @return
     */
    @RequestMapping("/Eip01w010_getTree.action")
    @ResponseBody
    public Eip01w010Case.Tree getTree(@RequestParam("attr") String attr) {
        return ObjectUtility.normalizeObject(eip01w010Service.getTree(attr, userData.getDeptId()));
    }

    /**
     * 透過ajax 查詢預覽資料
     * 
     * @param fseq 項次
     * @return
     */
    @RequestMapping("/Eip01w010_getDetail.action")
    @ResponseBody
    public String getDetail(@RequestParam("fseq") String fseq) {
        log.debug("導向 Eip01w010_getDetail 訊息上稿 預覽查詢");
        return ObjectUtility.normalizeObject(eip01w010Service.query(fseq));
    }

    /**
     * 訊息上稿 檔案下載
     * 
     * @param caseData
     * @return
     */
    @RequestMapping("/Eip01w010_getFile.action")
    public ModelAndView download(@ModelAttribute(CASE_KEY) Eip01w010Case caseData) {
        log.debug("導向 Eip01w010_getFile 訊息上稿 檔案下載");
        try {
            eip01w010Service.initOptions(caseData);
            Eip01wFileCase filecase = new Eip01wFileCase();
            filecase.setFseq(caseData.getFseq());
            filecase.setSeq(caseData.getSeq());
            filecase.setSubject("");
            ByteArrayOutputStream baos = eip01wFileService.getFile(filecase);
            if (baos != null) {
                return new ModelAndView(new FileOutputView(baos, filecase.getFilename(), FileOutputView.GENERAL_FILE));
            } else {
                setSystemMessage("查無下載檔案");
            }
        } catch (Exception e) {
            log.error("訊息上稿 附檔下載 - " + ExceptionUtility.getStackTrace(e));
            setSystemMessage("檔案下載失敗");
        }
        return new ModelAndView("Q".equals(caseData.getMode()) ? MAIN_PAGE : EDIT_PAGE);
    }
}
