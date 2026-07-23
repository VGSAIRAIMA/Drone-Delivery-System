package com.example.apigateway.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayController {

    @Autowired
    private RestTemplate restTemplate;

    private String getServiceUrl(String uri) {

        if (uri.startsWith("/packages")) {
            return "http://localhost:8080" + uri;
        }

        if (uri.startsWith("/drones")) {
            return "http://localhost:8081" + uri;
        }

        if (uri.startsWith("/routes")) {
            return "http://localhost:8082" + uri;
        }

        if (uri.startsWith("/deliveries")) {
            return "http://localhost:8083" + uri;
        }

        throw new RuntimeException("No matching service found.");
    }

    @RequestMapping("/**")
    public ResponseEntity<String> forwardRequest(
            HttpServletRequest request,
            @RequestBody(required = false) String body) {

        String uri = request.getRequestURI();

        String query = request.getQueryString();

        if (query != null) {
            uri = uri + "?" + query;
        }

        String targetUrl = getServiceUrl(uri);

        HttpHeaders headers = new HttpHeaders();

        request.getHeaderNames().asIterator().forEachRemaining(header ->
                headers.add(header, request.getHeader(header)));

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        ResponseEntity<String> response = restTemplate.exchange(
                targetUrl,
                method,
                entity,
                String.class);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody());
    }
}