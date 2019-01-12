package com.netcracker.edu.db.employee.dao;

import com.netcracker.edu.db.employee.model.Employee;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

/**
 * id name surname position departmentId salary
 * 1 name1 surname1 boss 1 100
 * 2 name2 surname2 big boss 1 200
 * 3 name3 surname2 big boss 1 300
 * 4 name4 surname3 boss 2 100
 * 5 name5 surname4 boss 2 100
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeDaoJpaTest {

	@Autowired
	private EmployeeDao employeeDao;

	private static EmbeddedDatabase database;

	@BeforeClass
	public static void setup() throws SQLException {
		database = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2).setName("testdb")
				.addScript("createTestDb.sql").build();
	}

	@Before
	public void setupData() throws Exception {
		database.getConnection().createStatement()
				.execute(getContentByResourceRelativePath("deleteData.sql"));
		database.getConnection().createStatement()
				.execute(getContentByResourceRelativePath("fillData.sql"));
	}

	@AfterClass
	public static void tearDown() {
		database.shutdown();
	}

	@Test
	public void testGetEmployeeByIdExist() {
		Employee actual;
		actual = employeeDao.getEmployeeById(BigInteger.valueOf(1L));
		Employee expected = new Employee(BigInteger.valueOf(1L), "name1",
				"surname1", "boss", 1L, 100);

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGetEmployeeByIdNotExist() {
		Employee actual = employeeDao.getEmployeeById(BigInteger.valueOf(10L));

		Assert.assertNull(actual);
	}

	@Test
	@Transactional
	public void testAddEmployeeWork() {
		Assert.assertTrue(employeeDao.addEmployee(
				new Employee(null, "Dmitry", "Bobryakov", "Dev", 1L, 100L)));
	}

	@Test
	@Transactional
	public void testUpdateEmployeeWork() {
		Employee empl=employeeDao.getEmployeeById(BigInteger.valueOf(1));
		empl.setName("Dmitry");
		empl.setSurname("Bobryakov");

		Assert.assertTrue(
				employeeDao.updateEmployee(empl));
	}

	@Test
	@Transactional
	public void testDeleteEmployeeWork1() {
		Assert.assertTrue(employeeDao.deleteEmployee(new Employee(
				BigInteger.valueOf(1L), "name1", "surname1", "boss", 1L, 100)));

	}

	@Test
	@Transactional
	public void testDeleteEmployeeWork2() {
		Assert.assertTrue(employeeDao.deleteEmployee(
				employeeDao.getEmployeeById(BigInteger.valueOf(1L))));

	}

	@Test
	public void testGetBySurnameTrue() {
		List<Employee> expected = new LinkedList<>();
		expected.add(new Employee(BigInteger.valueOf(1L), "name1", "surname1",
				"boss", 1L, 100));

		List<Employee> actual = employeeDao.getEmployeesBySurname("surname1");
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testGetBySurnameOfNonexistentEmployee() {
		List<Employee> expected = new LinkedList<>();


		List<Employee> actual = employeeDao.getEmployeesBySurname("surname10");
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testGetByDepartmentIdTrue() {
		List<Employee> expected = new LinkedList<>();
		expected.add(new Employee(BigInteger.valueOf(1L), "name1", "surname1",
				"boss", 1L, 100));
		expected.add(new Employee(BigInteger.valueOf(2L), "name2", "surname2",
				"big boss", 1L, 200));
		expected.add(new Employee(BigInteger.valueOf(3L), "name3", "surname2",
				"big boss", 1L, 300));

		List<Employee> actual = employeeDao.getEmployeesByDepartmentId(1);
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testGetByDepartmentIdOfNonexistentDepartmentId() {
		List<Employee> expected = new LinkedList<>();


		List<Employee> actual = employeeDao.getEmployeesByDepartmentId(100);
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testGetWithGreaterSalaryTrue() {
		List<Employee> expected = new LinkedList<>();

		expected.add(new Employee(BigInteger.valueOf(3L), "name3", "surname2",
				"big boss", 1L, 300));

		List<Employee> actual = employeeDao.getEmployeesWithGreaterSalary(200);
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void testGetWithGreaterSalaryWithSoMuchSalary() {
		List<Employee> expected = new LinkedList<>();

		expected.add(new Employee(BigInteger.valueOf(3L), "name3", "surname2",
				"big boss", 1L, 300));

		List<Employee> actual = employeeDao.getEmployeesWithGreaterSalary(4000);
		Assert.assertNotEquals(expected, actual);

	}

	@Test
	public void testGetAllTrue() {
		List<Employee> expected = new LinkedList<>();
		expected.add(new Employee(BigInteger.valueOf(1L), "name1", "surname1",
				"boss", 1L, 100));
		expected.add(new Employee(BigInteger.valueOf(2L), "name2", "surname2",
				"big boss", 1L, 200));
		expected.add(new Employee(BigInteger.valueOf(3L), "name3", "surname2",
				"big boss", 1L, 300));
		expected.add(new Employee(BigInteger.valueOf(4L), "name4", "surname4",
				"boss", 2L, 100));
		expected.add(new Employee(BigInteger.valueOf(5L), "name5", "surname5",
				"boss", 2L, 100));

		List<Employee> actual = employeeDao.getAllEmployees();
		Assert.assertNotEquals(expected, actual);

	}

	private String getContentByResourceRelativePath(String path)
			throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(path).getFile());

		return new String(
				Files.readAllBytes(Paths.get(file.getAbsolutePath())));
	}
}
