package com.tenpo.challenge.repository;

import com.tenpo.challenge.domain.dao.PercentageRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPercentageRecordCRUDRepository extends ReactiveCrudRepository<PercentageRecord, Long> {

}