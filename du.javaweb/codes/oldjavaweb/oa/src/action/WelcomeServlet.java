package action;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    /**
     * 函数用来判断用户打开webapp以后应该进入index.jsp进行登录, 还是直接进入list.jsp查看部门列表.
     * @param request 请求
     * @param response 响应
     * @throws ServletException 异常
     * @throws IOException 异常
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 用来判断用户打开oa应用以后的第一个展示页面应该是哪个.

        String username = null;
        String password = null;

        // 先获取所有的cookie
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            // 遍历cookies, 判断是否有用户名和密码.
            for (Cookie cookie:
                 cookies) {
                if ("username".equals(cookie.getName())) {
                    // 找到 name = username 的 cookie, 获取用户名.
                    username = cookie.getValue();
                }
                if ("password".equals(cookie.getName())) {
                    // 找到 name = password 的cookie, 获取密码.
                    password = cookie.getValue();
                }
            }
        }

        // 现在, username 和 password 有可能全为空, 或者全都不为空.
        if (username != null && password != null) {
            // 说明cookie中获取到了用户的登录信息.

            // 验证用户名和密码是否正确, 决定登录成功或者失败

            // JDBC代码.

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            boolean success = false;

            try {
                connection = DBUtils.getConnection();
                String sql = "select * from users where username = ? and password = ?";
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) success = true;

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("WelcomeServlet-->doGet函数出错.");
            }

            if (success) {
                // 登录成功, 将用户名密码写入session, 然后跳转到列表页面.

                // 写入session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("password", password);

                // 跳转页面.
                response.sendRedirect(request.getContextPath() + "/dept/getList");
            } else {
                // 登录失败
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }


        } else {
            // 没有用户信息, 跳转到登录页面.
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
