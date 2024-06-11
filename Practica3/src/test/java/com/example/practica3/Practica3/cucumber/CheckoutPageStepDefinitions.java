package com.example.practica3.Practica3.cucumber;

import com.example.practica3.DTOs.EmployeeDTOs.EmployeeDTO;
import com.example.practica3.model.Employee;
import com.example.practica3.repository.EmployeeRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;


public class CheckoutPageStepDefinitions {

    private EmployeeDTO employeeDTO;
    private ResponseEntity<String> response;
    private ResponseEntity<EmployeeDTO> employeeDTOResponse;
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
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Given("employee exists with code {long}")
    public void employeeExistsWithCode(long code) {
    }

    @When("I get the employee with code {int}")
    public void iGetTheEmployeeWithCode(int code) {
        String apiUrl = "http://localhost:8080/api/employees/" + code;
        employeeDTOResponse = restTemplate.getForEntity(apiUrl, EmployeeDTO.class);
    }

    @Then("returns OK")
    public void returnsOK() {
        assertEquals(HttpStatus.OK, employeeDTOResponse.getStatusCode());
        System.out.println("Empleado recuperado existosamente " + employeeDTOResponse.getBody());
    }

    @And("return must contain employee's name {string}")
    public void returnMustContainEmployeeSName(String name) {
        assertEquals(name, employeeDTOResponse.getBody().getName());
        System.out.println(employeeDTOResponse.getBody().getName());
    }

    @And("return must contain employee's role {string}")
    public void returnMustContainEmployeeSRole(String role) {
        assertEquals(role, employeeDTOResponse.getBody().getRole());
        System.out.println(employeeDTOResponse.getBody().getRole());

    }

    @And("return must contain employee's practice {string}")
    public void returnMustContainEmployeeSPractice(String practice) {
        assertEquals(practice, employeeDTOResponse.getBody().getPractice());
        System.out.println(employeeDTOResponse.getBody().getPractice());
    }

    @When("I delete the employee with code {int}")
    public void iDeleteTheEmployeeWithCode(int code) {
        String apiUrl = "http://localhost:8080/api/employees/88";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);
        response = restTemplate.exchange(apiUrl, HttpMethod.DELETE, request, String.class);
    }

}
