package com.tenpo.challenge.core;

import org.springframework.http.HttpMethod;

public interface APIEndpointRequest {

    String getUri();

    HttpMethod getRequestMethod();

    Class<? extends APIEndpointResponse> getResponseClass();

}