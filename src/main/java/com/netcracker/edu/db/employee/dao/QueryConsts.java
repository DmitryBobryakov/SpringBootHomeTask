package com.netcracker.edu.db.employee.dao;

public class QueryConsts {
	public static final String SELECT_WITH_SURNAME = new String(
			"SELECT e FROM Employee e WHERE e.surname = :surname");
	public static final String SELECT_WITH_DEPARTMENT = new String(
			"SELECT e FROM Employee e WHERE e.departmentId = :department_id");
	public static final String SELECT_WITH_GREATER_SALARY = new String(
			"SELECT e FROM Employee e WHERE e.salary > :eSalary");
	public static final String SELECT_ALL = new String(
			"SELECT e FROM Employee e");

}
