package tw.gov.pcc.eip.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tw.gov.pcc.eip.dao.StoredProcedureDao;

@Slf4j
@Service
public class StoredProcedureService {
	
	private StoredProcedureDao storedProcedureDao;
	
	public StoredProcedureService(StoredProcedureDao storedProcedureDao) {
		this.storedProcedureDao = storedProcedureDao;
	}

	/**
	 * 產生批次流水號
	 * @return jobno
	 */
	public String getJobno(){
		Map<String, Object> map = storedProcedureDao.getJobno();
		String jobno = (String) map.get("p_jobno");
    	return jobno ;
    }

	/**
	 * 紀錄執行批次作業 LOG
	 * @param jobno    流水號
	 * @param jobid    程式編號
	 * @param jobname  程式中文名稱
	 * @param step     處理階段(START, PARAMS, INFO, EXCEPTION, END)
	 * @param stepinfo 處理階段資訊
	 * @param memo     備註說明
	 */
    public void callSpJobLog(String jobno, String jobid, String jobname, String step, String stepinfo, String memo) {
        storedProcedureDao.callSpJobLog(jobno, jobid, jobname, step, stepinfo, memo);
    }

}
