package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Shoppingdao;
import entity.Comment;
import entity.Goods;
import entity.User;
import service.GoodsService;
import service.imp.GoodsServiceImp;

/**
 * Servlet implementation class CommentAction
 */
@WebServlet("/CommentAction")
public class CommentAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Shoppingdao idao = new Shoppingdao(); 	//商品业务逻辑类的对象
	private GoodsService gs = new GoodsServiceImp();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentAction() {
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
		System.out.println(action);

		
		if(action.equals("addComments")){//为商品添加评论和评价
			addComments(request, response);
		}
		
		if(action.equals("ShowAllCommentsUnderUser")){//查看用户发出的所有评价和评论
			ShowAllCommentsUnderUser(request, response);
		}

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	//为商品添加评论和评价
	private void addComments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		User user = (User) request.getSession().getAttribute("user");
		int gid = Integer.parseInt(request.getParameter("gid"));
		float score = Float.parseFloat(request.getParameter("score"));
		String content = request.getParameter("content");
		content = new String(content.getBytes("ISO-8859-1"),"utf-8");
		 
		idao.addComment(score,content,user.getId(),gid);
		request.getRequestDispatcher("GoodsAction?action=ShowDetails").forward(request, response);

		
		
	}

	//查看用户发出的所有评价和评论
	private void ShowAllCommentsUnderUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		List<Comment> comments = new ArrayList<>();
		comments = idao.selectCommentsByUser(user);
		System.out.println(comments);
		for(Comment comment:comments){
			Goods goods = comment.getGoods();
			System.out.println(goods);
			goods = gs.showGoodsDetail(goods);
			comment.setGoods(goods);
		}
		int size = 0;
		size = comments.size();
		
		request.setAttribute("size", size);
		request.setAttribute("comments", comments);
		request.getRequestDispatcher("UserComment.jsp").forward(request, response);
		
	}
	

}
