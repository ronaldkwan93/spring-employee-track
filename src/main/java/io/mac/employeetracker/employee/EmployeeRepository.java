package io.mac.employeetracker.employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT COUNT(*) FROM employees", nativeQuery = true)
    long allEmployeeCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE contract_type = 'CONTRACT'", nativeQuery = true)
    long allContractCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE contract_type = 'PERMANENT'", nativeQuery = true)
    long allPermanentCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE employment_type = 'FULL_TIME'", nativeQuery = true)
    long allFullTimeCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE employment_type = 'PART_TIME'", nativeQuery = true)
    long allPartTimeCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE MONTH(start_date) = MONTH(CURDATE()) AND YEAR(start_date) = YEAR(CURDATE())", nativeQuery = true)
    long allNewHireCount();

    @Query(value = "SELECT COUNT(*) FROM employees WHERE end_date > CURDATE() AND end_date <= Date_ADD(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
    long allEndingSoonCount();

    @Query(value = "SELECT * FROM employees WHERE MONTH(start_date) = MONTH(CURDATE()) AND YEAR(start_date) = YEAR(CURDATE())", nativeQuery = true)
    List<Employee> getAllNewHires();

    @Query(value = "SELECT * FROM employees WHERE end_date > CURDATE() AND end_date <= Date_ADD(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
    List<Employee> getAllUpcomingContractEnds();

    @Query(value = "SELECT * FROM employees WHERE contract_type = ?1 ", nativeQuery = true)
    List<Employee> getALlByContractType(String filterValue);

    @Query(value = "SELECT * FROM employees WHERE employment_type = ?1 ", nativeQuery = true)
    List<Employee> getAllByEmploymentType(String filterValue);

}
