package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import service.CompanyService;
import service.impl.CompanyServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.impl.CompanyServiceImpl.jobList;

/**
 * 员工信息表格接口位置
 * 查询工种：/job/get
 * 添加工种：/job/add
 * 删除工种：/job/del
 * 查询部门：/dept/get
 * 添加部门：/dept/add
 * 删除部门：/dept/del
 */
@WebServlet(urlPatterns = {"/job/get", "/job/add", "/job/del", "/dept/get", "/dept/del", "/dept/add"})
public class CompanyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        response.setContentType("application/json;charset=UTF-8");
        System.out.println(path);
        CompanyService companyService = new CompanyServiceImpl();

        switch (path) {
            case "/job/get":
                companyService.getJobs();
                FormResponse getJobResponse = new FormResponse(
                        "200",
                        "工种查询成功！",
                        jobList
                );

                SimplePropertyPreFilter getJobFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg","data"
                );

                String getJobJson = JSON.toJSONString(
                        getJobResponse,
                        getJobFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(getJobJson);
                break;
            case "/job/add":
                break;
            case "/job/del":
                break;
            case "/dept/get":
                companyService.getDepts();
                FormResponse getDeptResponse = new FormResponse(
                        "200",
                        "部门查询成功！",
                        jobList
                );

                SimplePropertyPreFilter getDeptFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg","data"
                );

                String getDeptJson = JSON.toJSONString(
                        getDeptResponse,
                        getDeptFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(getDeptJson);
                break;
            case "/dept/del":
                break;
            case "/dept/add":
                break;
        }
    }
}
