package tw.gov.pcc.eip.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StreamUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import tw.gov.pcc.eip.framework.json.CustomObjectMapper;
import tw.gov.pcc.common.util.ExceptionUtil;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * 配合PFS使用Web Service提供服務。
 * 原本使用和BC一樣，後來PFS要求將json包在soap中
 *
 * @author swho
 */
public class WebServiceUtility {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebServiceUtility.class);

    public static String getRawOutput(Object resRoot, String soapRestFilename) throws Exception {
        try {
            StringWriter writer = new StringWriter();
            ObjectMapper objectMapper = new CustomObjectMapper();
            writer.write(objectMapper.writeValueAsString(resRoot));
            String template;
            try (InputStream is = new ClassPathResource(String.format("soapTemplate/%s", soapRestFilename)).getInputStream()) {
                template = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            }
            String rawOutput = String.format(template, StringEscapeUtils.escapeXml(writer.toString()));
            log.info("rawOutput: {}", ObjectUtility.normalizeObject(writer));
            return ObjectUtility.normalizeObject(rawOutput);
        } catch (Exception e) {
            log.error("解析回傳失敗:{}", ExceptionUtil.getStackTrace(e));
            throw new Exception(e);
        }
    }

    public static <T> T getReqRoot(String rawInput, Class<T> clazz) throws Exception {
        try {
            log.info("rawInput: {}", ObjectUtility.normalizeObject(rawInput));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            dbf.setAttribute("http://xml.org/sax/features/namespaces", true);
            dbf.setAttribute("http://xml.org/sax/features/validation", false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            dbf.setValidating(true);
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new InputSource(new StringReader(ObjectUtility.normalizeObject(rawInput))));
            int maxTry = 0;
            Node node = document;
            while (maxTry < 100 && !StringUtils.startsWithIgnoreCase(node.getNodeValue(), "<?xml") && !PatternMatchUtils.simpleMatch("{*}", node.getNodeValue())) {
                if (node.getFirstChild() != null) {
                    node = node.getFirstChild();
                } else {
                    node = node.getNextSibling();
                }
                maxTry++;
            }
            String nodeValue = node.getNodeValue();
            return new ObjectMapper().readValue(nodeValue, clazz);
        } catch (Exception e) {
            log.error("解析參數失敗:{}", ExceptionUtil.getStackTrace(e));
            throw new Exception(e);
        }
    }
}
