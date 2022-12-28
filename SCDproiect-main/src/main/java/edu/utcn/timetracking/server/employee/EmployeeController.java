package edu.utcn.timetracking.server.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee create(@RequestBody Employee employee)
    {
        return employeeService.create(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.findAllEmployees();
    }
}
