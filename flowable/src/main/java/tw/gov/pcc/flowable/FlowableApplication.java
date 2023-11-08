package tw.gov.pcc.flowable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
        log.info("\n" +
                "=========================================================================================================\n" +
                "    //////////          &&&& &&&                                          ,,,         ,,,\n" +
                "  //////////////     &&&&&&& &&&                                          ,,,         ,,,\n" +
                " ///          ///   &&&      &&&                                          ,,,         ,,,\n" +
                " ////////     ///   &&&&&&&& &&&   &&&&&&&   &&&   &&&    &&&  ,,,,,,,,,, ,,,,,,,,,   ,,,   ,,,,,,,\n" +
                " //////   //  ///   &&&&&&&& &&& &&&&   &&&& &&&   &&&    &&& ,,,    ,,,, ,,,,   ,,,, ,,, ,,,    ,,\n" +
                " ///    ////  ///   &&&      &&& &&&     &&& &&&   &&&    &&& ,,      ,,, ,,,     ,,, ,,, ,,,,,,,,,\n" +
                " //    /////  ///   &&&      &&& &&&    &&&& &&&   &&&    &&& ,,,    ,,,, ,,,,    ,,, ,,, ,,,\n" +
                "     ///////////    &&&      &&&  &&&&&&&&    &&&&&&&&&&&&&&   ,,,,,,,,,,   ,,,,,,,,  ,,,  ,,,,,,,,\n" +
                ":::::Flowable Running:::::\n" +
                "=========================================================================================================");
    }

}
