package com.example.practica3.controller;

import com.example.practica3.DTOs.ErrorResponse;
import com.example.practica3.DTOs.PracticeDTOs.PracticeDTO;
import com.example.practica3.DTOs.PracticeDTOs.UPracticeDTO;
import com.example.practica3.Mappers.PracticeMapper;
import com.example.practica3.model.Practice;
import com.example.practica3.service.IPracticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/practices")
public class PracticeAPI {
    private final IPracticeService service;
    @Autowired
    private final PracticeMapper practiceMapper;

    @Autowired
    public PracticeAPI(IPracticeService service, PracticeMapper practiceMapper) {
        this.service = service;
        this.practiceMapper = practiceMapper;
    }

    /**
     * Inserts a new practice into the system.
     *
     * @param practiceDTO PracticeDTO object representing the practice to insert, passed in the request body.
     * @return ResponseEntity containing the inserted practiceDTO and HTTP status 200 (OK) if successful,
     *         or HTTP status 400 (Bad Request) if insertion fails.
     */
    @PostMapping
    public ResponseEntity<ErrorResponse> insertPractice(@RequestBody PracticeDTO practiceDTO) {
        boolean validatedPractice = service.validatePractice(practiceDTO);
        if (validatedPractice) {
            Practice practice = practiceMapper.practiceDTOToPractice(practiceDTO);
            boolean response = service.insertNewPractice(practice);
            if (response) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(new ErrorResponse(15, "Error inserting practice"), HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(16, "Wrong value for practice"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves all practices in the system.
     *
     * @return ResponseEntity containing a list of all practices and HTTP status 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<PracticeDTO>> getAllPractices() {
        return new ResponseEntity<>(service.getAllPractices().stream().map(practiceMapper::practiceToDTO).toList(), HttpStatus.OK);
    }

    /**
     * Retrieves a specific practice by ID.
     *
     * @param code The ID of the practice to retrieve, passed as a path variable.
     * @return ResponseEntity containing the requested practice and HTTP status 200 (OK) if found,
     *         or HTTP status 404 (Not Found) if the practice doesn't exist.
     */
    @GetMapping("/{code}")
    public ResponseEntity<?> getPractice(@PathVariable String code) {
        Practice practice = service.getPractice(code);
        if (practice == null) {
            return new ResponseEntity<>(new ErrorResponse(17, "Practice not found"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(practiceMapper.practiceToDTO(practice), HttpStatus.OK);
        }
    }

    /**
     * Updates an existing practice in the system.
     *
     * @param code The ID of the practice to update, passed as a path variable.
     * @param newPracticeDTO PracticeDTO with the updated information, passed in the request body.
     * @return ResponseEntity containing the updated practice and HTTP status 200 (OK) if successful,
     *         HTTP status 404 (Not Found) if the practice doesn't exist,
     *         or HTTP status 400 (Bad Request) if the update fails.
     */
    @PutMapping("/{code}")
    public ResponseEntity<ErrorResponse> refreshPractice(@PathVariable String code, @RequestBody UPracticeDTO newPracticeDTO) {
        boolean validatedPractice = service.validatePractice(newPracticeDTO);
        if (validatedPractice) {
            Practice newPractice = practiceMapper.uPracticeDTOToPractice(newPracticeDTO);
            Practice oldPractice = service.getPractice(code);
            if (oldPractice == null) {
                return new ResponseEntity<>(new ErrorResponse(18, "Practice not found"), HttpStatus.NOT_FOUND);
            } else {
                boolean response = service.refreshPractice(oldPractice, newPractice);
                if (response) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(new ErrorResponse(19, "Error updating practice"), HttpStatus.CONFLICT);
                }
            }
        } else {
            return new ResponseEntity<>(new ErrorResponse(20, "Wrong value for new practice"), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a practice from the system.
     *
     * @param code The ID of the practice to delete, passed as a path variable.
     * @return ResponseEntity with HTTP status 200 (OK) if deletion is successful,
     *         or HTTP status 404 (Not Found) if the practice doesn't exist.
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<ErrorResponse> deletePractice(@PathVariable String code) {
        boolean response = service.deletePractice(code);
        return response ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(new ErrorResponse(21, "Error deleting practice"), HttpStatus.CONFLICT);
    }
}
