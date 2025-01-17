package Controller;

import Model.BO.LoginBO;
import Model.Bean.Account;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/authen")
public class AuthenController extends HttpServlet {
    LoginBO loginBO = new LoginBO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String mod = req.getParameter("action");

        if(mod.equals("login")){
            login(req, resp);
        }
        else if(mod.equals("signup")){
            SignUp(req, resp);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        String action = req.getParameter("action");
        if(action.equals("logout")){
            logout(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Account account = loginBO.login(username, password);
        if(account == null){
            resp.sendRedirect("Login/login.jsp");
        }
        else{
            String role = account.getRole();
            req.getSession().setAttribute("account", account);
            if(role.equals("admin")){
                //req.getRequestDispatcher("Login/login.jsp").forward(req, resp);
                resp.sendRedirect("admin?action=viewList");
            }
            else if(role.equals("student")){
                resp.sendRedirect("test?action=getTest");
            }
            else if(role.equals("teacher")) {

                resp.sendRedirect("history?action=listHistoryTest&idTeacher="+account.getId());
            }
        }
    }

    public void SignUp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        boolean isSignUpSuccess = loginBO.SignUP(name, username, password, role);

        if(isSignUpSuccess){
            System.out.println("Dang ky thanh cong");
            resp.sendRedirect("index.jsp");
        }
        else{
            System.out.println("Dang ky that bai");
        }

    }
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("index.jsp");
    }
}
