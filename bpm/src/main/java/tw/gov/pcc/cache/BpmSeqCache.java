package tw.gov.pcc.cache;

public class BpmSeqCache {

    private static String l414Seq = null;
    private static String l410Seq=null;

    public static String getL414Seq() {
        return l414Seq;
    }

    public static void setL414Seq(String l414Seq) {
        BpmSeqCache.l414Seq = l414Seq;
    }

    public static String getL410Seq() {
        return l410Seq;
    }

    public static void setL410Seq(String l410Seq) {
        BpmSeqCache.l410Seq = l410Seq;
    }
}
