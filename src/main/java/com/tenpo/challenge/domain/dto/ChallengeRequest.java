package com.tenpo.challenge.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ChallengeRequest {

    private BigDecimal value1;
    private BigDecimal value2;

}
