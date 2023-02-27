import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/cookie/generateCookie")
public class GenerateCookie extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 生成一个cookie
        Cookie cookie = new Cookie("cookieName", "cookieValue"); // cookie的name和value都是String
        Cookie cookie1 = new Cookie("cookie1", "cookieValue1");
        // 设置cookie的最大持续时间
        /*
        如果设置cookie的有效期为0, 则表示改cookie被删除, 一般用于删除电脑上的同名cookie.
        如果设置cookie的最大持续时间为负数, 则表示该cookie不会被存储.(表示不会被存储到硬盘文件中, 会放在运行内存中)
        所以设置为负数和不调用setMaxAge()函数是相同的效果.
         */
        cookie.setMaxAge(60); // 设置最大持续时间为60秒
        cookie1.setMaxAge(-1); // 保存在浏览器运行内存中.

        /*
        默认情况下, 没有关联path的时候, cookie的路径是什么?
        一般情况是接受到这个cookie的路径(url)
         */
        // 设置path.
        cookie.setPath("/web01"); // web01是现在的webapp的项目名.
        cookie1.setPath(req.getContextPath()); // 设置webapp的根路径为cookie的路径.

        // 为response对象添加cookie,可以使得cookie通过response响应给浏览器
        resp.addCookie(cookie);
        resp.addCookie(cookie1);
    }
}
