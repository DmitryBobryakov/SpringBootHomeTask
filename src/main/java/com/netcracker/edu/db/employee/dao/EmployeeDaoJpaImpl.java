package com.netcracker.edu.db.employee.dao;

import com.netcracker.edu.db.employee.model.Employee;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.*;


import java.math.BigInteger;
import java.util.IllegalFormatException;
import java.util.List;


@Repository
public class EmployeeDaoJpaImpl implements EmployeeDao {
	private static final Logger LOGGER = LogManager.getLogger();

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Employee getEmployeeById(BigInteger employeeId) {
	    try{
            LOGGER.info("getEmployeeById called with \""+employeeId+"\"");
            return entityManager.find(Employee.class, employeeId);
        }catch (IllegalArgumentException e){
	        LOGGER.warn("IllegalArgumentException in getEmployeeById appeared",e);
	        throw e;
        }

	}

	@Override
	public boolean addEmployee(Employee employee) {
		try {
			entityManager.persist(employee);
            LOGGER.info("addEmployee called with \""+employee+"\"");
			return true;
		} catch (Exception e) {
		    LOGGER.warn("Some exception appeared in addEmployee",e);
			throw e;
		}

	}

	@Override
	public boolean updateEmployee(Employee employee) {
		try {
		    if(entityManager.contains(employee)){
                entityManager.merge(employee);
                LOGGER.info("updateEmployee called with \""+employee+"\"");
                return true;
            }else{
                LOGGER.warn("No such Employee in updateEmpoyee appeared");
		        throw new IllegalStateException("No such Employee");//Not found entity to update
            }

		} catch (IllegalArgumentException e) {
		    LOGGER.warn("IllegalArgumentException in updateEmpoyee appeared",e);
			throw e;
		}

	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		try {
           	BigInteger identifier=employee.getId();

			Employee same=entityManager.find(Employee.class,identifier);
			if(same.equals(employee)) {
                entityManager.remove(same);
                LOGGER.info("deleteEmployee called with \""+employee+"\"");
                return true;
            }else{
                LOGGER.warn("No such Employee in deleteEmpoyee appeared");
			    throw new IllegalStateException("No such Employee");//Not found entity to update
            }
		} catch (IllegalArgumentException e) {
            LOGGER.warn("IllegalArgumentException in deleteEmployee appeared",e);
			throw e;
		}

	}

	@Override
	public List<Employee> getEmployeesBySurname(String surname) {
		try {
			Query query = entityManager.createQuery(QueryConsts.SELECT_WITH_SURNAME);
			query.setParameter("surname", surname);
			List<Employee> ret = query.getResultList();

            LOGGER.info("getEmployeesBySurname called with \""+surname+"\"");
            return ret;
		}catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeesBySurname appeared",e);
			throw e;
		}

	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long departmentId) {
		try{
			Query query = entityManager
					.createQuery(QueryConsts.SELECT_WITH_DEPARTMENT);
			query.setParameter("department_id", departmentId);
			List<Employee> ret = query.getResultList();
            LOGGER.info("getEmployeesByDepartmentId called with \""+departmentId+"\"");
			return ret;
		}catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeesByDepartmentId appeared",e);
			throw e;
		}

	}

	@Override
	public List<Employee> getEmployeesWithGreaterSalary(long thresholdSalary) {
		try {
			Query query = entityManager
					.createQuery(QueryConsts.SELECT_WITH_GREATER_SALARY);
			query.setParameter("eSalary", thresholdSalary);
			List<Employee> ret = query.getResultList();
            LOGGER.info("getEmployeesWithGreaterSalary called with \""+thresholdSalary+"\"");
			return ret;
		}catch (IllegalArgumentException e){
            LOGGER.warn("IllegalArgumentException in getEmployeesWithGreaterSalary appeared",e);
			throw e;
		}

	}

	@Override
	public List<Employee> getAllEmployees() {
		Query query = entityManager.createQuery(QueryConsts.SELECT_ALL);
		List<Employee> ret = query.getResultList();
        LOGGER.info("getAllEmployees called");
		return ret;
	}


}
