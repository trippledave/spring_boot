/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cibek.mscasa.common.client;


import de.cibek.mscasa.common.session.dto.SessionUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author DaSee
 */
@Service
public class UserManagementApiClient {

    @Autowired
    private WebClient.Builder webClientBuilderino;

    @Bean
    public WebClient getUserManagementWebClient() {
        return webClientBuilderino
                .baseUrl("lb://user-management/")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public SessionUserDto getSessionUserByUsername(String username) {
        return getUserManagementWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                .path("/internal-api/v1/session-user/{username}")
                .build(username))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    System.out.println("4xx error");
                    return Mono.error(new RuntimeException("4xx"));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    System.out.println("5xx error");
                    return Mono.error(new RuntimeException("5xx"));
                })
                .bodyToMono(SessionUserDto.class)
                .block();
    }
}
