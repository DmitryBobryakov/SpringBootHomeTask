package com.netcracker.edu.db.employee.ui;

import com.netcracker.edu.db.employee.model.Employee;
import com.netcracker.edu.db.employee.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/delete")
    public String deleteEmpl(Model model, @RequestParam(value = "id", required = false) String id) {
        if (!ControllerUtils.isFilled(id)) {
            LOGGER.warn("Employee ID was not specified: {}", id);
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "delete";
        }
        BigInteger employeeId = ControllerUtils.toBigInteger(id);

        if (employeeId != null) {
            Employee emplToDelete=employeeService.getEmployeeById(employeeId);
            emplToDelete = emplToDelete != null ? emplToDelete : ControllerUtils.getNonexistentEmployee();
            employeeService.deleteEmployee(emplToDelete);
            model.addAttribute("emplToDelete", emplToDelete);
        }else{
            Employee emplToDelete=ControllerUtils.getNonexistentEmployee();
            model.addAttribute("emplToDelete", emplToDelete);
        }

        List<Employee> employeeList = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employeeList);

        return "delete";
    }

    @GetMapping("/update")
    public String updateEmpl(@RequestParam(value = "id", required = false,defaultValue = "-1") String id,
                             @RequestParam(value = "name", required = false,defaultValue = "-1") String name,
                             @RequestParam(value = "surname", required = false,defaultValue = "-1") String surname,
                             @RequestParam(value = "position", required = false,defaultValue = "-1") String position,
                             @RequestParam(value = "departmentid", required = false,defaultValue = "-1") Long departentid,
                             @RequestParam(value = "salary", required = false,defaultValue = "-1") Long salary,
                             Model model) {
        BigInteger oldEmplId = ControllerUtils.toBigInteger(id);
        if(oldEmplId==null){
            Employee oldEmpl = ControllerUtils.getNonexistentEmployee();
            model.addAttribute("newEmployee", oldEmpl);
            return "update";
        }



        if (id==null||surname==null||name==null||position==null||departentid<1||salary<1) {
            LOGGER.warn("Wrong new Employee");
            model.addAttribute(EMPLOYEE_ATTR, ControllerUtils.getNonexistentEmployee());
            return "update";
        } else {
             Employee newEmpl = new Employee(new BigInteger(id), name, surname, position, departentid, salary);
             if(newEmpl.getId()==null){
                 model.addAttribute(EMPLOYEE_ATTR, ControllerUtils.getNonexistentEmployee());
                 return "update";
             }
             Employee oldEmpl=employeeService.getEmployeeById(newEmpl.getId());
             employeeService.updateEmployee(newEmpl,oldEmpl);
             model.addAttribute("newEmployee", newEmpl);


            List<Employee> employeeList = employeeService.getAllEmployees();
            model.addAttribute("allEmployees", employeeList);
            return "update";
        }

    }

    @GetMapping("/create")
    public String createEmpl(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "surname", required = false) String surname,
                             @RequestParam(value = "position", required = false) String position,
                             @RequestParam(value = "departmentid", required = false) Long departentid,
                             @RequestParam(value = "salary", required = false) Long salary,
                             Model model) {
        if (surname==null||name==null||position==null||departentid<1||salary<1) {
            LOGGER.warn("Wrong new Employee");
            model.addAttribute(EMPLOYEE_ATTR, null);
            return "create";
        }else{
            Employee newEmpl=new Employee(null,name,surname,position,departentid,salary);

            employeeService.addEmployee(newEmpl);
            model.addAttribute("addedEmpl", newEmpl);

            List<Employee> employeeList = employeeService.getAllEmployees();
            model.addAttribute("allEmployees", employeeList);
        }


        return "create";
    }


}