package tw.gov.pcc.eip.framework.validation.validators;

import tw.gov.pcc.eip.framework.validation.ChineseBirthday;
import tw.gov.pcc.eip.util.DateUtility;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ChineseBirthdayValidator
 *
 * @author Goston
 */
public class ChineseBirthdayValidator implements ConstraintValidator<ChineseBirthday, Object> {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChineseBirthdayValidator.class);
    private String beforeChineseFieldName;
    private String birthdayFieldName;

    @Override
    public void initialize(final ChineseBirthday parameters) {
        this.beforeChineseFieldName = parameters.beforeChineseFieldName();
        this.birthdayFieldName = parameters.birthdayFieldName();
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext constraintValidatorContext) {
        try {
            final String beforeChinese = BeanUtils.getProperty(obj, this.beforeChineseFieldName);
            final String birthday = BeanUtils.getProperty(obj, this.birthdayFieldName);
            if (StringUtils.isBlank(beforeChinese) || StringUtils.isBlank(birthday)) return true;
            boolean beforeROC = false;
            if (StringUtils.equalsIgnoreCase(beforeChinese, "true")) beforeROC = true;
            boolean result = DateUtility.isValidDate(birthday, beforeROC);
            if (result) {
                // 檢核是否大於今天日期
                if (!beforeROC) {
                    result = Integer.parseInt(birthday) <= Integer.parseInt(DateUtility.getNowChineseDate());
                }
            }
            return result;
        } catch (Exception e) {
            log.error("@ChineseBirthday Validator 發生錯誤");
            return false;
        }
    }
}
