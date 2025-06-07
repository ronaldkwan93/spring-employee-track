package io.mac.employeetracker.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public Employee create(CreateEmployeeDTO data) {
        // Employee newEmployee = new Employee();
        // newEmployee.setFirstName(data.getFirstName());
        // newEmployee.setMiddlename(data.getMiddlename());
        // newEmployee.setLastName(data.getLastName());
        // newEmployee.setEmail(data.getEmail());
        // newEmployee.setMobile(data.getMobile());
        // newEmployee.setAddress(data.getAddress());
        // newEmployee.setContractType(data.getContractType());
        // newEmployee.setStartDate(data.getStartDate());
        // newEmployee.setEndDate(data.getEndDate());
        // newEmployee.setEmploymentType(data.getEmploymentType());
        // newEmployee.setHoursPerWeek(data.getHoursPerWeek());

        // implemented with ModelMapper library
        Employee newEmployee = modelMapper.map(data, Employee.class);
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

    public Optional<Employee> updateById(Long id, UpdateEmployeeDTO data) {
        Optional<Employee> foundEmployee = this.findById(id);
        if (foundEmployee.isEmpty()) {
            return foundEmployee;
        }

        // Employee employeefromDB = foundEmployee.get();
        // if(data.getFirstName() != null) {
        // employeefromDB.setFirstName(data.getFirstName().trim());
        // }
        // if(data.getMiddlename() != null) {
        // employeefromDB.setMiddlename(data.getMiddlename().trim());
        // }
        // if(data.getLastName() != null) {
        // employeefromDB.setLastName(data.getLastName().trim());
        // }
        // if(data.getEmail() != null) {
        // employeefromDB.setEmail(data.getEmail().trim());
        // }
        // if(data.getMobile() != null) {
        // employeefromDB.setMobile(data.getMobile().trim());
        // }
        // if(data.getAddress() != null) {
        // employeefromDB.setAddress(data.getAddress().trim());
        // }
        // if(data.getContractType() != null) {
        // employeefromDB.setContractType(data.getContractType());
        // }
        // if(data.getStartDate() != null) {
        // employeefromDB.setStartDate(data.getStartDate());
        // }
        // if(data.getEndDate() != null) {
        // employeefromDB.setEndDate(data.getEndDate());
        // }
        // if(data.getEmploymentType() != null) {
        // employeefromDB.setEmploymentType(data.getEmploymentType());
        // }
        // if(data.getHoursPerWeek() != null) {
        // employeefromDB.setHoursPerWeek(data.getHoursPerWeek());
        // }

        // implemented Model Mapper library

        Employee employeefromDB = foundEmployee.get();

        modelMapper.map(data, employeefromDB);

        this.employeeRepository.save(employeefromDB);
        return Optional.of(employeefromDB);
    }

}
