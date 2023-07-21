package tw.gov.pcc.eip.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

/**
 * 時間轉48位元共同Service
 * @author 2201009
 */
@Service
public class TimeConversionService {


    /**
     * 起訖時間轉為48位二進制編碼
     * 0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000_0000
     * ex 1300~1430 0000_0000_0000_0000_0000_0000_0011_1000_0000_0000_0000_0000
     * @param begin
     * @param end
     * @return
     */
    public String to48binary(String begin, String end){
        StringBuilder using = new StringBuilder(); //回傳值
        //取得時間差間隔幾個半小時
        LocalTime beginTime = LocalTime.parse(begin.substring(0,2) + ":" + begin.substring(2));
        LocalTime endTime = LocalTime.parse(end.substring(0,2) + ":" + end.substring(2));
        Duration duration = Duration.between(beginTime, endTime);
        int halfHours = Integer.parseInt(String.valueOf(duration.getSeconds()/1800));
        //取得開始時間位置
        //前兩位如果是00，後兩位是30? +0 : +1
        //前兩位如果不是00，取前兩位數/100*2，後兩位是30? +0 : +1
        int beginPosition = 0;
        if(begin.startsWith("00")){
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }else {
            beginPosition = Integer.parseInt(begin.substring(0,2) + "00")/100*2;
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }

        //生成48字元字串
        for(int i = 0 ; using.length() < 48 ; i++){
            if(i!=beginPosition){
                using.append("0");
            }else {
                //將開始時間+半小時個數填為1 其餘皆為0
                for (int j = 0; j < halfHours ; j++ ){
                    using.append("1");
                }
                using.append("0");
            }
        }
        //若訖的時間為2330 則將第48位元轉成1
        if(StringUtils.equals(end,"2330")){
            using.setCharAt(using.length()-1, '1');
        }
        return using.toString();
    }

    /**
     * 會議室啟用期間 轉為48位二進制編碼
     * ex 1300~1430 1111_1111_1111_1111_1111_1111_1100_0111_1111_1111_1111_1111
     * @param begin
     * @param end
     * @return
     */
    public String to48binary_isAble(String begin, String end){
        StringBuilder using = new StringBuilder(); //回傳值
       //轉換時間格式
        LocalTime beginTime = LocalTime.parse(begin.substring(0,2) + ":" + begin.substring(2));
        LocalTime endTime = LocalTime.parse(end.substring(0,2) + ":" + end.substring(2));
        //計算時間區間
        Duration duration = Duration.between(beginTime, endTime);
        //計算時間區間為幾個半小時(系統只有29分+60秒=1800)
        int halfHours = Integer.parseInt(String.valueOf((duration.getSeconds()+60)/1800));

        //計算起始位置，預設為0
        int beginPosition = 0;
        //00:00 起始位置為0、00:30 起始位置為1
        if(begin.startsWith("00")){
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }else {
            //XX:00 起始位置為 時*2、XX:30 XX:30 起始位置為 時*2+1
            beginPosition = Integer.parseInt(begin.substring(0,2))*2;
            beginPosition += StringUtils.equals(begin.substring(2),"30")? 1 : 0;
        }

        //生成48字元字串
        for(int i = 0 ; using.length() < 48 ; i++){
            //會議室非啟用區間 回傳為 1 (串)
            if(i!=beginPosition){
                using.append("1");
            }else {
                //會議室啟用區間 回傳為0
                for (int j = 0; j < halfHours ; j++ ){
                    using.append("0");
                }
            }
        }
        return using.toString();
    }

    public String[] timeStringToBeginEndTime (String timeString){
        char[] chars = timeString.toCharArray();
        String[] beginEndTime = new String[2];
        String beginTime = null;
        String endTime = null;
        int i;
        //            beginTime
        for ( i = 0 ; i < chars.length ; i++){
            if( chars[i] == '1' && i <= 1){  //0000 or 0030 for beginTime
                if( i == 0){  //偶數 = XX:00 奇數 = XX:30
                    beginTime = "0000";
                }else {
                    beginTime = "0030";
                }
                break;
            } else if( chars[i] == '1'){
                if( i % 2 == 0){  //偶數 = XX:00 奇數 = XX:30
                    beginTime = (i/2) < 10 ? ("0" + i/2) + "00" : (i/2) + "00";
                }else {  //奇數要-1
                    beginTime = ((i-1)/2) < 10 ? ("0" + (i-1)/2 ) + "30" :  (i-1)/2 + "30";
                }
                break;
            }
        }

        //            endTime
        for (int j = i ; j < chars.length ; j++){
            if( chars[j+1] == '0' && j+1 <= 1){  // 如果從開始時間算起下一位元為0 表示此位元為字串最後一個 1 ， 0000 or 0030 for endTime
                endTime = "0030"; //偶數 = XX:00 奇數 = XX:30
                break;
            } else if( chars[j+1] == '0'){
                if( (j+1) % 2 == 0){  //偶數 = XX:00 奇數 = XX:30
                    endTime = ((j+1)/2) < 10 ? ("0" + (j+1)/2) + "00" : ((j+1)/2) + "00";
                }else {
                    endTime = (j/2) < 10 ? ("0" + j/2) + "30" :  j/2  + "30";
                }
                break;
            }
        }
        beginEndTime[0] = beginTime;
        beginEndTime[1] = endTime;

        return beginEndTime;
    }
}


