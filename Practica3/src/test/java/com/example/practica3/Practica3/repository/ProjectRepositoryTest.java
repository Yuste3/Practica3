//package com.example.practica3.Practica3.repository;
//
//import com.example.practica3.model.Practice;
//import com.example.practica3.repository.EmployeeRepository;
//import com.example.practica3.repository.PracticeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Transactional
//public class ProjectRepositoryTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private PracticeRepository practiceRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @BeforeEach
//    public void setup() {
//        employeeRepository.deleteAll();
//        practiceRepository.deleteAll();
//    }
//
//    @Test
//    public void shouldGetAllPracticesSavedInDDBB() throws Exception {
//        Practice practice = new Practice("practiceTest");
//        Practice practice2 = new Practice("practiceTest2");
//        practiceRepository.saveAll(List.of(practice, practice2));
//
//        mockMvc.perform(get("/api/practices"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].code", is("practiceTest")))
//                .andExpect(jsonPath("$[1].code", is("practiceTest2")));
//    }
//
//    @Test
//    public void shouldGetPracticeSavedInDDBB() throws Exception {
//        Practice practice = new Practice("practiceTest");
//        practiceRepository.save(practice);
//
//        mockMvc.perform(get("/api/practices/practiceTest"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.code", is("practiceTest")));
//    }
//
//    @Test
//    public void shouldInsertPracticeInDDBB() throws Exception {
//        String practiceJson = "{\"code\":\"practiceTest\"}";
//
//        mockMvc.perform(post("/api/practices")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(practiceJson))
//                .andExpect(status().isNoContent());
//
//        Practice insertedPractice = practiceRepository.findById("practiceTest").orElse(null);
//        assertNotNull(insertedPractice);
//        assertEquals("practiceTest", insertedPractice.getName());
//    }
//
//    @Test
//    public void testUpdatePractice() throws Exception {
//        Practice practice = new Practice("practiceTest");
//        practiceRepository.save(practice);
//
//        String updatedPracticeJson = "{\"newCode\":\"newPracticeTest\"}";
//
//        mockMvc.perform(put("/api/practices/practiceTest")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(updatedPracticeJson))
//                .andExpect(status().isNoContent());
//
//        Practice updatedPractice = practiceRepository.findById("newPracticeTest").orElse(null);
//        assertNotNull(updatedPractice);
//        assertEquals("newPracticeTest", updatedPractice.getName());
//    }
//
//    @Test
//    public void testDeletePractice() throws Exception {
//        Practice practice = new Practice("practiceTest");
//        practiceRepository.save(practice);
//
//        mockMvc.perform(delete("/api/practices/practiceTest"))
//                .andExpect(status().isNoContent());
//
//        Practice deletedPractice = practiceRepository.findById("practiceTest").orElse(null);
//        assertNull(deletedPractice);
//    }
//}
