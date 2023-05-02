package com.tenpo.challenge.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "service.percentage")
public class AddPercentageServiceProperty {

    private Long ttlInMinutes = 2L;

}
