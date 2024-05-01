package ru.rsreu.ChineseCourse.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
                .doOnError(WebClientResponseException.class, e -> {
                    // Обработка ошибки
                })
                .subscribe(response -> {
                    // JavaScript для открытия отчета в новой вкладке
                    String script = "window.open('/path/to/generated/report', '_blank');";
                    // Здесь '/path/to/generated/report' должен быть путь к вашему сгенерированному отчету на сервере
                    // Этот путь должен быть возвращен в ответе от сервера после генерации отчета

                    // Выполнение JavaScript на стороне клиента
                    webEngine.executeScript(script);
                });
                //.block();
    }


}