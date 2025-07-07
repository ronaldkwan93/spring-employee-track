# Employee Tracker

[![Automated Docker Build](https://github.com/ronaldkwan93/spring-employee-track/actions/workflows/docker-build.yml/badge.svg)](https://github.com/ronaldkwan93/spring-employee-track/actions/workflows/docker-build.yml)

## To get started, Run

```bash
mvn spring-boot:run
```

## Backend

### Libraries

- Model Mapper (3.2.2)
- ModelMapper Config

### Endpoints

- GET - get all employees {/employees}
- GET - get employee by id {/employees/:id}
- POST - create new employee {/employees}
- PATCH - update employee by id {/employees/:id}
- DELETE - delete employee by id {/employees/:id}
