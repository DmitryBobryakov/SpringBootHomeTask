package com.netcracker.edu.db.employee.ui;

import com.netcracker.edu.db.employee.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ControllerUtils {
    private static final Logger LOGGER = LogManager.getLogger();

    public static boolean isFilled(String id) {
        return id != null && !id.isEmpty();
    }

    public static BigInteger toBigInteger(String str) {
        try {
            return new BigInteger(str);
        } catch (NumberFormatException e) {
            LOGGER.warn("Error while converting input {} to BigInteger", str);
        }

        return null;
    }

    public static Employee getNonexistentEmployee() {
        Employee nonexistentEmployee = new Employee();
        nonexistentEmployee.setId(BigInteger.valueOf(-1L));

        return nonexistentEmployee;
    }

    public static List<Employee> getStubEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();

        Employee employee1 = new Employee();
        employee1.setId(BigInteger.ONE);
        employee1.setName("Duke");
        employee1.setSurname("Duke");
        employee1.setPosition("Java Mascot");
        employee1.setSalary(0L);

        Employee employee2 = new Employee();
        employee2.setId(BigInteger.TEN);
        employee2.setName("James");
        employee2.setSurname("Gosling");
        employee2.setPosition("Java creator");
        employee2.setSalary(Long.MAX_VALUE);

        employeeList.add(employee1);
        employeeList.add(employee2);

        return employeeList;
    }
}