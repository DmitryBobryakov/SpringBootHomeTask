package com.netcracker.edu.db.employee.dao;

import com.netcracker.edu.db.employee.model.Employee;


import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;


import java.math.BigInteger;
import java.util.List;

/**
 * TODO: Implement me
 */
@Repository
public class EmployeeDaoJpaImpl implements EmployeeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Employee getEmployeeById(BigInteger employeeId) {
		return entityManager.find(Employee.class, employeeId);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		try {
			entityManager.persist(employee);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean updateEmployee(Employee employee) {
		try {
			entityManager.merge(employee);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean deleteEmployee(Employee employee) {
		try {
            Object identifier =
                    entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(employee);

			Employee same=entityManager.find(Employee.class,identifier);
			entityManager.remove(same);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public List<Employee> getEmployeesBySurname(String surname) {
		Query query = entityManager.createQuery(QueryConsts.selectWithSurname);
		query.setParameter("surname", surname);
		List<Employee> ret = query.getResultList();

		return ret;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long departmentId) {
		Query query = entityManager
				.createQuery(QueryConsts.selectWithDepartment);
		query.setParameter("department_id", departmentId);
		List<Employee> ret = query.getResultList();

		return ret;
	}

	@Override
	public List<Employee> getEmployeesWithGreaterSalary(long thresholdSalary) {
		Query query = entityManager
				.createQuery(QueryConsts.selectWithGreaterSalary);
		query.setParameter("eSalary", thresholdSalary);
		List<Employee> ret = query.getResultList();

		return ret;
	}

	@Override
	public List<Employee> getAllEmployees() {
		Query query = entityManager.createQuery(QueryConsts.selectAll);
		List<Employee> ret = query.getResultList();
		return ret;
	}
}
