package com.tenpo.challenge.domain.dto;

import com.tenpo.challenge.core.APIEndpointRequest;
import com.tenpo.challenge.core.APIEndpointResponse;
import org.springframework.http.HttpMethod;


public class PercentageRequest implements APIEndpointRequest {

    @Override
    public String getUri() {
        return "/api/percentage/fixed";
    }

    @Override
    public HttpMethod getRequestMethod() {
        return HttpMethod.GET;
    }

    @Override
    public Class<? extends APIEndpointResponse> getResponseClass() {
        return PercentageResponse.class;
    }

}
