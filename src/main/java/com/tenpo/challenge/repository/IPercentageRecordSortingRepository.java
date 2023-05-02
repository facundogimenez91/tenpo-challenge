package com.tenpo.challenge.repository;

import com.tenpo.challenge.domain.dao.PercentageRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IPercentageRecordSortingRepository extends ReactiveSortingRepository<PercentageRecord, Long> {

    Flux<PercentageRecord> findAllBy(Pageable pageable);

}