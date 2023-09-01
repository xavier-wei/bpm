package tw.gov.pcc.common.tag.func;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class MinguoDateFormaterTag extends TagSupport {
	private static final long serialVersionUID = -5176664237145052068L;
	private static final DateTimeFormatter STRING_YMD_PATTERN = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter STRING_YMDH_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHH");
	private static final DateTimeFormatter STRING_YMDHM_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private static final DateTimeFormatter STRING_YMDHMS_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final DateTimeFormatter STRING_TW_YMD_PATTERN = DateTimeFormatter.ofPattern("yyyMMdd").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter STRING_TW_YMDH_PATTERN = DateTimeFormatter.ofPattern("yyyMMddHH").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter STRING_TW_YMDHM_PATTERN = DateTimeFormatter.ofPattern("yyyMMddHHmm").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter STRING_TW_YMDHMS_PATTERN = DateTimeFormatter.ofPattern("yyyMMddHHmmss").withChronology(MinguoChronology.INSTANCE);

	private static final DateTimeFormatter DEFAULT_Y_PATTERN = DateTimeFormatter.ofPattern("yyy").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter DEFAULT_YM_PATTERN = DateTimeFormatter.ofPattern("yyy/MM").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter DEFAULT_YMD_PATTERN = DateTimeFormatter.ofPattern("yyy/MM/dd").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter DEFAULT_YMDH_PATTERN = DateTimeFormatter.ofPattern("yyy/MM/dd HH").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter DEFAULT_YMDHM_PATTERN = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm").withChronology(MinguoChronology.INSTANCE);
	private static final DateTimeFormatter DEFAULT_YMDHMS_PATTERN = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss").withChronology(MinguoChronology.INSTANCE);
	private static final Function<String, DateTimeFormatter> MAPPER = p -> DateTimeFormatter.ofPattern(p).withChronology(MinguoChronology.INSTANCE);
	private Object value;
	private String pattern;

	@Override
	public int doStartTag() {
		if (value == null) {
			return SKIP_BODY;
		}
		String str;
		try {
			if (value instanceof Date) {
				str = format((Date) value);
			} else if (value instanceof LocalDate) {
				DateTimeFormatter formatter = getFormatter(DEFAULT_YMD_PATTERN);
				str = format((LocalDate) value, formatter);
			} else if (value instanceof LocalDateTime) {
				DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHMS_PATTERN);
				str = format((LocalDateTime) value, formatter);
			} else if (value instanceof MinguoDate) {
				DateTimeFormatter formatter = getFormatter(DEFAULT_YMD_PATTERN);
				str = format((MinguoDate) value, formatter);
			} else if (value instanceof YearMonth) {
				DateTimeFormatter formatter = getFormatter(DEFAULT_YM_PATTERN);
				str = format((YearMonth) value, formatter);
			} else if (value instanceof String) {
				str = format((String) value);
			} else {
				str = Objects.toString(value, "");
			}
		} catch (DateTimeException e1) {
			str = value.toString();
		}
		try {
			pageContext.getOut().write(HtmlUtils.htmlEscape(str));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		return SKIP_BODY;
	}

	private DateTimeFormatter getFormatter(DateTimeFormatter ifEmpty) {
		return Optional.ofNullable(StringUtils.trimToNull(pattern)).map(MAPPER).orElse(ifEmpty);
	}

	private String format(Date value) {
		if (value instanceof Timestamp) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHMS_PATTERN);
			return format(((Timestamp) value).toLocalDateTime(), formatter);
		}
		DateTimeFormatter formatter = getFormatter(DEFAULT_YMD_PATTERN);
		return format(value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), formatter);
	}

	private String format(TemporalAccessor value, DateTimeFormatter formatter) {
		return formatter.format(value);
	}

	private String format(String value) {
		String s = value.replaceAll("[^0-9]", "");
		if (s.length() == 4) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_Y_PATTERN);
			return format(STRING_YMD_PATTERN.parse(s + "0101"), formatter);
		} else if (s.length() == 5) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YM_PATTERN);
			return format(STRING_TW_YMD_PATTERN.parse(s + "01"), formatter);
		} else if (s.length() == 6) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YM_PATTERN);
			return format(STRING_YMD_PATTERN.parse(s + "01"), formatter);
		} else if (s.length() == 7) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMD_PATTERN);
			return format(STRING_TW_YMD_PATTERN.parse(s), formatter);
		} else if (s.length() == 8) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMD_PATTERN);
			return format(STRING_YMD_PATTERN.parse(s), formatter);
		} else if (s.length() == 9) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDH_PATTERN);
			return format(STRING_TW_YMDH_PATTERN.parse(s), formatter);
		} else if (s.length() == 10) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDH_PATTERN);
			return format(STRING_YMDH_PATTERN.parse(s), formatter);
		} else if (s.length() == 11) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHM_PATTERN);
			return format(STRING_TW_YMDHM_PATTERN.parse(s), formatter);
		} else if (s.length() == 12) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHM_PATTERN);
			return format(STRING_YMDHM_PATTERN.parse(s), formatter);
		} else if (s.length() == 13) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHMS_PATTERN);
			return format(STRING_TW_YMDHMS_PATTERN.parse(s), formatter);
		} else if (s.length() == 14) {
			DateTimeFormatter formatter = getFormatter(DEFAULT_YMDHMS_PATTERN);
			return format(STRING_YMDHMS_PATTERN.parse(s), formatter);
		}
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}
}
