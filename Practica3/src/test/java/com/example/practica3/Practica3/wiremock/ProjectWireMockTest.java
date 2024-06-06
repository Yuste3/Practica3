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

public class ProjectWireMockTest {

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
    public void wireMockTestForGetAllProjects() {
        wireMockServer.stubFor(get(urlEqualTo("/api/projects"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from getAllProjects")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/projects";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from getAllProjects", response.getBody());
    }

    @Test
    public void wireMockTestForGetProject() {
        wireMockServer.stubFor(get(urlEqualTo("/api/projects/ProjectTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withBody("Response from get project with code ProjectTest")));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/projects/ProjectTest";
        ResponseEntity<String> response = new RestTemplate().getForEntity(apiUrl, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Response from get project with code ProjectTest", response.getBody());
    }

    @Test
    public void wireMockTestForInsertProject() {
        wireMockServer.stubFor(post(urlEqualTo("/api/projects"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/projects";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"code\":\"ProjectTest\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.POST, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForUpdateProject() {
        wireMockServer.stubFor(put(urlEqualTo("/api/projects/ProjectTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/projects/ProjectTest";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"newCode\":\"Updated ProjectTest\"}";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.PUT, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void wireMockTestForDeleteProject() {
        wireMockServer.stubFor(delete(urlEqualTo("/api/projects/ProjectTest"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        String apiUrl = "http://localhost:" + wireMockServer.port() + "/api/projects/ProjectTest";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = new RestTemplate().exchange(apiUrl, HttpMethod.DELETE, request, String.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
