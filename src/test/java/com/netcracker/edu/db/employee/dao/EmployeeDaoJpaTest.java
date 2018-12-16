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

/**
 *  id   name    surname     position    departmentId    salary
 *  -----------------------------------------------------------
 *  1    name1   surname1    boss        1               100
 *  2    name2   surname2    big boss    1               200
 *  3    name3   surname2    big boss    1               300
 *  4    name4   surname3    boss        2               100
 *  5    name5   surname4    boss        2               100
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
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb")
                .addScript("createTestDb.sql")
                .build();
    }

    @Before
    public void setupData() throws Exception {
        database.getConnection().createStatement().execute(getContentByResourceRelativePath("deleteData.sql"));
        database.getConnection().createStatement().execute(getContentByResourceRelativePath("fillData.sql"));
    }

    @AfterClass
    public static void tearDown() {
        database.shutdown();
    }

    @Test
    public void testGetEmployeeByIdExist() {
        Employee actual = employeeDao.getEmployeeById(BigInteger.valueOf(1L));
        Employee expected = new Employee(
                BigInteger.valueOf(1L), "name1", "surname1", "boss", 1L, 100);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetEmployeeByIdNotExist() {
        Employee actual = employeeDao.getEmployeeById(BigInteger.valueOf(10L));

        Assert.assertNull(actual);
    }
    
	@Test
	public void testAddEmployeeWork() {
		Assert.assertTrue(
				employeeDao.addEmployee(new Employee(BigInteger.valueOf(1L),
						"Dmitry", "Bobryakov", "Dev", 1L, 100L)));
	}

    private String getContentByResourceRelativePath(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());

        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
    }
}
