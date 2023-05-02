package com.tenpo.challenge.domain.dto;

import com.tenpo.challenge.core.APIEndpointResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PercentageResponse implements APIEndpointResponse {

    private BigDecimal value;

}
