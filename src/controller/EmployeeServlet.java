package controller;

import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 员工信息表格接口位置
 * 查询总表：/employee/getAllEmployeeInfo
 * 添加员工：/employee/addEmployeeInfo
 * 删除员工：/employee/delEmployeeInfo
 * 编辑员工：/employee/updateEmployeeInfo
 * 查询员工：/employee/searchEmployeeInfo
 */
@WebServlet(urlPatterns = {"/employee/getAllEmployeeInfo","/employee/addEmployeeInfo","/employee/delEmployeeInfo","/employee/updateEmployeeInfo","/employee/searchEmployeeInfo"})
public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(path);
        EmployeeService employeeService = new EmployeeServiceImpl();

        switch (path) {
            case "/employee/getAllEmployeeInfo":
                String page = request.getParameter("page");
                String limit = request.getParameter("limit");

                employeeService.getEmployeeList(page, limit);
                //TODO 查询所有用户的json返回格式编写
                break;
            case "/employee/addEmployeeInfo":
                break;
            case "/employee/delEmployeeInfo":
                break;
            case "/employee/updateEmployeeInfo":
                break;
            case "/employee/searchEmployeeInfo":
                break;
        }
    }
}
