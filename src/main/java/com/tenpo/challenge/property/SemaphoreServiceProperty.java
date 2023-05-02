package com.tenpo.challenge.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "service.semaphore")
public class SemaphoreServiceProperty {

    private Integer permits = 3;
    private Long acquireTimeoutInSec = 2L;

}
