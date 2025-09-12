package controller;

import Response.FormResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import entity.User;
import service.LoginService;
import service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static service.impl.LoginServiceImpl.userList;

@WebServlet(urlPatterns = {"/login", "/register"})
public class LoginServlet extends HttpServlet {
    LoginService loginService = new LoginServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        System.out.println(path);
        switch (path) {
            case "/login":
                StringBuilder loginInfo = new StringBuilder();
                FormResponse loginResponse;
                response.setContentType("application/json;charset=UTF-8");
                String line;

                while ((line = request.getReader().readLine()) != null) {
                    loginInfo.append(line);
                }
                String jsonStr = loginInfo.toString();

                User user = JSON.parseObject(jsonStr, User.class);
                boolean isLogin = loginService.isLogin(user.getLoginname(), user.getPASSWORD());
                if (isLogin) {
                    loginResponse = new FormResponse(
                            "200",
                            "登录成功！",
                            userList
                    );
                } else {
                    loginResponse = new FormResponse(
                            "404",
                            "用户名或密码错误，请重新登录！",
                            null
                    );
                }
                SimplePropertyPreFilter loginFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg","data"
                );

                String loginJson = JSON.toJSONString(
                        loginResponse,
                        loginFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().print(loginJson);

                break;
            case "/register":
                break;
            default:
                break;
        }
    }
}
