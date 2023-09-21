package tw.gov.pcc.eip.framework.spring.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * 用來結合 Bean Validation 及 Spring Validation 的 abstract class。
 * <p>
 * 使用時只要繼承這個 Class 並實作以下兩個 method 即可：
 * <p>
 * <code>public abstract boolean supports(Class<?> clazz);</code>、
 * <code>abstract void validation(Object target, Errors errors);</code>
 * <p>
 * ＊＊＊注意＊＊＊
 * <p>
 * 使用限制：只能配合 Bean Validation 的 Default Group 使用。
 *
 * @author Goston
 */
public abstract class AbstractCombineValidator implements Validator {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractCombineValidator.class);
    @Autowired
    private javax.validation.Validator beanValidator;

    public AbstractCombineValidator() {
    }

    public abstract boolean supports(Class<?> clazz);

    public void validate(Object target, Errors errors) {
        SpringValidatorAdapter spv = new SpringValidatorAdapter(beanValidator);
        Set<ConstraintViolation<Object>> constraintViolations = spv.validate(target);
        constraintViolations.forEach(constraintViolation -> {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.rejectValue(propertyPath, "", message);
        });
        validation(target, errors);
    }

    protected abstract void validation(Object target, Errors errors);
}
