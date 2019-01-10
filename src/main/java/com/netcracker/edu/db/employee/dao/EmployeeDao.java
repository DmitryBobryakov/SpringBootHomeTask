package com.netcracker.edu.db.employee.dao;

import com.netcracker.edu.db.employee.model.Employee;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeDao{

	
	 
    
	
	
    /**
     * Select employee from DB by specified id
     * @param employeeId
     * @return {@link Employee} or null
     */
    Employee getEmployeeById(BigInteger employeeId);

    /**
     * Add new employee to database
     * @param employee
     * @return true or false depending on operation result
     */
    boolean addEmployee(Employee employee);

    /**
     * update existent employee in database
     * @param employee
     * @return true or false depending on operation result
     */
    boolean updateEmployee(Employee employee);

    /**
     * delete employee from database
     * @param employee
     * @return true or false depending on operation result
     */
  
    boolean deleteEmployee(Employee employee);

    /**
     * Select list of employees from DB by specified secondNames
     * @param surname
     * @return list of {@link Employee} or empty list
     */
    List<Employee> getEmployeesBySurname(String surname);

    /**
     * Select employees from DB by specified department Id
     * @param departmentId
     * @return list of {@link Employee} or empty list
     */
    List<Employee> getEmployeesByDepartmentId(long departmentId);

    /**
     * Select employees from DB which salary is higher or equal to specified
     * @param thresholdSalary
     * @return list of {@link Employee} or empty list
     */
    List<Employee> getEmployeesWithGreaterSalary(long thresholdSalary);

    /**
     * Select all employees from DB
     * @return list of {@link Employee} or empty list
     */
    List<Employee> getAllEmployees();



}
