package controller;

import DTO.CheckPwdInfo;
import DTO.RegisterInfo;
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

import static service.impl.LoginServiceImpl.userInfoList;

@WebServlet(urlPatterns = {"/login", "/register/checkLoginname", "/logout", "/changePwd", "/register/submit"})
public class LoginServlet extends HttpServlet {
    LoginService loginService = new LoginServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        response.setContentType("application/json;charset=UTF-8");

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
                            userInfoList
                    );
                    loginService.loggedIn(user.getLoginname());
                } else {
                    loginResponse = new FormResponse(
                            "404",
                            "用户名或密码错误，请重新登录！",
                            null
                    );
                }
                SimplePropertyPreFilter loginFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg", "data"
                );

                String loginJson = JSON.toJSONString(
                        loginResponse,
                        loginFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().print(loginJson);

                break;
            case "/register/checkLoginname":
                String loginname = request.getParameter("loginname");
                FormResponse checkLoginnameResponse;

                if (loginService.checkLoginname(loginname)) {
                    checkLoginnameResponse = new FormResponse(
                            "401",
                            "用户名已占用，请重新输入！"
                    );

                    SimplePropertyPreFilter changePwdFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String checkLoginname = JSON.toJSONString(
                            checkLoginnameResponse,
                            changePwdFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(checkLoginname);
                } else {
                    checkLoginnameResponse = new FormResponse(
                            "200",
                            "名字可以使用！"
                    );

                    SimplePropertyPreFilter changePwdFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String checkLoginname = JSON.toJSONString(
                            checkLoginnameResponse,
                            changePwdFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(checkLoginname);
                }

                break;
            case "/register/submit":
                StringBuilder registerInfo = new StringBuilder();
                FormResponse registerResponse;
                String line2;
                while ((line2 = request.getReader().readLine()) != null) {
                    registerInfo.append(line2);
                }
                String jsonStr2 = registerInfo.toString();
                RegisterInfo registerInfo1 = JSON.parseObject(jsonStr2, RegisterInfo.class);

                if (loginService.checkLoginname(registerInfo1.getLoginname())) {
                    checkLoginnameResponse = new FormResponse(
                            "401",
                            "用户名已占用，请重新输入！"
                    );

                    SimplePropertyPreFilter changePwdFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String checkLoginname = JSON.toJSONString(
                            checkLoginnameResponse,
                            changePwdFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(checkLoginname);
                } else {
                    loginService.registerNewUser(registerInfo1.getLoginname(), registerInfo1.getPassword());
                    registerResponse = new FormResponse(
                            "200",
                            "注册成功！"
                    );

                    SimplePropertyPreFilter registerOKFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String logoutJson = JSON.toJSONString(
                            registerResponse,
                            registerOKFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(logoutJson);
                }


                break;
            case "/logout":
                String loginname2 = request.getParameter("loginname");
                loginService.loggedOut(loginname2);
                FormResponse logoutResponse = new FormResponse(
                        "200",
                        "退出登录成功！"
                );

                SimplePropertyPreFilter logoutFilter = new SimplePropertyPreFilter(
                        FormResponse.class,
                        "code", "msg"
                );

                String logoutJson = JSON.toJSONString(
                        logoutResponse,
                        logoutFilter,
                        SerializerFeature.WriteMapNullValue,
                        SerializerFeature.MapSortField
                );
                response.getWriter().print(logoutJson);
                break;
            case "/changePwd":
                StringBuilder changePwdInfo = new StringBuilder();
                FormResponse changePwdResponse;
                String line3;

                while ((line3 = request.getReader().readLine()) != null) {
                    changePwdInfo.append(line3);
                }
                String jsonStr3 = changePwdInfo.toString();
                CheckPwdInfo checkPwdInfo = JSON.parseObject(jsonStr3, CheckPwdInfo.class);
                if (loginService.checkPassword(checkPwdInfo.getLoginname(), checkPwdInfo.getOldPassword())) {
                    loginService.changePassword(checkPwdInfo.getLoginname(), checkPwdInfo.getNewPassword());

                    changePwdResponse = new FormResponse(
                            "200",
                            "密码修改成功！"
                    );

                    SimplePropertyPreFilter changePwdFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String changePwdJson = JSON.toJSONString(
                            changePwdResponse,
                            changePwdFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(changePwdJson);
                } else {
                    changePwdResponse = new FormResponse(
                            "401",
                            "原密码错误，请重新输入！"
                    );

                    SimplePropertyPreFilter changePwdFilter = new SimplePropertyPreFilter(
                            FormResponse.class,
                            "code", "msg"
                    );

                    String changePwdJson = JSON.toJSONString(
                            changePwdResponse,
                            changePwdFilter,
                            SerializerFeature.WriteMapNullValue,
                            SerializerFeature.MapSortField
                    );
                    response.getWriter().print(changePwdJson);
                }


                break;
            default:
                break;
        }
    }
}
