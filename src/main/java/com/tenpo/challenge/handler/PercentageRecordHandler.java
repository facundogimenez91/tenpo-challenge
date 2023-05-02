package com.tenpo.challenge.handler;

import com.tenpo.challenge.domain.exception.MissingQueryParamException;
import com.tenpo.challenge.service.IPercentageRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@RequiredArgsConstructor
public class PercentageRecordHandler {

    private static final String QUERY_PARAM_PAGE = "page";
    private static final String QUERY_PARAM_SIZE = "size";
    private final IPercentageRecordService percentageRecordService;
    private final ResponseHelper responseHelper;


    public Mono<ServerResponse> execute(ServerRequest request) {
        var pageOptional = request.queryParam(QUERY_PARAM_PAGE);
        if (pageOptional.isEmpty()) {
            return responseHelper.build(HttpStatus.BAD_REQUEST, Mono.just(new MissingQueryParamException(QUERY_PARAM_PAGE).toErrorResponse()));
        }
        var page = pageOptional.get();
        if (!StringUtils.isNumeric(page))
            return responseHelper.build(HttpStatus.BAD_REQUEST, Mono.just(new MissingQueryParamException(QUERY_PARAM_PAGE).toErrorResponse()));
        var sizeOptional = request.queryParam(QUERY_PARAM_SIZE);
        if (sizeOptional.isEmpty()) {
            return responseHelper.build(HttpStatus.BAD_REQUEST, Mono.just(new MissingQueryParamException(QUERY_PARAM_SIZE).toErrorResponse()));
        }
        var size = sizeOptional.get();
        if (!StringUtils.isNumeric(size))
            return responseHelper.build(HttpStatus.BAD_REQUEST, Mono.just(new MissingQueryParamException(QUERY_PARAM_SIZE).toErrorResponse()));
        return responseHelper.buildOK(percentageRecordService.findAll(PageRequest.of(Integer.parseInt(pageOptional.get()), Integer.parseInt(sizeOptional.get()))));
    }

}
