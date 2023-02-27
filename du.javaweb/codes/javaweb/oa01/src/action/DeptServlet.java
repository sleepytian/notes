package action;

import beans.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.DBUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// 功能依次对应:
@WebServlet({"/dept/getList", "/dept/detail", "/dept/edit", "/dept/commitEdit", "/dept/del", "/dept/add"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取session
        // 这个session只是获取,不需要新建
        // 所以参数写false
        HttpSession session = request.getSession(false);


        if (session != null && session.getAttribute("username") != null) {
            if (request.getServletPath().equals("/dept/getList")) {
                // servlet路径是 /dept/getList, 链接数据库获取list
                doGetList(request, response);
            }
            if (request.getServletPath().equals("/dept/detail")) {
                // 获取部门详细信息
                deGetDetail(request, response);
            }
            if (request.getServletPath().equals("/dept/edit")) {
                // 进入详情编辑页面
                doEdit(request, response);
            }
            if (request.getServletPath().equals("/dept/commitEdit")) {
                // 提交修改信息
                doCommitEdit(request, response);
            }
            if (request.getServletPath().equals("/dept/del")) {
                doDel(request, response);
            }
            if (request.getServletPath().equals("/dept/add")) {
                doAdd(request, response);
            }
        } else {
            // 登录不成功, 返回首页
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }

    }
    private void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 添加新部门
        // 获取部门值
        String dno = request.getParameter("dno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 写sql
        String sql = "insert into depts(dno, dname, loc) value(?, ?, ?)";

        // 我们仨
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtils.getConnection();

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, dno);
            preparedStatement.setObject(2, dname);
            preparedStatement.setObject(3, loc);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加新部门-->操作数据库捕获异常");
        } finally {
            DBUtils.closeResources(connection, preparedStatement);
        }

        request.getRequestDispatcher("/dept/getList").forward(request, response);
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取到要删除的dno
        String dno = request.getParameter("dno");
        // 写sql
        String sql = "delete from depts where dno = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, dno);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("删除操作捕获异常");
        }

        // request.getRequestDispatcher("/getList").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/dept/getList");

    }

    private void doCommitEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取修改后的属性值
        String dno = request.getParameter("dno");
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        System.out.println(dno + " " + dname + " " + loc);

        // 进入修改页面
        // 获取部门详细信息
        // 准备好空对象
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Department department = null;

        try {
            // 获取数据库链接
            connection = DBUtils.getConnection();

            String sql = "update depts set dname = ?, loc = ? where dno = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dname);
            preparedStatement.setString(2, loc);
            preparedStatement.setString(3, dno);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("进入编辑页面-->获取部门列表链接数据库失败.");
        } finally {
            DBUtils.closeResources(connection, preparedStatement, resultSet);
        }

        // 转发请求
        // 注意: 转发不需要写项目名
        // request.getRequestDispatcher("/dept/getList").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/dept/getList");
    }

    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 进入修改页面
        // 获取部门详细信息
        // 准备好空对象
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Department department = null;

        String dno = request.getParameter("dno");

        try {
            // 获取数据库链接
            connection = DBUtils.getConnection();
            String sql = "select dname, loc from depts where dno = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dno);
            // 获取结果集
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 准备字段
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                // 创建部门对象并且填充字段.
                department = new Department(dno, dname, loc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("进入编辑页面-->获取部门列表链接数据库失败.");
        } finally {
            DBUtils.closeResources(connection, preparedStatement, resultSet);
        }

        request.setAttribute("department", department);

        // 转发请求
        // 注意: 转发不需要写项目名
        request.getRequestDispatcher("/pages/edit.jsp").forward(request, response);

    }

    private void deGetDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取部门详细信息
        // 准备好空对象
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Department department = null;

        String dno = request.getParameter("dno");

        try {
            // 获取数据库链接
            connection = DBUtils.getConnection();
            String sql = "select dname, loc from depts where dno = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dno);
            // 获取结果集
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 准备字段
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                // 创建部门对象并且填充字段.
                department = new Department(dno, dname, loc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取部门列表链接数据库失败.");
        } finally {
            DBUtils.closeResources(connection, preparedStatement, resultSet);
        }

        // 将列表存入请求域转发到list.jsp
        request.setAttribute("detailedDept", department);
        // 转发请求
        // 注意: 转发不需要写项目名
        request.getRequestDispatcher("/pages/details.jsp").forward(request, response);

    }

    private void doGetList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 存储部门列表的容器
        List<Department> list = new ArrayList<>();

        // 准备好空对象
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // 获取数据库链接
            connection = DBUtils.getConnection();
            String sql = "select dno, dname, loc from depts";
            preparedStatement = connection.prepareStatement(sql);
            // 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 遍历结果集
            while (resultSet.next()) {
                // 准备字段
                String dno = resultSet.getString("dno");
                String dname = resultSet.getString("dname");
                String loc = resultSet.getString("loc");
                // 创建部门对象并且填充字段.
                Department department = new Department(dno, dname, loc);
                // 将对象存入list
                list.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取部门列表链接数据库失败.");
        } finally {
            DBUtils.closeResources(connection, preparedStatement, resultSet);
        }

        // 将列表存入请求域转发到list.jsp
        request.setAttribute("deptList", list);
        // 转发请求
        // 注意: 转发不需要写项目名
        request.getRequestDispatcher("/pages/list.jsp").forward(request, response);

    }
}
