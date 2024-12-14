package Controller;

import Model.BO.AdminBO;
import Model.Bean.Account;
import Model.Bean.Teacher;
import Model.Bean.TestDetail;

import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/admin")
public class AdminController extends HttpServlet {
    AdminBO adminBO = new AdminBO();
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        System.out.println(action);
       if(action.equals("edit")){
           int idGV = Integer.parseInt(req.getParameter("idTeacher").toString());
           String nameGV = req.getParameter("nameTeacher");
           try {
               editNameTeacher(req,resp,idGV,nameGV);
           } catch (SQLException e) {
               throw new RuntimeException(e);
           } catch (ClassNotFoundException e) {
               throw new RuntimeException(e);
           }
       }
       else if(action.equals("resetPassword")){
           System.out.println("Vào action reset password");
           int idGV = Integer.parseInt(req.getParameter("idgv"));
           System.out.println("IDGV Reset : " + idGV);

           try {
               adminBO.resetPasswordBO(idGV);
               resp.setStatus(HttpServletResponse.SC_OK);
           } catch (Exception e) {
               resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               e.printStackTrace();
           }

       }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action.equals("viewList")){
            try {
                viewListTeacher(req,resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        else if(action.equals("viewDetail")){
            int idGV = Integer.parseInt(req.getParameter("idgv").toString());
            try {
                viewListOfTests(req,resp,idGV);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if(action.equals("editName")){
            int idGV = Integer.parseInt(req.getParameter("idgv").toString());
            try {
                getNameTeacherById(req,resp,idGV);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void viewListTeacher(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        List<Teacher> listTeacher = adminBO.getListTeacherBO();
        request.setAttribute("listTeacher",listTeacher);
        request.getRequestDispatcher("/Admin/listTeacher.jsp").forward(request,response);
    }

    public void viewListOfTests(HttpServletRequest request, HttpServletResponse response,int IDGV) throws SQLException, ClassNotFoundException, ServletException, IOException {
        List<TestDetail> listTest = adminBO.getListOfTestsBO(IDGV);
        request.setAttribute("listTestDetail",listTest);
        request.getRequestDispatcher("/Teacher/listTest.jsp").forward(request,response);
    }
    public void getNameTeacherById(HttpServletRequest request, HttpServletResponse response,int IDGV) throws SQLException, ClassNotFoundException, ServletException, IOException {
        String currentName = adminBO.getNameTeacherByIdBO(IDGV);
        request.setAttribute("currentName",currentName);
        request.getRequestDispatcher("/Admin/editNameTeacher.jsp").forward(request,response);
    }
    public void editNameTeacher(HttpServletRequest request,HttpServletResponse response,int idgv,String nameTeacher) throws SQLException, ServletException, IOException, ClassNotFoundException {
        boolean isSuccess = adminBO.editNameTeacherBO(idgv,nameTeacher);
        if(isSuccess) {
            viewListTeacher(request, response);
        }
        else{
            request.setAttribute("errorMessage", "Failed to update teacher's name. Please try again.");
            getNameTeacherById(request, response, idgv);
        }
    }
}
