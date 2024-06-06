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

public class PracticeWireMockTest {

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
    public void wireMockTestForGetAllPractices() {
        wireMockServer.stubFor(get(urlEqualTo("/api/practices"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from getAllPractices")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/practices";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from getAllPractices", response.getBody());
    }

    @Test
    public void wireMockTestForGetPractice() {
        wireMockServer.stubFor(get(urlEqualTo("/api/practices/PracticeTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from get practice with code PracticeTest")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/practices/PracticeTest";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from get practice with code PracticeTest", response.getBody());
    }

    @Test
    public void wireMockTestForInsertPractice() {
        wireMockServer.stubFor(post(urlEqualTo("/api/practices"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/practices";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"code\":\"PracticeTest\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForUpdatePractice() {
        wireMockServer.stubFor(put(urlEqualTo("/api/practices/PracticeTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/practices/PracticeTest";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"newCode\":\"Updated PracticeTest\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForDeletePractice() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/practices/PracticeTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/practices/PracticeTest";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.DELETE, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
