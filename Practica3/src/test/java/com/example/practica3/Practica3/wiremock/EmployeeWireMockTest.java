package com.example.practica3.Practica3.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

public class EmployeeWireMockTest {

    private WireMockServer wireMockServer;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    @After
    public void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    public void wireMockTestForGetAllEmployees() {
        wireMockServer.stubFor(get(urlEqualTo("/api/employees"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from getAllEmployees")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/employees";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from getAllEmployees", response.getBody());
    }

    @Test
    public void wireMockTestForGetEmployee() {
        wireMockServer.stubFor(get(urlEqualTo("/api/employees/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from get employee with id 1")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/employees/1";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from get employee with id 1", response.getBody());
    }

    @Test
    public void wireMockTestForInsertEmployee() {
        wireMockServer.stubFor(post(urlEqualTo("/api/employees"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/employees";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"code\":1234, \"name\":\"John Doe\",\"position\":\"Developer\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForUpdateEmployee() {
        wireMockServer.stubFor(put(urlEqualTo("/api/employees/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/employees/1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"name\":\"Daniel Test\", \"role\": \"Test\", \"practice\": \"prueba\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForDeleteEmployee() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/employees/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/employees/1";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.DELETE, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
