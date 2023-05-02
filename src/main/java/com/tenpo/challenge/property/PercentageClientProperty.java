package com.tenpo.challenge.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Data
@ConfigurationProperties(prefix = "percentage.client")
public class PercentageClientProperty {

    private Boolean mockEnable = Boolean.TRUE;
    private BigDecimal mockFixedValue = BigDecimal.valueOf(10);
    private String url;
    private Integer connectionTimeout = 1000;
    private Long retries = 3L;
    private Long retriesDelay = 2000L;

}
