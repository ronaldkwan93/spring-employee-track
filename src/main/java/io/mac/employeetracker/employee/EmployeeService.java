package io.mac.employeetracker.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(CreateEmployeeDTO data) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(data.getFirstName());
        newEmployee.setMiddlename(data.getMiddlename());
        newEmployee.setLastName(data.getLastName());
        newEmployee.setEmail(data.getEmail());
        newEmployee.setMobile(data.getMobile());
        newEmployee.setAddress(data.getAddress());
        newEmployee.setContractType(data.getContractType());
        newEmployee.setStartDate(data.getStartDate());
        newEmployee.setEndDate(data.getEndDate());
        newEmployee.setEmploymentType(data.getEmploymentType());
        newEmployee.setHoursPerWeek(data.getHoursPerWeek());
        Employee savedEmployee = this.employeeRepository.save(newEmployee);
        return savedEmployee;
    }

    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    public Optional<Employee> findById(Long id) {
        Optional<Employee> foundEmployee = this.employeeRepository.findById(id);
        return foundEmployee;
    }

    public void delete(Employee employee) {
        this.employeeRepository.delete(employee);
    }

    public void update(Employee employee) { 
        this.employeeRepository.save(employee);
    }

}
