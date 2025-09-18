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

    @Override
    public boolean addEmployee(Employee employee) {
        int addSuccess = employeeDao.addEmployee(employee);
        return addSuccess == 1;
    }

    @Override
    public boolean delEmployeeInfo(int id) {
        int delSuccess = employeeDao.deleteEmployee(id);
        return delSuccess == 1;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        int updateSuccess = employeeDao.updateEmployee(employee);
        return updateSuccess == 1;
    }

    @Override
    public void searchEmployee(String username,String page, String limit) {
        employeeList = employeeDao.searchEmployeeInfoByName(username, page, limit);
        count = employeeDao.getSearchEmployeeInfoByNameCount(username);
    }
}
