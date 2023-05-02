package com.tenpo.challenge.service;

import com.tenpo.challenge.domain.dao.PercentageRecord;
import com.tenpo.challenge.domain.dto.ChallengeRequest;
import com.tenpo.challenge.domain.dto.ChallengeResponse;
import com.tenpo.challenge.domain.dto.ErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Mono;

public interface IPercentageRecordService {

    /**
     * Saves the ChallengeRequest given
     * @param request the ChallengeRequest to be saved
     *
     * @return the ChallengeRequest saved
     */
    Mono<ChallengeRequest> saveRequest(ChallengeRequest request);

    /**
     * Saves the ChallengeResponse given
     * @param response the ChallengeResponse to be saved
     *
     * @return the ChallengeResponse saved
     */
    Mono<ChallengeResponse> saveResponse(ChallengeResponse response);

    /**
     * Saves the ErrorResponse given
     * @param errorResponse the ErrorResponse to be saved
     *
     * @return the ErrorResponse saved
     */
    Mono<ErrorResponse> saveResponse(ErrorResponse errorResponse);

    /**
     * Gets the target PercentageRecords using PageRequest
     * @param pageRequest PageRequest parameters
     *
     * @return the Page target page of PercentageRecord
     */
    Mono<Page<PercentageRecord>> findAll(PageRequest pageRequest);



}
