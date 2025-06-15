package io.mac.employeetracker.employee;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy.Content;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EmployeeEndToEndTest {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeRepository employeeRepository;

    private ArrayList<Employee> employees = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        RestAssured.port = this.port;

        this.employeeRepository.deleteAll();
        this.employees.clear();

        // "firstName": "Ron",
        // "middlename": "PF",
        // "lastName": "Kwan",
        // "email": "ronaldkwan90@gmail.com",
        // "mobile": "0432442263",
        // "address": "123 king street",
        // "contractType": "PERMANENT",
        // "startDate": "1993-05-30",
        // "endDate" : "2000-06-30",
        // "employmentType": "FULL_TIME",
        // "hoursPerWeek": 40

        Employee employee1 = new Employee();
        employee1.setFirstName("Ron");
        employee1.setMiddlename("PF");
        employee1.setLastName("KN");
        employee1.setEmail("rk@gmail.com");
        employee1.setMobile("0432442266");
        employee1.setAddress("123 king street");
        employee1.setContractType(Employee.Contract.PERMANENT);
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            employee1.setStartDate(sdf.parse("1993-05-30"));
            employee1.setEndDate(sdf.parse("2000-06-30"));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        employee1.setEmploymentType(Employee.EmploymentType.FULL_TIME);
        employee1.setHoursPerWeek(40);
        this.employeeRepository.save(employee1);
        this.employees.add(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Jae");
        employee2.setMiddlename("PF");
        employee1.setLastName("KW");
        employee2.setEmail("jae@gmail.com");
        employee2.setMobile("0432442264");
        employee2.setAddress("234 king street");
        employee2.setContractType(Employee.Contract.PERMANENT);
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            employee2.setStartDate(sdf.parse("2022-05-30"));
            employee2.setEndDate(sdf.parse("2020-06-30"));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        employee2.setEmploymentType(Employee.EmploymentType.PART_TIME);
        employee2.setHoursPerWeek(50);
        this.employeeRepository.save(employee2);
        this.employees.add(employee2);
    }

    @Test
    public void getAllEmployees_EmployeesInDB_ReturnsSuccess() {
        // arrange
        // act
        given().when().get("/employees").then().statusCode(HttpStatus.OK.value()).body("$", hasSize(2));
        // assert
    }

    @Test
    public void getEmployeeById_ValidId_ReturnSuccessAndCorrectInformation() {
        Employee existingEmployee = this.employees.get(1);
        given()
                .when()
                .get("/employees/" + existingEmployee.getId())
                .then().statusCode(HttpStatus.OK.value())
                .body("firstName", equalTo("Jae"))
                .body("middlename", equalTo("PF"))
                .body("mobile", equalTo("0432442264"))
                .body("address", equalTo("234 king street"))
                .body("contractType", equalTo("PERMANENT"))
                .body("startDate", equalTo("2022-05-29T14:00:00.000+00:00"))
                .body("endDate", equalTo("2020-06-29T14:00:00.000+00:00"))
                .body("employmentType", equalTo("PART_TIME"))
                .body("hoursPerWeek", equalTo(50));
    }

    @Test
    public void getEmployeeById_InvalidId_Returns404Error() {
        long invalidId = 123456789L;

        given()
                .when()
                .get("/employees/" + invalidId)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createEmployee_WhenPassedValidData_Created() {

        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Jae");
        data.put("middlename", "PF");
        data.put("lastName", "PL");
        data.put("email", "PL@gmail.com");
        data.put("mobile", "0432442264");
        data.put("address", "234 king street");
        data.put("contractType", "PERMANENT");
        data.put("startDate", "2022-05-29");
        data.put("endDate", "2020-06-29");
        data.put("employmentType", "PART_TIME");
        data.put("hoursPerWeek", 50);
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when().post("/employees")
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void createEmployee_InvalidData_BadRequest() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("firstName", "Jae");
        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/employees")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createEmployee_EmptyRequestBody_BadRequest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .post("/employees")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
