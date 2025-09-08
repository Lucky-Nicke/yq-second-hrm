package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import service.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.impl.UserServiceImpl.count;
import static service.impl.UserServiceImpl.userList;

@WebServlet(urlPatterns = {"/user/getAllUserInfo"})
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");

        response.setContentType("application/json;charset=UTF-8");
        userService.getAllUserInfo(page,limit);
        FormResponse formResponse = new FormResponse(
                "0",
                "查询用户成功",
                count,
                userList
        );

        String json =  JSON.toJSONString(
                formResponse,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.MapSortField
        );
        response.getWriter().write(json);

    }
}
