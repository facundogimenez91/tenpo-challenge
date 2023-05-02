package com.tenpo.challenge.config;

import com.tenpo.challenge.handler.ChallengeHandler;
import com.tenpo.challenge.handler.PercentageRecordHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routeRequest(ChallengeHandler challengeHandler, PercentageRecordHandler percentageRecordHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/api/v1/calc"), challengeHandler::execute)
                .andRoute(RequestPredicates.GET("/api/v1/record"), percentageRecordHandler::execute);
    }

}
