package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import entity.Employee;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.impl.EmployeeServiceImpl.count;
import static service.impl.EmployeeServiceImpl.employeeList;

/**
 * 员工信息表格接口位置
 * 查询总表：/employee/getAllEmployeeInfo
 * 添加员工：/employee/addEmployeeInfo
 * 删除员工：/employee/delEmployeeInfo
 * 编辑员工：/employee/updateEmployeeInfo
 * 查询员工：/employee/searchEmployeeInfo
 */
@WebServlet(urlPatterns = {"/employee/getAllEmployeeInfo", "/employee/addEmployeeInfo", "/employee/delEmployeeInfo", "/employee/updateEmployeeInfo", "/employee/searchEmployeeInfo"})
public class EmployeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                FormResponse getAllEmployee = new FormResponse(
                        "0",
                        "查询员工成功",
                        count,
                        employeeList
                );

                String jsonStr = JSON.toJSONString(
                        getAllEmployee,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(jsonStr);
                break;
            case "/employee/addEmployeeInfo":
                StringBuilder addEmployeeInfo = new StringBuilder();
                String line;
                FormResponse addEmployee;
                response.setContentType("application/json;charset=UTF-8");

                while ((line = request.getReader().readLine()) != null) {
                    addEmployeeInfo.append(line);
                }
                String jsonStr2 = addEmployeeInfo.toString();
                Employee employee = JSON.parseObject(jsonStr2, Employee.class);
                boolean isAddEmployee = employeeService.addEmployee(employee);

                if (isAddEmployee) {
                    addEmployee = new FormResponse(
                            "200",
                            "员工信息添加成功！"
                    );
                }else{
                    addEmployee = new FormResponse(
                            "401",
                            "员工信息添加失败！"
                    );
                }
                SimplePropertyPreFilter addFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String addJson = JSON.toJSONString(
                        addEmployee,
                        addFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(addJson);
                break;
            case "/employee/delEmployeeInfo":
                String delIdStr = request.getParameter("delId");
                response.setContentType("application/json;charset=UTF-8");
                FormResponse delResponse;
                boolean delSuccess = employeeService.delEmployeeInfo(Integer.parseInt(delIdStr));

                if (delSuccess) {
                    delResponse = new FormResponse(
                            "200",
                            "删除员工信息成功！"
                    );
                } else {
                    delResponse = new FormResponse(
                            "401",
                            "删除员工信息失败！"
                    );
                }

                SimplePropertyPreFilter delFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String delJson = JSON.toJSONString(
                        delResponse,
                        delFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(delJson);
                break;
            case "/employee/updateEmployeeInfo":
                StringBuilder updateEmployeeInfo = new StringBuilder();
                String line2;
                FormResponse updateEmployee;
                while ((line2 = request.getReader().readLine()) != null) {
                    updateEmployeeInfo.append(line2);
                }

                String jsonStr3 = updateEmployeeInfo.toString();
                Employee employee2 = JSON.parseObject(jsonStr3, Employee.class);
                if(employeeService.updateEmployee(employee2)){
                    updateEmployee = new FormResponse(
                            "200",
                            "员工信息更新成功！"
                    );
                }else{
                    updateEmployee = new FormResponse(
                            "401",
                            "员工信息更新失败！"
                    );
                }

                SimplePropertyPreFilter updateFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String updateJson = JSON.toJSONString(
                        updateEmployee,
                        updateFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(updateJson);
                break;
            case "/employee/searchEmployeeInfo":
                String page2 = request.getParameter("page");
                String limit2 = request.getParameter("limit");
                String username = request.getParameter("username");

                employeeService.searchEmployee(username, page2, limit2);
                FormResponse searchQueryResponse = new FormResponse(
                        "0",
                        "索引查询员工成功",
                        count,
                        employeeList
                );

                String searchQueryJson = JSON.toJSONString(
                        searchQueryResponse,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(searchQueryJson);
                break;
        }
    }
}
