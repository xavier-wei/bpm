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
}
