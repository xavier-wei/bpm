package tw.gov.pcc.eip.framework.controllers;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tw.gov.pcc.common.dao.impl.PortalDaoImpl;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.framework.spring.support.FileOutputView;
import tw.gov.pcc.eip.util.ExceptionUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * !!!!開發除錯用!!!!
 *
 * @author swho
 */
@Controller
public class ConsoleController {

    public static final String CASE_KEY = "_console_caseData";

    @Autowired
    private PortalDaoImpl consoleDao;

    @Autowired
    private UserBean userBean;

    @RequestMapping("/Common_console.action")
    public String console(@ModelAttribute(CASE_KEY) ConsoleCase consoleCase, Model model) {
        if (userBean == null || !StringUtils.equalsIgnoreCase(userBean.getUserId(), "n5000")) {
            return "/index";
        }
        try {
            String inputText = StringUtils.trim(consoleCase.getInput_text());
            if (StringUtils.isEmpty(inputText)) {
                return "/console";
            }
            JdbcTemplate jdbcTemplate = consoleDao.getJdbcTemplate();
            if (!StringUtils.startsWithIgnoreCase(inputText, "select")) {
                int rowsAffected = jdbcTemplate.update(inputText);
                model.addAttribute("updateResult", "異動 " + rowsAffected + " 行");
            } else {
                List<Map<String, Object>> results = jdbcTemplate.queryForList(inputText);
                model.addAttribute("results", results);
            }
        } catch (Exception e) {
            model.addAttribute("error", "ERROR!:" + e.getMessage() + "\n " + ExceptionUtility.getStackTrace(e));
        }
        return "/console";
    }


    @RequestMapping("/Common_downloadlog.action")
    public ModelAndView downloadlog(@ModelAttribute(CASE_KEY) ConsoleCase consoleCase, Model model) {
        if (userBean == null || !StringUtils.equalsIgnoreCase(userBean.getUserId(), "n5000")) {
            return new ModelAndView("/index");
        }
        try {
            LoggerContext loggerContext = LoggerContext.getContext(false);

            Configuration config = loggerContext.getConfiguration();

            Appender appender = config.getAppender("A2");

            if (appender == null) {
                return new ModelAndView("/console");
            }

            String logFilePath = ((RollingFileAppender) appender).getFileName();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream inputStream = new FileInputStream(logFilePath)) {
                IOUtils.copy(inputStream, baos);
            }
            return new ModelAndView(new FileOutputView(baos, FilenameUtils.getName(logFilePath), FileOutputView.GENERAL_FILE));


        } catch (Exception e) {
            model.addAttribute("error", "ERROR!:" + e.getMessage() + "\n " + ExceptionUtility.getStackTrace(e));
        }
        return new ModelAndView("/console");
    }


    @Data
    public static class ConsoleCase {
        private String input_text;
    }

}
