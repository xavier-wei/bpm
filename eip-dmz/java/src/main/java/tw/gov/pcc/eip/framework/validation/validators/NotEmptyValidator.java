package tw.gov.pcc.eip.framework.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.eip.framework.validation.NotEmpty;

/**
 * IdNoFormatValidator
 *
 * @author Goston
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, CharSequence> {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NotEmptyValidator.class);

  @Override
  public void initialize(NotEmpty parameters) {
  }

  @Override
  public boolean isValid(CharSequence str, ConstraintValidatorContext constraintValidatorContext) {
    if (str == null) return false;
    String value = str.toString();
    if (StringUtils.isBlank(StringUtils.trimToEmpty(value))) return false;
    return true;
  }
}
