package com.netcracker.edu.db.employee.ui;

import com.netcracker.edu.db.employee.model.Employee;
import com.netcracker.edu.db.employee.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.List;

@Controller
public class EmployeePageController {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String EMPLOYEE_ATTR = "employee";

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public String greeting(Model model) {
        List<Employee> employeeList = employeeService.getAllEmployees();

        model.addAttribute("employees", employeeList);

        return "all";
    }

    @GetMapping("/find")
    public String findById(Model model, @RequestParam(value = "id", required = false) String id) {
        if (!ControllerUtils.isFilled(id)) {
            LOGGER.warn("Employee ID was not specified: {}", id);
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "find";
        }

        BigInteger employeeId = ControllerUtils.toBigInteger(id);


        Employee employee = employeeId != null ? employeeService.getEmployeeById(employeeId) : null;
        employee = employee != null ? employee : ControllerUtils.getNonexistentEmployee();

        model.addAttribute(EMPLOYEE_ATTR, employee);

        return "find";
    }

    @DeleteMapping("/delete")
    public String deleteEmpl(Model model, @RequestParam(value = "id", required = false) String id) {
        if (!ControllerUtils.isFilled(id)) {
            LOGGER.warn("Employee ID was not specified: {}", id);
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "delete";
        }

        Employee emplToDelete = employeeService.getEmployeeById(ControllerUtils.toBigInteger(id));
        employeeService.deleteEmployee(emplToDelete);
        model.addAttribute("emplToDelete", emplToDelete);

        List<Employee> employeeList = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employeeList);

        return "delete";
    }

    @GetMapping("/update")
    public String updateEmpl(@RequestBody Employee newEmpl, Model model) {
        if (newEmpl == null) {
            LOGGER.warn("Employee was not specified: {}");
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "update";
        }
        employeeService.updateEmployee(newEmpl);

        Employee oldEmpl = employeeService.getEmployeeById(newEmpl.getId());
        model.addAttribute("oldEmployee", oldEmpl);

        model.addAttribute("newEmployee", newEmpl);
        List<Employee> employeeList = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employeeList);

        return "update";
    }

    @GetMapping("/create")
    public String createEmpl(@RequestBody Employee newEmpl, Model model) {
        if (newEmpl == null) {
            LOGGER.warn("Wrong new Employee");
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "create";
        }

        employeeService.addEmployee(newEmpl);
        model.addAttribute("addedEmpl", newEmpl);

        List<Employee> employeeList = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employeeList);

        return "create";
    }


}