package dao;

import entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> getEmployeeList(String page, String limit);
    int getAllEmployeeCount();
}
