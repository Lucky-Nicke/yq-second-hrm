package dao;

import entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getEmployeeList(String page, String limit);
    int getAllEmployeeCount();
    int addEmployee(Employee employee);
    int deleteEmployee(int delId);
    int updateEmployee(Employee employee);
    List<Employee> searchEmployeeInfoByName(String username, String page, String limit);
    int getSearchEmployeeInfoByNameCount(String username);
}
