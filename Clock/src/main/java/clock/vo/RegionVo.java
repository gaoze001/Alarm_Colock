package clock.vo;

import lombok.Data;

import java.util.List;

@Data
public class RegionVo {
    private String region;
    private String area;
    private String regionCode;
    private List<PriceVo> priceListVo;
}
