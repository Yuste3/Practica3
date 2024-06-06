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

public class AssignmentWireMockTest {

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
    public void wireMockTestForGetAllAssignments() {
        wireMockServer.stubFor(get(urlEqualTo("/api/assignments"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from getAllAssignments")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/assignments";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from getAllAssignments", response.getBody());
    }

    @Test
    public void wireMockTestForGetAssignment() {
        wireMockServer.stubFor(get(urlEqualTo("/api/assignments/1234"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from get project with code 1234")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/assignments/1234";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from get project with code 1234", response.getBody());
    }

    @Test
    public void wireMockTestForInsertAssignment() {
        wireMockServer.stubFor(post(urlEqualTo("/api/assignments"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/assignments";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"employeeCode\": 0,\"supervisorCode\": 0,\"assignmentInfo\": {\"remark\": \"string\",\"percentage\": \"string\",\"startDate\": \"string\",\"endDate\": \"string\"},\"project\": {\"oldCode\": \"string\",\"newCode\": \"string\"},\"practiceName\": \"string\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForUpdateAssignment() {
        wireMockServer.stubFor(put(urlEqualTo("/api/assignments/1234"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/assignments/1234";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"employeeCode\": 0,\"supervisorCode\": 0,\"assignmentInfo\": {\"remark\": \"string\",\"percentage\": \"string\",\"startDate\": \"string\",\"endDate\": \"string\"},\"project\": {\"oldCode\": \"string\",\"newCode\": \"string\"},\"practiceName\": \"string\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForDeleteAssignment() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/assignments/1234"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/assignments/1234";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.DELETE, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
