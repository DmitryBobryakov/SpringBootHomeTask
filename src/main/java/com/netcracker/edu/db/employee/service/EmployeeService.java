package com.netcracker.edu.db.employee.service;

import com.netcracker.edu.db.employee.model.Employee;

import java.math.BigInteger;
import java.util.List;

public interface EmployeeService {

    /**
     * Get employee by specified id
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
     * Update existent employee
     * @param employee
     * @return true or false depending on operation result
     */
    boolean updateEmployee(Employee employee);

    /**
     * Delete employee
     * @param employee

     * @return true or false depending on operation result
     */
    boolean deleteEmployee(Employee employee);

    /**
     * Swap positions and salaries of employees
     * Imagine the situation in a parallel universe where employee can be demoted)
     * @param promotedId
     * @param demotedId
     * @return
     */
    boolean swapEmployeesPositionsAndSalaries(BigInteger promotedId, BigInteger demotedId);

    /**
     * Select all employees from DB
     * @return list of {@link Employee} or empty list
     */
    List<Employee> getAllEmployees();


}
