package tw.gov.pcc.common.util;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.web.util.HtmlUtils;

public class TagUtil {

	public static String getAttributes(Map<String, Object> dynamicMap) {
		if (dynamicMap == null) {
			return "";
		}
		return dynamicMap
		.entrySet()
		.stream()
		.map(e -> new StringBuilder()
				.append(e.getKey())
				.append("=\"")
				.append(HtmlUtils.htmlEscape(Objects.toString(e.getValue(), "")))
				.append("\"")
				.toString())
		.collect(Collectors.joining(" ", " ", " "));
	}
	
}
