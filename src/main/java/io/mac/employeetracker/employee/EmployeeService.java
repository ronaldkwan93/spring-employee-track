package io.mac.employeetracker.employee;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.mac.employeetracker.S3Service.S3Service;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private S3Service s3Service;

    EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, S3Service s3Service) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.s3Service = s3Service;
    }

    public Employee create(CreateEmployeeDTO data, MultipartFile photo) {

        String photoUrl = null;

        if (photo != null && !photo.isEmpty()) {
            photoUrl = s3Service.uploadFile(photo);
        }

        Employee newEmployee = modelMapper.map(data, Employee.class);

        newEmployee.setProfileImageUrl(photoUrl);

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

    public Optional<Employee> updateById(Long id, UpdateEmployeeDTO data, MultipartFile photo) {
        Optional<Employee> foundEmployee = this.findById(id);
        if (foundEmployee.isEmpty()) {
            return foundEmployee;
        }

        Employee employeeFromDB = foundEmployee.get();

        modelMapper.map(data, employeeFromDB);

        if (photo != null && !photo.isEmpty()) {
            String photoUrl = s3Service.uploadFile(photo);
            employeeFromDB.setProfileImageUrl(photoUrl);
        }

        this.employeeRepository.save(employeeFromDB);
        return Optional.of(employeeFromDB);
    }

    public EmployeeStatsDTO getStatistics() {
        // refactored code to native query on repository
        // List<Employee> employees = this.employeeRepository.findAll();
        // LocalDate now = LocalDate.now();
        // LocalDate inOneMonth = now.plusDays(30);

        EmployeeStatsDTO stats = new EmployeeStatsDTO();
        // stats.total = employees.size();
        stats.total = this.employeeRepository.allEmployeeCount();
        stats.contract = this.employeeRepository.allContractCount();
        stats.permanent = this.employeeRepository.allPermanentCount();
        stats.fullTime = this.employeeRepository.allFullTimeCount();
        stats.partTime = this.employeeRepository.allPartTimeCount();
        stats.newHires = this.employeeRepository.allNewHireCount();
        stats.endingSoon = this.employeeRepository.allEndingSoonCount();

        // refactored code to native query on repository
        // for (Employee emp : employees) {
        // // if (emp.getStartDate() != null) {
        // // LocalDate empStartDate = emp.getStartDate().toInstant()
        // // .atZone(java.time.ZoneId.systemDefault())
        // // .toLocalDate();
        // // if (empStartDate.getMonthValue() == now.getMonthValue() &&
        // // empStartDate.getYear() == now.getYear()) {
        // // stats.newHires++;
        // // }
        // // // newHires
        // // }
        // // if (emp.getEndDate() != null) {
        // // LocalDate empEndDate = emp.getEndDate().toInstant()
        // // .atZone(java.time.ZoneId.systemDefault())
        // // .toLocalDate();
        // // if (empEndDate.isAfter(now) && empEndDate.isBefore(inOneMonth)) {
        // // stats.endingSoon++;
        // // }
        // // // endingSoon
        // // }
        // // if (emp.getContractType() == Contract.CONTRACT) {
        // // stats.contract++;
        // // // contract
        // // }
        // // if (emp.getContractType() == Contract.PERMANENT) {
        // // stats.permanent++;
        // // // permanent
        // // }
        // // if (emp.getEmploymentType() == EmploymentType.FULL_TIME)
        // // stats.fullTime++;
        // // // full time
        // // if (emp.getEmploymentType() == EmploymentType.PART_TIME)
        // // stats.partTime++;
        // // // part time
        // }
        return stats;
    }

    public List<Employee> findListByCategory(String filterField, String filterValue) {

        return switch (filterField) {
            case "contractType" ->
                this.employeeRepository.getALlByContractType(filterValue);
            case "employmentType" ->
                this.employeeRepository.getAllByEmploymentType(filterValue);
            case "newHires" -> this.employeeRepository.getAllNewHires();
            case "upcomingEnds" -> this.employeeRepository.getAllUpcomingContractEnds();
            default -> this.employeeRepository.findAll();
        };

        // refactored code to native query on repository
        // List<Employee> filteredEmployees =
        // this.employeeRepository.findAll().stream()
        // .filter(employee -> {
        // switch (filterField) {
        // case "contractType":
        // return employee.getContractType().name().equalsIgnoreCase(filterValue);
        // case "employmentType":
        // return employee.getEmploymentType().name().equalsIgnoreCase(filterValue);
        // case "newHires":
        // return checkIfNewHire(employee);
        // case "upcomingEnds":
        // return checkIfUpcomingEnd(employee);
        // default:
        // return false;
        // }
        // })
        // .collect(Collectors.toList());
        // return filteredEmployees.isEmpty() ? Optional.empty() :
        // Optional.of(filteredEmployees);
    }

    // refactored code to native query on repository
    // private boolean checkIfUpcomingEnd(Employee employee) {
    // if (employee.getEndDate() == null) {
    // return false;
    // }
    // LocalDate end = employee.getEndDate().toInstant()
    // .atZone(java.time.ZoneId.systemDefault())
    // .toLocalDate();
    // LocalDate now = LocalDate.now();
    // LocalDate inOneMonth = now.plusDays(30);
    // return end.isAfter(now) && end.isBefore(inOneMonth);
    // }

    // refactored code to native query on repository
    // private boolean checkIfNewHire(Employee employee) {
    // LocalDate start = employee.getStartDate().toInstant()
    // .atZone(java.time.ZoneId.systemDefault())
    // .toLocalDate();
    // LocalDate now = LocalDate.now();
    // return (start.getMonth() == now.getMonth() && start.getYear() ==
    // now.getYear());
    // }

}
