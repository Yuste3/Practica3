package com.example.practica3.Practica3.cucumber;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutPageStepDefinitions {

    private EmployeeDTO employeeDTO;
    private ResponseEntity<String> response;
    private final RestTemplate restTemplate = new RestTemplate();

    private WireMockServer wireMockServer;

    @Given("a employeeDTO to insert in DDBB with code {int}, name {String}, role {String} and practice {String}")
    public void a_employee_dto_to_insert_in_ddbb(int code, String name, String role, String practice) {
        System.out.println(name);
        employeeDTO = new EmployeeDTO();
        employeeDTO.setCode(88);
        employeeDTO.setName("John Doe");
        employeeDTO.setRole("Developer");
        employeeDTO.setPractice("prueba");
    }

    @When("I insert a employee")
    public void i_insert_a_employee() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);

        // Asumiendo que la aplicación de prueba levanta la API en un puerto aleatorio
        response = restTemplate.exchange("http://localhost:8080/api/employees", HttpMethod.POST, request, String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Then("returns CONFLICT")
    public void returns_no_content() {
        // ELIMINO EL EMPLEADO QUE SE ACABA DE INSERTAR PORQUE SE ESTA HACIENDO UNA LLAMADA A LA BBDD REAL Y SI SE VUELVE A EJECUTAR
        // EL TEST DARÍA ERROR
        // TODO MOCKEAR LA LLAMADA A LA API O LEVANTAR UNA INSTANCIA NUEVA
        String apiUrl = "http://localhost:8080/api/employees/88";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.exchange(apiUrl, HttpMethod.DELETE, request, String.class);
        // FINALIZADO DEL BORRADO DEL EMPLEADO Y COMPROBACIÓN DEL STATUS DE LA INSERCCIÓN REALIZADA ANTERIORMENTE
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        if (this.wireMockServer != null) {
            this.wireMockServer.stop();
        }
    }
}
