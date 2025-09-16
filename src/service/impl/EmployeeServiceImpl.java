package service.impl;

import dao.EmployeeDao;
import dao.impl.EmployeeDaoImpl;
import entity.Employee;
import service.EmployeeService;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {
    public static List<Employee> employeeList;
    public static int count;
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void getEmployeeList(String page, String limit) {
        employeeList = employeeDao.getEmployeeList(page, limit);
        count = employeeDao.getAllEmployeeCount();
        System.out.println(employeeList);
    }
}
