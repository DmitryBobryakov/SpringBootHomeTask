package com.netcracker.edu.db.employee.dao;

import com.netcracker.edu.db.employee.model.Employee;

import java.util.List;

public interface EmployeeDao {

    /**
     * Select employee from DB by specified id
     * @param employeeId
     * @return {@link Employee}
     */
    Employee getEmployeeById(long employeeId);

    /**
     * Select list of employees from DB by specified secondNames
     * @param surname
     * @return list of {@link Employee}
     */
    List<Employee> getEmployeesBySurname(String surname);

    /**
     * Select employees from DB by specified department Id
     * @param departmentId
     * @return list of {@link Employee}
     */
    List<Employee> getEmployeesByDepartmentId(long departmentId);

    /**
     * Select employees from DB which salary is higher or equal to specified
     * @param thresholdSalary
     * @return list of {@link Employee}
     */
    List<Employee> getEmployeeWithGreaterSalary(long thresholdSalary);

    /**
     * Select all employees from DB
     * @return list of {@link Employee}
     */
    List<Employee> getAllEmployees();

}
