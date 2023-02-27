package action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.DBUtils;

import javax.management.relation.RelationSupport;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet({"/user/login", "/user/logout"})
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/user/login".equals(request.getServletPath())) {
            doLogin(request, response);
        }
        if ("/user/logout".equals(request.getServletPath())) {
            doLogOut(request, response);
        }
    }

    private void doLogOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取session

        HttpSession session = request.getSession();

        // 删除session内容
        session.removeAttribute("username");
        session.removeAttribute("password");

        // 删除cookie

        // 获取cookie
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // cookies不为空.
            for (Cookie cookie :
                    cookies) {
                if ("username".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setValue(null);
                    response.addCookie(cookie);
                    System.out.println("获取到cookie:" + cookie.getName() + " : " + cookie.getValue());
                }
                if ("password".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setValue(null);
                    response.addCookie(cookie);
                    System.out.println("获取到cookie:" + cookie.getName() + " : " + cookie.getValue());
                }
            }
        }

        // 回到首页
        response.sendRedirect(request.getContextPath());
        // request.getRequestDispatcher("/welcome").forward(request, response);
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取前端发来的信息.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 链接数据库
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        // 标记登陆是否成功。
        boolean success = false;

        try {
            // 获取到链接
            connection = DBUtils.getConnection();
            // sql
            String sql = "select * from users where username = ? and password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 判断结果集有没有结果, 从而判断用户是否存在
            if (resultSet.next()) {
                // 登陆成功, 将success改为true.
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("登录-->链接数据库失败");
        } finally {
            DBUtils.closeResources(connection, preparedStatement, resultSet);
        }

        if (success) {
            // 登录成功
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("password", password);

            // 做免登录功能.
            // 如果用户选择了免登录, 就要创建cookie并且发送给浏览器.
            String autoLogin = request.getParameter("autoLogin"); // 获取免登录相关的参数
            if ("1".equals(autoLogin)) {
                // 用户选择了免登录

                // 创建cookie
                // 创建新的cookie标记用户名和密码
                Cookie username_cookie = new Cookie("username", username);
                Cookie password_cookie = new Cookie("password", password); // 实际上这些数据是加密过的

                // 设置cookie的有效期为十天
                username_cookie.setMaxAge(60 * 60 * 24 * 10);
                password_cookie.setMaxAge(60 * 60 * 24 * 10);

                // 设置cookie的path
                // 只要访问oa应用,浏览器就一定要携带这两个cookie
                username_cookie.setPath(request.getContextPath());
                password_cookie.setPath(request.getContextPath());

                // 加入cookie到响应中
                response.addCookie(username_cookie);
                response.addCookie(password_cookie);
            }

            // 转发到列表页
            request.getRequestDispatcher("/dept/getList").forward(request, response);
        } else {
            // 登录失败
            response.sendRedirect(request.getContextPath());
        }


    }
}
