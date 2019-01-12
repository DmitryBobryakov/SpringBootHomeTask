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

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") BigInteger employeeId) {
        Employee retEmpl=employeeService.getEmployeeById(employeeId);
        if(retEmpl.getId()==BigInteger.valueOf(-1)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(retEmpl);
    }

    @DeleteMapping("/{emplToDelete}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("emplToDelete") BigInteger emplToDelete){
        try{
            Employee emp=employeeService.getEmployeeById(emplToDelete);


            if (emp == null) {
                System.out.println("Unable to delete. User with id " + emplToDelete + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            employeeService.deleteEmployee(emp);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//Not found entity to update
        }


    }

    @PutMapping("/{promotedId}/{devotedId}")
    public ResponseEntity<Employee> swapEmplSalaryAndPosition(@PathVariable("promotedId") BigInteger promotedId,
                                                              @PathVariable("devotedId")BigInteger devotedId){
        if(promotedId==null||devotedId==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        employeeService.swapEmployeesPositionsAndSalaries(promotedId,devotedId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateByEmpl(@PathVariable BigInteger id,@RequestBody Employee newEmpl){
        try{
            Employee oldEmpl=employeeService.getEmployeeById(id);
            oldEmpl.setName(newEmpl.getName());
            oldEmpl.setSurname(newEmpl.getSurname());
            oldEmpl.setPosition(newEmpl.getPosition());
            oldEmpl.setSalary(newEmpl.getSalary());
            oldEmpl.setDepartmentId(newEmpl.getDepartmentId());

            employeeService.updateEmployee(oldEmpl);
            return new ResponseEntity<>(oldEmpl,HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);//Not found entity to update
        }

     }

    @PostMapping("/")
    public ResponseEntity<Employee> addNewEmpl(@RequestBody Employee newEmpl){
        boolean ret=employeeService.addEmployee(newEmpl);
        if(ret==false){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newEmpl,HttpStatus.OK);
    }


}