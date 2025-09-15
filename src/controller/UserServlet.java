package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
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
 * 查询用户：/user/searchUserInfo
 */
@WebServlet(urlPatterns = {"/user/getAllUserInfo", "/user/updateUserInfo", "/user/delUserInfo", "/user/addUserInfo", "/user/searchUserInfo"})
public class UserServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        System.out.println(path);
        switch (path) {
            case "/user/getAllUserInfo":
                String page = req.getParameter("page");
                String limit = req.getParameter("limit");

                resp.setContentType("application/json;charset=UTF-8");
                userService.getAllUserInfo(page, limit);
                FormResponse queryResponse = new FormResponse(
                        "0",
                        "查询用户成功",
                        count,
                        userList
                );

                String QueryJson = JSON.toJSONString(
                        queryResponse,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.MapSortField
                );
                resp.getWriter().write(QueryJson);
                break;
            case "/user/updateUserInfo":
                StringBuilder updateUserInfo = new StringBuilder();
                FormResponse updateResponse;
                String line;
                resp.setContentType("application/json;charset=UTF-8");

                while ((line = req.getReader().readLine()) != null) {
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

                SimplePropertyPreFilter updateFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String updateJson = JSON.toJSONString(
                        updateResponse,
                        updateFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                resp.getWriter().write(updateJson);
                break;
            case "/user/delUserInfo":
                String delIdStr = req.getParameter("delId");
                resp.setContentType("application/json;charset=UTF-8");
                FormResponse delResponse;
                boolean delSuccess = userService.delUserInfo(Integer.parseInt(delIdStr));

                if (delSuccess) {
                    delResponse = new FormResponse(
                            "0",
                            "删除用户信息成功！"
                    );
                } else {
                    delResponse = new FormResponse(
                            "1",
                            "删除用户信息失败！"
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
                resp.getWriter().write(delJson);
                break;
            case "/user/addUserInfo":
                StringBuilder addUserInfo = new StringBuilder();
                FormResponse addResponse;
                String line2;
                resp.setContentType("application/json;charset=UTF-8");

                while ((line2 = req.getReader().readLine()) != null) {
                    addUserInfo.append(line2);
                }
                String jsonStr2 = addUserInfo.toString();
                User user2 = JSON.parseObject(jsonStr2, User.class);
                boolean addSuccess = userService.addUserInfo(user2);
                if (addSuccess) {
                    addResponse = new FormResponse("0", "添加新用户成功！");
                } else {
                    addResponse = new FormResponse("1", "添加新用户成功！");
                }

                SimplePropertyPreFilter addFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String addJson = JSON.toJSONString(
                        addResponse,
                        addFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                resp.getWriter().write(addJson);
                break;
            case "/user/searchUserInfo":
                String page2 = req.getParameter("page");
                String limit2 = req.getParameter("limit");
                String loginname = req.getParameter("loginname");
                String status = req.getParameter("STATUS");

                if (loginname == null || loginname.isEmpty()) {
                    userService.searchUserInfo("status", null, status, page2, limit2);
                } else {
                    userService.searchUserInfo("all", loginname, status, page2, limit2);
                }

                FormResponse searchQueryResponse = new FormResponse(
                        "0",
                        "索引查询用户成功",
                        count,
                        userList
                );

                String searchQueryJson = JSON.toJSONString(
                        searchQueryResponse,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat,
                        SerializerFeature.MapSortField
                );
                resp.getWriter().write(searchQueryJson);
                break;
            default:
                break;
        }
    }
}
