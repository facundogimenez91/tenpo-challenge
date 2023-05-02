package com.tenpo.challenge.domain.dao;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;

@Data
@Builder
@Table("percentage_records")
public class PercentageRecord {

    @Id
    private Long id;
    private String type;
    private String value;
    @Column("created_at")
    private OffsetDateTime createdAt;

}
