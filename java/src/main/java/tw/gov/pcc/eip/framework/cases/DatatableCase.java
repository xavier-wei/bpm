package tw.gov.pcc.eip.framework.cases;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Datatable ajax case
 * 拿來放datable 交換資料
 *
 * @param <T> 乘載的資料型態
 * @author swho
 */
@Data
public class DatatableCase<T> {
    private BigDecimal recordsTotal;
    private BigDecimal recordsFiltered;
    private Search search;
    private List<Order> order;
    private BigDecimal start;
    private BigDecimal length;
    private BigDecimal draw;


    private List<T> data;

    @Data
    public static class Order {
        private String column;
        private DIR dir;

        public enum DIR {
            asc, desc
        }
    }

    @Data
    public static class Search {
        private String value;
    }

}
