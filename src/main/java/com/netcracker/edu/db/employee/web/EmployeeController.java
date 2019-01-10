package com.netcracker.edu.db.employee.web;

import com.netcracker.edu.db.employee.model.Employee;
import com.netcracker.edu.db.employee.service.EmployeeService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigInteger;
import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") BigInteger employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @DeleteMapping("/delete/{emplToDelete}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("emplToDelete") BigInteger emplToDelete){

        Employee emp=employeeService.getEmployeeById(emplToDelete);


        if (emp == null) {
            System.out.println("Unable to delete. User with id " + emplToDelete + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        employeeService.deleteEmployee(emp);
        return new ResponseEntity<>(emp,HttpStatus.NO_CONTENT);

    }

    @PutMapping("/swap/{promotedId}/{devotedId}")
    public ResponseEntity<Employee> swapEmplSalaryAndPosition(@PathVariable("promotedId") BigInteger promotedId,
                                                              @PathVariable("devotedId")BigInteger devotedId){
        employeeService.swapEmployeesPositionsAndSalaries(promotedId,devotedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateByEmpl(@PathVariable BigInteger id,@RequestBody Employee newEmpl){
            Employee oldEmpl=employeeService.getEmployeeById(id);
            oldEmpl.setName(newEmpl.getName());
            oldEmpl.setSurname(newEmpl.getSurname());
            oldEmpl.setPosition(newEmpl.getPosition());
            oldEmpl.setSalary(newEmpl.getSalary());
            oldEmpl.setDepartmentId(newEmpl.getDepartmentId());

            employeeService.updateEmployee(oldEmpl);
            return new ResponseEntity<>(oldEmpl,HttpStatus.OK);
     }

    @PostMapping("/add")
    public ResponseEntity<Employee> addNewEmpl(@RequestBody Employee newEmpl){
        employeeService.addEmployee(newEmpl);
        return new ResponseEntity<>(newEmpl,HttpStatus.OK);
    }


}