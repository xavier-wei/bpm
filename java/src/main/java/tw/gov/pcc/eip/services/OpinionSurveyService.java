package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.gov.pcc.eip.dao.EipcodeDao;
import tw.gov.pcc.eip.domain.Eipcode;

import java.io.File;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 意見調查共用Service
 *
 * @author Weith
 */
@Service
public class OpinionSurveyService {
    @Autowired
    EipcodeDao eipcodeDao;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OpinionSurveyService.class);

    DateTimeFormatter minguoformatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss")
            .withChronology(MinguoChronology.INSTANCE)
            .withLocale(Locale.TAIWAN);

}
