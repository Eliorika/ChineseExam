package ru.rsreu.ChineseCourse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ReportClient {

    @Value("${report.base-url}")
    private String baseUrl = "http://localhost:8089";
    private final WebClient reportWebClient;

    public ReportClient() {
        reportWebClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void getUserStatReport(Integer userId, Integer courseId){
        reportWebClient.post()
                .uri("/report?userId={userId}&courseId={courseId}", userId, courseId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


}