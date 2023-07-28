package tw.gov.pcc.eip.services;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.gov.pcc.eip.apply.cases.Eip08w050Case;
import tw.gov.pcc.eip.apply.report.Eip08w050l00;
import tw.gov.pcc.eip.apply.report.Eip08w050l01;
import tw.gov.pcc.eip.dao.ApplyitemDao;
import tw.gov.pcc.eip.dao.ItemcodeDao;
import tw.gov.pcc.eip.dao.ItemcodeuDao;
import tw.gov.pcc.eip.domain.Applyitem;
import tw.gov.pcc.eip.framework.domain.UserBean;
import tw.gov.pcc.eip.util.FilenameUtility;

/**
 * 領物單紀錄查詢及列印作業Service
 * 
 * @author Ivy
 * @since 2023
 */
@Service
public class Eip08w050Service {

	@Autowired
	UserBean userData;

	@Autowired
	ItemcodeDao itemcodeDao;

	@Autowired
	ApplyitemDao applyitemDao;
	
	@Autowired
	ItemcodeuDao itemcodeuDao;
	
	@Autowired
	ItemcodeService itemcodeService;
	
	/**
	 * 查詢/設定資料
	 * 
	 * @param caseData
	 * @return 
	 */
	public void getEip08w050Report(Eip08w050Case caseData) {
		List<Applyitem> unitList = applyitemDao.selectApplyItemReportByUnit(caseData.getApplyYearMonth());
		List<Applyitem> itemList = applyitemDao.selectApplyItemReportByItem(caseData.getApplyYearMonth());
		caseData.setUnitList(unitList);
		caseData.setItemList(itemList);
	}
	
	/**
	 * 列印報表
	 * 
	 * @param caseData
	 * @return 
	 */
	public ByteArrayOutputStream getPrintData(Eip08w050Case caseData) throws Exception{
		getEip08w050Report(caseData);
		if(CollectionUtils.isEmpty(caseData.getUnitList()) && CollectionUtils.isEmpty(caseData.getItemList())) {
			return null;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream);
		
		Eip08w050l00 report = new Eip08w050l00();
		report.createPdf(caseData);
		ByteArrayOutputStream output = report.getOutputStream();
		if (!Objects.isNull(output)) {
			String fileName = FilenameUtility.getFileName(userData.getUserId(), "Eip08w050l00領物單核發數量統計表(依申請單位顯示)", "pdf");
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOut.putNextEntry(zipEntry);
			zipOut.write(output.toByteArray());
			zipOut.closeEntry();
		}
		
		Eip08w050l01 rpt = new Eip08w050l01();
		rpt.createPdf(caseData);
		byte[] out = rpt.getOutputStream().toByteArray();
		if (out != null && out.length != 0) {
			String fileName = FilenameUtility.getFileName(userData.getUserId(), "Eip08w050l01領物單核發數量統計表(依品名顯示)", "pdf");
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOut.putNextEntry(zipEntry);
			zipOut.write(out);
			zipOut.closeEntry();
		}
		
		zipOut.close();
		return byteArrayOutputStream;
		
	}
}