package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Shoppingdao;
import entity.Cart;
import entity.Goods;
import service.GoodsService;
import service.imp.GoodsServiceImp;

/**
 * Servlet implementation class ShoppingCarController
 */
@WebServlet("/ShoppingAction")
public class ShoppingAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String action ; //表示购物车的动作 ,add,show,delete
	private Shoppingdao idao = new Shoppingdao(); 	//商品业务逻辑类的对象
	private GoodsService gs = new GoodsServiceImp();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		if(request.getParameter("action")!=null)
		{
			this.action = request.getParameter("action");
			
			if(action.equals("add")) //如果是添加商品进购物车
			{
				try {
					if(addToCart(request,response))
					{
						
//						request.getRequestDispatcher("addGoodSuccess.jsp").forward(request, response);
						
					}
					else
					{
						request.getRequestDispatcher("addGoodFailed.jsp").forward(request, response);
					}
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(action.equals("show"))//如果是显示购物车
			{
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
			}
			
			if(action.equals("sumbitCart"))//如果是从购物车中生成暂时订单
			{
				this.buildOrder(request,response);//生成临时订单，删除购物车

				response.sendRedirect("temporaryOrder.jsp");
			}
			
			if(action.equals("empty"))//如果是清空购物车
			{
				request.getSession().removeAttribute("cart");//把session里的购物车删掉
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
			}
			
			if(action.equals("delete")) //如果是执行删除购物车中的商品
			{
				try {
					if(deleteFromCart(request,response))
					{
						request.getRequestDispatcher("/error.jsp").forward(request, response);
					}
					else
					{
						request.getRequestDispatcher("/cart.jsp").forward(request, response);
					}
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//添加商品进购物车的方法
	private boolean addToCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		int number = Integer.parseInt(request.getParameter("num"));
		System.out.println(number);
		Goods item = new Goods();
		item.setId(id);
		item = gs.showGoodsDetail(item);
		
		
		//是否是第一次给购物车添加商品,需要给session中创建一个新的购物车对象
		if(request.getSession().getAttribute("cart")==null)
		{
			Cart cart = new Cart();
			request.getSession().setAttribute("cart",cart);
		}
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart.addGoodsInCart(item,number))
		{
			request.getSession().setAttribute("cart", cart);//刷新购物车
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	//从购物车中删除商品(从session)
	private boolean deleteFromCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		Cart cart = (Cart)request.getSession().getAttribute("cart");
	      HashMap<Goods,Integer> goods = cart.getGoods();
	         Set<Goods> items = goods.keySet();
	         Iterator<Goods> it = items.iterator();
	         
	         while(it.hasNext())
	         {Goods i = it.next();
	         System.out.println("ss="+i.toString());
	         
	         }
	        	 
		Goods item = new Goods();
		item.setId(id);
		item = gs.showGoodsDetail(item);
		System.err.println(item.toString());
		System.out.println("好好");
	    	cart=cart.removeGoodsFromCart(item);
	    	 HashMap<Goods,Integer> goods1 = cart.getGoods();
	         Set<Goods> items1 = goods1.keySet();
	         Iterator<Goods> it1 = items1.iterator();
	         
	         while(it1.hasNext())
	         {Goods i = it1.next();
	         System.out.println(i.toString());
	         }
	        	 
	    	request.getSession().setAttribute("cart", cart);//刷新购物车
	    	return true;
	   
	}
	
	//把从购物车中的提交的生成暂时订单TemporaryOrder,把提交过的购物车当作暂时订单，并且删除购物车
	private void  buildOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cart TemporaryOrder=new Cart();
		TemporaryOrder=(Cart)request.getSession().getAttribute("cart");
		request.getSession().removeAttribute("cart");
		request.getSession().setAttribute("TemporaryOrder", TemporaryOrder);

		
	}

}
