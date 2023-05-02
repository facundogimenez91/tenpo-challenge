package com.tenpo.challenge.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PercentageCache {

    private OffsetDateTime updatedAt;
    private BigDecimal lastValue;


}
