package io.mac.employeetracker.employee;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.mac.employeetracker.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeDTO data) {
        Employee saved = this.employeeService.create(data);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> allEmployees = this.employeeService.getAll();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws NotFoundException {
        Optional<Employee> existingEmployee = this.employeeService.findById(id);
        if (existingEmployee.isPresent()) {
            this.employeeService.delete(existingEmployee.get());
            return new ResponseEntity<>("Employee id " + id + " deleted", HttpStatus.OK);
        }
        throw new NotFoundException("Book with id " + id + "does not exist");
    }

    // @PatchMapping("/{id}")
    // public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody data @PathVariable Long id ) {
    //     Optional<Employee> existingEmployee = this.employeeService.findById(id);
    //     if (existingEmployee.isPresent()) {
    //         this.employeeService.update(existingEmployee.get());
    //         return new ResponseEntity<>(existingEmployee.get(), HttpStatus.OK);
    //     }
    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }
}
