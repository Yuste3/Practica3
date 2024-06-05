package com.example.practica3.Practica3.repository;

import com.example.practica3.model.Employee;
import com.example.practica3.model.Practice;
import com.example.practica3.repository.EmployeeRepository;
import com.example.practica3.repository.PracticeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PracticeRepository practiceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    public void shouldGetAllEmployeesSavedInDDBB() throws Exception {
        Practice practice = new Practice("practiceTest");
        practiceRepository.save(practice);

        Employee employee1 = new Employee(1L, "Daniel Yuste", "Developer", practice);
        Employee employee2 = new Employee(2L, "Lucía Yuste", "Manager", practice);
        employeeRepository.saveAll(List.of(employee1, employee2));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Daniel Yuste")))
                .andExpect(jsonPath("$[1].name", is("Lucía Yuste")));
    }

    @Test
    public void shouldGetEmployeeSavedInDDBB() throws Exception {
        Practice practice = new Practice("practiceTest");
        practiceRepository.save(practice);

        Employee employee1 = new Employee(1L, "Daniel Yuste", "Developer", practice);
        employeeRepository.save(employee1);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Daniel Yuste")));
    }

    @Test
    public void shouldInsertEmployeeInDDBB() throws Exception {
        Practice practice = new Practice("practiceTest");
        practiceRepository.save(practice);
        String employeeJson = "{\"code\":3,\"name\":\"Dani\",\"role\":\"Tester\",\"practice\":\"practiceTest\"}";

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                        .andExpect(status().isNoContent());

        Employee insertedEmployee = employeeRepository.findById(3L).orElse(null);
        assertNotNull(insertedEmployee);
        assertEquals("Dani", insertedEmployee.getName());
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        Practice practice = new Practice("practiceTest");
        practiceRepository.save(practice);
        Practice practice2 = new Practice("nuevaPractice");
        practiceRepository.save(practice);

        Employee employee = new Employee(3L, "Dani", "Tester", practice);
        employeeRepository.save(employee);

        String updatedEmployeeJson = "{\"name\":\"NuevoNombre\",\"role\":\"NuevoRol\",\"practice\":\"nuevaPractice\"}";

        mockMvc.perform(put("/api/employees/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isNoContent());

        Employee updatedEmployee = employeeRepository.findById(3L).orElse(null);
        assertNotNull(updatedEmployee);
        assertEquals("NuevoNombre", updatedEmployee.getName());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        Practice practice = new Practice("practiceTest");
        practiceRepository.save(practice);

        Employee employee = new Employee(3L, "Dani", "Tester", practice);
        employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/3"))
                .andExpect(status().isNoContent());

        Employee deletedEmployee = employeeRepository.findById(3L).orElse(null);
        assertNull(deletedEmployee);
    }
}
