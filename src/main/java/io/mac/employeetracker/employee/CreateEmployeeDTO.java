package io.mac.employeetracker.employee;
import java.sql.Date;
import io.mac.employeetracker.employee.Employee.Contract;
import io.mac.employeetracker.employee.Employee.EmploymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateEmployeeDTO {

    @NotBlank
    private String firstName;

    private String middlename;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String mobile;

    @NotBlank
    private String address;

    @NotNull
    private Contract contractType;

    @NotNull
    private Date startDate;

    private Date endDate;

    @NotNull
    private EmploymentType employmentType;

    private Integer hoursPerWeek;

    private String profileImageUrl;

    public String getprofileImageUrl() {
        return profileImageUrl;
    }

    public void setprofileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

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
