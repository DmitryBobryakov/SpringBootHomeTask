package com.netcracker.edu.db.employee.service;

import com.netcracker.edu.db.employee.dao.EmployeeDao;
import com.netcracker.edu.db.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Override

    public Employee getEmployeeById(BigInteger employeeId) {
        return employeeDao.getEmployeeById(employeeId);
    }

    @Override
    @Transactional
    public boolean addEmployee(Employee employee) {
        return employeeDao.addEmployee(employee);
    }

    @Override
    @Transactional
    public boolean updateEmployee(Employee employee) {
        return employeeDao.updateEmployee(employee);
    }

    @Override
    @Transactional
    public boolean deleteEmployee(Employee employee) {
        return employeeDao.deleteEmployee(employee);
    }

    @Override
    @Transactional
    public boolean swapEmployeesPositionsAndSalaries(BigInteger promotedId, BigInteger demotedId) {
        try{
            Employee promotedEmpl=employeeDao.getEmployeeById(promotedId);
            Employee demotedEmpl=employeeDao.getEmployeeById(demotedId);

            long buffSalary=promotedEmpl.getSalary();
            String buffPosition=promotedEmpl.getPosition();

            promotedEmpl.setSalary(demotedEmpl.getSalary());
            promotedEmpl.setPosition(demotedEmpl.getPosition());

            demotedEmpl.setSalary(buffSalary);
            demotedEmpl.setPosition(buffPosition);

           employeeDao.updateEmployee(promotedEmpl);
           employeeDao.updateEmployee(demotedEmpl);

            return true;
        } catch (Exception e){
            return false;
        }


    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }



}
