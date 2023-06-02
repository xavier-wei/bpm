package tw.gov.pcc.eip.util;

import org.apache.commons.lang3.StringUtils;
import tw.gov.pcc.common.domain.FrameworkUserInfoBean;
import tw.gov.pcc.common.helper.HttpHelper;
import tw.gov.pcc.common.helper.UserSessionHelper;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilenameUtility {

	/**
	 * 依傳入參數，取得對應檔案名稱
	 * 
	 * @param userid 員工編號
	 * @param fileName 檔案名稱
	 * @param extension 副檔名
	 * @return　userid_fileName_年月日時分秒毫秒.extension
	 */
    public static String getFileName(String userid, String fileName, String extension) {
    	
    	if(StringUtils.isNoneEmpty(new CharSequence[]{userid, fileName, extension}) ) {
    		
        	String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        	
    		return userid + "_" + fileName + "_" + temp + "." + extension;
    	}
    	return "";
    }
    /**
     * 丟印單給傳給機房 檔案名稱在最後
     * 依傳入參數，取得對應檔案名稱
     * 
     * @param userid 員工編號
     * @param fileName 檔案名稱
     * @param extension 副檔名
     * @return　userid_fileName_年月日時分秒毫秒.extension
     */
    public static String getDuinFileName(String userid, String fileName, String extension) {
        
        if(StringUtils.isNoneEmpty(new CharSequence[]{userid, fileName, extension}) ) {
            
            String temp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
            
            return userid + "_" + temp + "_" + fileName + "." + extension;
        }
        return "";
    }

	public static String getFileName(String fileName, String extension) {
		HttpServletRequest httpRequest = HttpHelper.getHttpRequest();
		String userid = StringUtils.EMPTY;
		if (httpRequest != null) {
			FrameworkUserInfoBean user = UserSessionHelper.getFrameworkUserData(httpRequest);
			if (user != null) {
				userid = user.getUserId();
			}
		}
		if (StringUtils.isNotEmpty(userid)) {
			String temp = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			return userid + "_" + fileName + "_" + temp + "." + extension;
		} else {
			String temp = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			return fileName + "_" + temp + "." + extension;
		}
	}


	public static String getContentTypeByFilename(String filename) {
		String ext = org.springframework.util.StringUtils.getFilenameExtension(filename);
		if ("txt".equalsIgnoreCase(ext)) {
			return "text/plain";
		} else if ("gif".equalsIgnoreCase(ext)) {
			return "image/gif";
		} else if ("jpg".equalsIgnoreCase(ext)) {
			return "image/jpeg";
		} else if ("jpeg".equalsIgnoreCase(ext)) {
			return "image/jpeg";
		} else if ("jpe".equalsIgnoreCase(ext)) {
			return "image/jpeg";
		} else if ("zip".equalsIgnoreCase(ext)) {
			return "application/zip";
		} else if ("rar".equalsIgnoreCase(ext)) {
			return "application/rar";
		} else if ("doc".equalsIgnoreCase(ext)) {
			return "application/msword";
		} else if ("ppt".equalsIgnoreCase(ext)) {
			return "application/vnd.ms-powerpoint";
		} else if ("xls".equalsIgnoreCase(ext)) {
			return "application/vnd.ms-excel";
		} else if ("html".equalsIgnoreCase(ext)) {
			return "text/html";
		} else if ("htm".equalsIgnoreCase(ext)) {
			return "text/html";
		} else if ("tif".equalsIgnoreCase(ext)) {
			return "image/tiff";
		} else if ("tiff".equalsIgnoreCase(ext)) {
			return "image/tiff";
		} else if ("pdf".equalsIgnoreCase(ext)) {
			return "application/pdf";
		} else if ("xlsx".equalsIgnoreCase(ext)) {  //20220801 BF增加
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else {
			return "text/html";
		}
		}

}
