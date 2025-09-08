package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import entity.User;
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

/**
 * 用户信息表格接口位置
 * 查询总表：/user/getAllUserInfo
 * 添加用户：/user/addUserInfo
 * 删除用户：/user/delUserInfo
 * 编辑用户：/user/updateUserInfo
 */
@WebServlet(urlPatterns = {"/user/getAllUserInfo", "/user/updateUserInfo", "/user/delUserInfo", "/user/addUserInfo"})
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
            case "/user/getAllUserInfo":
                String page = request.getParameter("page");
                String limit = request.getParameter("limit");

                response.setContentType("application/json;charset=UTF-8");
                userService.getAllUserInfo(page, limit);
                FormResponse formResponse = new FormResponse(
                        "0",
                        "查询用户成功",
                        count,
                        userList
                );

                String QueryJson = JSON.toJSONString(
                        formResponse,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(QueryJson);
                break;
            case "/user/updateUserInfo":
                StringBuilder updateUserInfo = new StringBuilder();
                FormResponse updateResponse;
                String line;
                response.setContentType("application/json;charset=UTF-8");

                while ((line = request.getReader().readLine()) != null) {
                    updateUserInfo.append(line);
                }
                String jsonStr = updateUserInfo.toString();

                User user = JSON.parseObject(jsonStr, User.class);
                boolean updateSuccess = userService.updateUserInfo(user);
                if (updateSuccess) {
                    updateResponse = new FormResponse(
                            "0",
                            "用户信息更改成功！"
                    );
                } else {
                    updateResponse = new FormResponse(
                            "1",
                            "用户信息更改失败！"
                    );
                }
                String updateJson = JSON.toJSONString(
                        updateResponse,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().write(updateJson);
                break;
            case "/user/delUserInfo":
                break;
            case "/user/addUserInfo":
                break;
            default:
                break;
        }
    }
}
