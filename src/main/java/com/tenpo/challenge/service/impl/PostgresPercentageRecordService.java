package com.tenpo.challenge.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.domain.dao.PercentageRecord;
import com.tenpo.challenge.domain.dao.PercentageRecordTypeEnum;
import com.tenpo.challenge.domain.dto.ChallengeRequest;
import com.tenpo.challenge.domain.dto.ChallengeResponse;
import com.tenpo.challenge.domain.dto.ErrorResponse;
import com.tenpo.challenge.repository.IPercentageRecordCRUDRepository;
import com.tenpo.challenge.repository.IPercentageRecordSortingRepository;
import com.tenpo.challenge.service.IPercentageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostgresPercentageRecordService implements IPercentageRecordService {

    private final ObjectMapper objectMapper;
    private final IPercentageRecordCRUDRepository percentageRecordCRUDRepository;
    private final IPercentageRecordSortingRepository percentageRecordSortingRepository;

    @Override
    public Mono<ChallengeRequest> saveRequest(ChallengeRequest request) {
        PercentageRecord percentageRecord;
        try {
            percentageRecord = generateRecord(request, PercentageRecordTypeEnum.REQUEST);
        } catch (JsonProcessingException e) {
            return Mono.just(request);
        }
        log.info(percentageRecord);
        return percentageRecordCRUDRepository.save(percentageRecord)
                .then(Mono.just(request));
    }

    @Override
    public Mono<ChallengeResponse> saveResponse(ChallengeResponse response) {
        PercentageRecord percentageRecord;
        try {
            percentageRecord = generateRecord(response, PercentageRecordTypeEnum.RESPONSE);
        } catch (JsonProcessingException e) {
            return Mono.just(response);
        }
        log.info(percentageRecord);
        return percentageRecordCRUDRepository.save(percentageRecord)
                .then(Mono.just(response));
    }

    @Override
    public Mono<ErrorResponse> saveResponse(ErrorResponse response) {
        PercentageRecord percentageRecord;
        try {
            percentageRecord = generateRecord(response, PercentageRecordTypeEnum.RESPONSE);
        } catch (JsonProcessingException e) {
            return Mono.just(response);
        }
        log.info(percentageRecord);
        return percentageRecordCRUDRepository.save(percentageRecord)
                .then(Mono.just(response));
    }

    @Override
    public Mono<Page<PercentageRecord>> findAll(PageRequest pageRequest) {
        return percentageRecordSortingRepository.findAllBy(pageRequest)
                .collectList()
                .zipWith(percentageRecordCRUDRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }

    private PercentageRecord generateRecord(Object object, PercentageRecordTypeEnum type) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(object);
        return PercentageRecord.builder()
                .value(json)
                .createdAt(OffsetDateTime.now())
                .type(type.toString())
                .build();
    }

}
