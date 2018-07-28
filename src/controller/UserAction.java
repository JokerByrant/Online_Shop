package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.imp.UserDaoImp;
import entity.User;
import service.UserService;
import service.imp.UserServiceImp;

/**
 * Servlet implementation class UserAction
 */
@WebServlet("/UserAction")
public class UserAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String action = request.getParameter("action");
		
		if(action.equals("login")){//µÇÂ¼
			login(request, response);
		}
		
		if(action.equals("regist")){//×¢²á
			regist(request, response);
		}

		if(action.equals("logout")){//×¢Ïú
			logout(request, response);
		}
		
		if(action.equals("checkname")){//¼ì²éname
			checkName(request, response);
		}
		
		if(action.equals("checkemail")){//¼ì²éemail
			checkEmail(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//µÇÂ¼
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		UserService us = new UserServiceImp();
		User user = new User();
		user = us.login(name, password);
		
		if(user!=null){
			request.getSession().setAttribute("user", user);
			response.sendRedirect("GoodsAction?action=ShowAllGoods");
		} else {
			response.sendRedirect("login.html");
		}
	}
	
	// ×¢Ïú
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int uid = Integer.parseInt(request.getParameter("uid"));
		System.out.println(uid);
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("cart");
		response.sendRedirect("login.html");
		
	}

	// ×¢²á
	private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		
		UserDao ud = new UserDaoImp();
		boolean flag = ud.addUser(user);
		
		if(flag){
			response.sendRedirect("login.html");
		} else {
			response.sendRedirect("regist.html");
		}
		
	}
	
	// ¼ì²éemail
	private void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 boolean flag = true;
		 String email = request.getParameter("email");
		 UserService us = new UserServiceImp();
		 List<User> users = us.FindAllUsers();
		 for(User user:users){
			 System.out.println(user);
			 if(user.getEmail().equals(email)){
				 flag = false;
				 break;
			 }
		 }

		 if(flag){
			 response.getWriter().write("true");
			 System.out.println("true");
		 } else {
			response.getWriter().write("false");
			System.out.println("false");
		}
		
	}

	// ¼ì²éname
	private void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 boolean flag = true;
		 String name = request.getParameter("name");
		 UserService us = new UserServiceImp();
		 List<User> users = us.FindAllUsers();
		 for(User user:users){
			 System.out.println(user);
			 if(user.getName().equals(name)){
				 flag = false;
				 break;
			 }
			 
		 }
		 if(flag){
			 response.getWriter().write("true");
			 System.out.println("true");
		 } else {
			response.getWriter().write("false");
			System.out.println("false");
		}
		 
		
	}
	
	

}
