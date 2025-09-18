package service;

import entity.Employee;

public interface EmployeeService {
    void getEmployeeList(String page, String limit);
    boolean addEmployee(Employee employee);
    boolean delEmployeeInfo(int id);
    boolean updateEmployee(Employee employee);
    void searchEmployee(String username,String page, String limit);
}
