package clock.vo;

import lombok.Data;

import java.util.List;

@Data
public class RegionList {
    private String region;
    private String area;
    private String regionCode;
    private List<PriceList> priceListList;
}
