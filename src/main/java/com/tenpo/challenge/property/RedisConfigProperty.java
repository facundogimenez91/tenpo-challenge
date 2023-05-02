package com.tenpo.challenge.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConfigProperty {

    private String host;
    private Integer port;
    private Long expireTime = 10000L;
    private String keyPrefix = "challenge";

}
