package io.mac.employeetracker.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.mac.employeetracker.common.exceptions.NotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody CreateEmployeeDTO data) {
        Employee saved = this.employeeService.create(data);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
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
    @PatchMapping("{id}")
    public ResponseEntity<Employee> updateById(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeDTO data)
            throws NotFoundException {
        Optional<Employee> result = this.employeeService.updateById(id, data);
        Employee updated = result.orElseThrow(
                () -> new NotFoundException("Could not updated Employee with id " + id + " , it does not exists"));

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/list/{filterField}/{filterValue}")
    public ResponseEntity<List<Employee>> getListByCriteria(
            @PathVariable String filterField,
            @PathVariable String filterValue) throws NotFoundException {

        Optional<List<Employee>> foundEmployees;

        if ("none".equals(filterField) || "all".equals(filterValue) ||
                filterField.isEmpty() || filterValue.isEmpty()) {
            foundEmployees = Optional.of(this.employeeService.getAll());
        } else {
            foundEmployees = this.employeeService.findListByCategory(filterField, filterValue);
        }

        if (foundEmployees.isPresent()) {
            return new ResponseEntity<>(foundEmployees.get(), HttpStatus.OK);
        }

        throw new NotFoundException("No employees found");
    }
}
