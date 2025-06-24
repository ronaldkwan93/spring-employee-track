package io.mac.employeetracker.employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.mac.employeetracker.common.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final ObjectMapper objectMapper;
    private EmployeeService employeeService;
    private Validator validator;

    EmployeeController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;

    }

    @CrossOrigin
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> create(
            @RequestParam("employee") String employeeJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) throws JsonProcessingException {

        CreateEmployeeDTO data = objectMapper.readValue(employeeJson, CreateEmployeeDTO.class);

        Employee saved = employeeService.create(data, photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> allEmployees = this.employeeService.getAll();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/statistics")
    public EmployeeStatsDTO getEmployeeStats() {
        return this.employeeService.getStatistics();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getbyId(@PathVariable Long id) throws NotFoundException {
        // check if employee exists
        Optional<Employee> foundEmployee = this.employeeService.findById(id);
        // if yes, return employee
        if (foundEmployee.isPresent()) {
            return new ResponseEntity<>(foundEmployee.get(), HttpStatus.OK);
        }

        // if not throw notfound exception
        throw new NotFoundException("Employee with id " + id + " does not exist");

    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws NotFoundException {
        Optional<Employee> existingEmployee = this.employeeService.findById(id);
        if (existingEmployee.isPresent()) {
            this.employeeService.delete(existingEmployee.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException("Employee with id " + id + " does not exist");
    }

    @CrossOrigin
    @PatchMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Employee> updateById(
            @PathVariable Long id,
            @RequestParam("employee") String employeeJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo)
            throws JsonProcessingException, NotFoundException {

        UpdateEmployeeDTO data = objectMapper.readValue(employeeJson, UpdateEmployeeDTO.class);

        Optional<Employee> result = this.employeeService.updateById(id, data, photo);
        Employee updated = result.orElseThrow(
                () -> new NotFoundException("Could not update Employee with id " + id + " , it does not exist"));

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/list/{filterField}/{filterValue}")
    public ResponseEntity<List<Employee>> getListByCriteria(
            @PathVariable String filterField,
            @PathVariable String filterValue) {

        List<Employee> employees;

        if ("none".equals(filterField) || "all".equals(filterValue) ||
                filterField.isEmpty() || filterValue.isEmpty()) {
            employees = this.employeeService.getAll();
        } else {
            employees = this.employeeService.findListByCategory(filterField, filterValue);
        }

        return ResponseEntity.ok(employees);
    }
}
