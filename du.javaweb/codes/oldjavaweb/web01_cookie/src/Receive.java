import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/receiveCookie")
public class Receive extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从收到的请求中获取cookie.
        // 函数返回一个数组保存所有cookie,但是如果没有cookie,那么会返回一个null
        Cookie[] cookies = req.getCookies();

        // 如果cookies!=null,说明获取到了cookie.
        if (cookies!=null) {
            // cookies不为空,说明有cookie
            // 遍历cookie, 获取name和value
            for (Cookie cookie:
                 cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println("cookie name: " + name + "; cookie value:" + value);
            }
        }
    }
}
