package com.netcracker.edu.db.employee.dao;

public class QueryConsts {
	public static final String selectWithSurname = new String(
			"SELECT e FROM Employee e WHERE e.surname = :surname");
	public static final String selectWithDepartment = new String(
			"SELECT e FROM Employee e WHERE e.departmentId = :department_id");
	public static final String selectWithGreaterSalary = new String(
			"SELECT e FROM Employee e WHERE e.salary > :eSalary");
	public static final String selectAll = new String(
			"SELECT e FROM Employee e");

}
