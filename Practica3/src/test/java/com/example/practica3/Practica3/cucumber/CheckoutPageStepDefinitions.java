package com.example.practica3.Practica3.cucumber;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.en.And;
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
    private ResponseEntity<?> response;
    private final RestTemplate restTemplate = new RestTemplate();

    @Given("a employeeDTO to insert in DDBB with code {int}, name {string}, role {string} and practice {string}")
    public void aEmployeeDTOToInsertInDDBBWithCodeNameRoleAndPractice(int code, String name, String role, String practice) {
        employeeDTO = new EmployeeDTO();
        employeeDTO.setCode(code);
        employeeDTO.setName(name);
        employeeDTO.setRole(role);
        employeeDTO.setPractice(practice);
    }

    @When("I insert a employee")
    public void i_insert_a_employee() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<EmployeeDTO> request = new HttpEntity<>(employeeDTO, headers);

        response = restTemplate.exchange("http://localhost:8080/api/employees", HttpMethod.POST, request, String.class);
    }

    @Then("returns NO_CONTENT")
    public void returns_no_content() {
        // ELIMINO EL EMPLEADO QUE SE ACABA DE INSERTAR PORQUE SE ESTA HACIENDO UNA LLAMADA A LA BBDD REAL Y SI SE VUELVE A EJECUTAR EL TEST DARÍA ERROR
        String apiUrl = "http://localhost:8080/api/employees/88";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        restTemplate.exchange(apiUrl, HttpMethod.DELETE, request, String.class);
        // FINALIZADO DEL BORRADO DEL EMPLEADO Y COMPROBACIÓN DEL STATUS DE LA INSERCCIÓN REALIZADA ANTERIORMENTE

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Given("employee exists with code {int}")
    public void employeeExistsWithCode(int code) {

    }

    @When("I get the employee with code {int}")
    public void iGetTheEmployeeWithCode(int code) {
        String apiUrl = "http://localhost:8080/api/employees/" + code;
        response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
    }

    @Then("returns OK")
    public void returnsOK() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @And("return must contain employee's name {string}")
    public void returnMustContainEmployeeSName(String name) {
        assertEquals(name, response.getBody());
    }

    @And("return must contain employee's role {string}")
    public void returnMustContainEmployeeSRole(String role) {

    }

    @And("return must contain employee's practice {string}")
    public void returnMustContainEmployeeSPractice(String practice) {
    }
}
