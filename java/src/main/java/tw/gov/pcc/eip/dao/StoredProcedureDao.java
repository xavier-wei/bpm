package tw.gov.pcc.eip.dao;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import tw.gov.pcc.eip.domain.Eipcode;

import java.util.List;
import java.util.Map;


public interface StoredProcedureDao {

    /**
     * 產生批次流水號
     *
     * @return 批次流水號
     */
    Map<String, Object> getJobno();

    /**
     * 紀錄執行批次作業 LOG
     * 
     * @param jobno    流水號
     * @param jobid    程式編號
     * @param jobname  程式中文名稱
     * @param step     處理階段(START, PARAMS, INFO, EXCEPTION, END)
     * @param stepinfo 處理階段資訊
     * @param memo     備註說明
     */
    void callSpJobLog(String jobno, String jobid, String jobname, String step, String stepinfo, String memo);

    /**
     * call SP
     *
     * @param catalogName
     * @param procedureName
     * @param param         執行參數
     * @return 執行結果
     */
    Object callProcedure(String catalogName, String procedureName, SqlParameterSource param);

    /**
     * call Function
     * 
     * @param catalogName
     * @param functionName
     * @param param
     * @return
     */
    public String callFunction(String catalogName, String functionName, SqlParameterSource param);

    /**
     * call SP
     *
     * @param procedureName
     * @param param         執行參數
     * @return 執行結果
     */
    Object callProcedure(String procedureName, SqlParameterSource param);
    Object callProcedure(String procedureName, SqlParameterSource param, SqlParameter[] sqlParameters);


    /**
     * 呼叫 procedure 不透過 meta
     * 
     * @param catalogName
     * @param procedureName
     * @param param
     * @param sqlParameters
     * @return
     */
    Map<String, Object> callProcedureWithoutMetaData(String catalogName, String procedureName, SqlParameterSource param,
            SqlParameter... sqlParameters);


    /**
     * 依據CODEKIND回傳代碼檔暫存TABLE資料
     * @param codeKind
     * @return
     */
    public List<Eipcode> findByCodeKind(String codeKind) ;

}