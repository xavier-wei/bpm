package tw.gov.pcc.flowable;

public class TEST {
    public static void main(String[] args) {
        String input = "http://localhost:9986/eip/Eip08w010_enter.actio/bpm";
        String keyword = "/eip";

        int index = input.indexOf(keyword);
        input = input.replace(input.substring(index),"");

        System.out.println(input);
    }
}
