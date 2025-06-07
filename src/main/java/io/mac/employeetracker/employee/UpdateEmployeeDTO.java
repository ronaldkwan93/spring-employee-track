package io.mac.employeetracker.employee;

import java.sql.Date;
import io.mac.employeetracker.employee.Employee.Contract;
import io.mac.employeetracker.employee.Employee.EmploymentType;
import jakarta.validation.constraints.Pattern;

public class UpdateEmployeeDTO {

    @Pattern(regexp = ".*\\S.*", message = "firstName cannot be empty")
    private String firstName;
    @Pattern(regexp = ".*\\S.*", message = "middlename cannot be empty")
    private String middlename;
    @Pattern(regexp = ".*\\S.*", message = "lastName cannot be empty")
    private String lastName;
    @Pattern(regexp = ".*\\S.*", message = "email cannot be empty")
    private String email;
    @Pattern(regexp = ".*\\S.*", message = "mobile cannot be empty")
    private String mobile;
    @Pattern(regexp = ".*\\S.*", message = "address cannot be empty")
    private String address;

    private Contract contractType;

    private Date startDate;

    private Date endDate;

    private EmploymentType employmentType;

    private Integer hoursPerWeek;


    public String getFirstName() {
        return firstName;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public Contract getContractType() {
        return contractType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

}
