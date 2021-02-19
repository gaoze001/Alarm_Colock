package clock.vo;

import lombok.Data;

@Data
public class PriceVo {
    private String regionCode;
    private String itemName;
    private Integer itemPrice;
    private Integer itemNum;
}
