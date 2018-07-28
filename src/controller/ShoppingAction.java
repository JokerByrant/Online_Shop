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
	private String action ; //��ʾ���ﳵ�Ķ��� ,add,show,delete
	private Shoppingdao idao = new Shoppingdao(); 	//��Ʒҵ���߼���Ķ���
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
			
			if(action.equals("add")) //����������Ʒ�����ﳵ
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
			
			if(action.equals("show"))//�������ʾ���ﳵ
			{
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
			}
			
			if(action.equals("sumbitCart"))//����Ǵӹ��ﳵ��������ʱ����
			{
				this.buildOrder(request,response);//������ʱ������ɾ�����ﳵ

				response.sendRedirect("temporaryOrder.jsp");
			}
			
			if(action.equals("empty"))//�������չ��ﳵ
			{
				request.getSession().removeAttribute("cart");//��session��Ĺ��ﳵɾ��
				request.getRequestDispatcher("/cart.jsp").forward(request, response);
			}
			
			if(action.equals("delete")) //�����ִ��ɾ�����ﳵ�е���Ʒ
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
	
	//�����Ʒ�����ﳵ�ķ���
	private boolean addToCart(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, SQLException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		int number = Integer.parseInt(request.getParameter("num"));
		System.out.println(number);
		Goods item = new Goods();
		item.setId(id);
		item = gs.showGoodsDetail(item);
		
		
		//�Ƿ��ǵ�һ�θ����ﳵ�����Ʒ,��Ҫ��session�д���һ���µĹ��ﳵ����
		if(request.getSession().getAttribute("cart")==null)
		{
			Cart cart = new Cart();
			request.getSession().setAttribute("cart",cart);
		}
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart.addGoodsInCart(item,number))
		{
			request.getSession().setAttribute("cart", cart);//ˢ�¹��ﳵ
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	//�ӹ��ﳵ��ɾ����Ʒ(��session)
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
		System.out.println("�ú�");
	    	cart=cart.removeGoodsFromCart(item);
	    	 HashMap<Goods,Integer> goods1 = cart.getGoods();
	         Set<Goods> items1 = goods1.keySet();
	         Iterator<Goods> it1 = items1.iterator();
	         
	         while(it1.hasNext())
	         {Goods i = it1.next();
	         System.out.println(i.toString());
	         }
	        	 
	    	request.getSession().setAttribute("cart", cart);//ˢ�¹��ﳵ
	    	return true;
	   
	}
	
	//�Ѵӹ��ﳵ�е��ύ��������ʱ����TemporaryOrder,���ύ���Ĺ��ﳵ������ʱ����������ɾ�����ﳵ
	private void  buildOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cart TemporaryOrder=new Cart();
		TemporaryOrder=(Cart)request.getSession().getAttribute("cart");
		request.getSession().removeAttribute("cart");
		request.getSession().setAttribute("TemporaryOrder", TemporaryOrder);

		
	}

}
