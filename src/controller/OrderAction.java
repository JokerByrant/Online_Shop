package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.ior.Identifiable;
import com.sun.org.apache.xpath.internal.operations.Or;

import dao.Shoppingdao;
import entity.Address;
import entity.Cart;
import entity.Detail;
import entity.Goods;
import entity.Order;
import entity.User;
import service.GoodsService;
import service.imp.GoodsServiceImp;

/**
 * Servlet implementation class OrderAction
 */
@WebServlet("/OrderAction")
public class OrderAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String action ; //表示购物车的动作 ,add,show,delete
	//商品业务逻辑类的对象
	private Shoppingdao idao = new Shoppingdao();
	private Order order;
	private Detail orderdetail;
	private Address address;
	private GoodsService gs = new GoodsServiceImp();
	
       
   
    public OrderAction() {
        super();
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		if(request.getParameter("action")!=null)
		{
			this.action = request.getParameter("action");
			
			
			if(action.equals("delete")) //如果是执行删除购物车中的商品
			{
					this.deleteFromTemporaryOrder(request,response);
					request.getRequestDispatcher("/temporaryOrder.jsp").forward(request, response);		
			}
			
			
			if(action.equalsIgnoreCase("sumbitTemporaryOrder")){//提交这个暂时订单，但还需进入地址界面，填写地址，才可成为真正的订单
				
				this.sumbitTemporaryOrder(request, response);
				request.getRequestDispatcher("/address.jsp").forward(request, response);//到地址界面	
			}
			
			
			if(action.equals("cancel")){//取消这个订单
				request.getSession().removeAttribute("TemporaryOrder");
				request.getRequestDispatcher("/temporaryOrder.jsp").forward(request, response);
			}
			
			if(action.equals("sumbitOrder")){//提交这个订单
				becomeOrder(request, response);
				request.getRequestDispatcher("/shoppingok.jsp").forward(request, response);
				
			}
			
			if(action.equals("showOrder")){//显示订单
				showOrders(request, response);
			}
			
			if(action.equals("showDetail")){//显示订单细节
				showDetails(request, response);
			}
			
			if(action.equals("delOrder")){//删除订单
				delOrder(request,response);
			}
					
			
		}else{
			response.sendRedirect("/error.jsp");
		
		}
		
	
			
	}

	//从订单中删除商品(对session里的TemporaryOrder进行更改，并对TemporaryOrder刷新)
		private void deleteFromTemporaryOrder(HttpServletRequest request, HttpServletResponse response) 
		{
			int id = Integer.parseInt(request.getParameter("id"));
			Cart temporaryOrder = (Cart)request.getSession().getAttribute("TemporaryOrder");
		    Goods item = new Goods();
		    item.setId(id);
			try {
				//通过id找到item
				item = gs.showGoodsDetail(item);
				//修改订单
				temporaryOrder.removeGoodsFromCart(item);					   
		    	request.getSession().setAttribute("TemporaryOrder", temporaryOrder);//刷新订单
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		   
		    
		}
	//提交临时订单（持久化这个临时订单，并且在这里就根据临时订单持久化订单细节）
		private void sumbitTemporaryOrder(HttpServletRequest request, HttpServletResponse response){
			order=new Order();
			User user=(User)request.getSession().getAttribute("user");
			System.out.println(user);
			java.util.Date date = new java.util.Date ();
	        int data = (int) date.getTime();
	      //自己生成地址id
	        String oid=user.getId()+data+"";
	        oid=oid.substring(1, 10);
	    
	        
			order.setId(oid);
			order.setUser(user);
			order.setDate(new Date(0));
			order.setStatus(0);
			//把这个临时订单提交到数据库t_order和t_detail,但是订单的status=false,地址也为空，需要填写地址后更新，才可变为真正订单
			try {
				idao.addorder(order);
			//把订单的细节也加入数据库
				Cart temporaryOrder=(Cart) request.getSession().getAttribute("TemporaryOrder");
				 HashMap<Goods,Integer> goods = temporaryOrder.getGoods();
		         Set<Goods> items = goods.keySet();
		         Iterator<Goods> it = items.iterator(); 
		         while(it.hasNext())
		         {
		            Goods i = it.next();
		            orderdetail=new Detail();
		            orderdetail.setOrder(order);;
		            orderdetail.setGoods(i);;
		            orderdetail.setNum(goods.get(i));
		            idao.addorderdetail(orderdetail);
		         }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	//生成真正订单(生成地址，更改该订单的状态，并持久化)
		private void becomeOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
			String province = request.getParameter("province");
			String city = request.getParameter("city");
			String detail=request.getParameter("detail");
			province = new String(province.getBytes("ISO-8859-1"),"utf-8");
			city = new String(city.getBytes("ISO-8859-1"),"utf-8");
			detail = new String(detail.getBytes("ISO-8859-1"),"utf-8");
			User user=(User)request.getSession().getAttribute("user");
			java.util.Date date = new java.util.Date ();
	        int data = (int) date.getTime();
	      //自己生成地址id
	        String id=user.getId()+data+"";
	        id=id.substring(1, 4)+id.substring(6,10);
	        int addr_id=Integer.parseInt(id);
	        System.out.println(addr_id);
	        
	        address = new Address();
			address.setId(addr_id);
			address.setUser(user);
			address.setCity(city);
			address.setProvince(province);
			address.setDetail(detail);
			try {
				
				//添加地址到数据库
				idao.addaddress(address);
				//更改订单的状态
				idao.changeorder(1, addr_id, order.getId());
				request.getSession().removeAttribute("TemporaryOrder");//订单完成后就删除订单
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
			
	//显示订单 
		private void showOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
			User user = (User) request.getSession().getAttribute("user");
			List<Order> orders = null;
			try {
				orders = idao.getOrderById(user.getId());
				for(Order order:orders){
					Address address = new Address();
					address = order.getAddress();
					address = idao.showAddressByid(address.getId());
					order.setAddress(address);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(orders);
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("order.jsp").forward(request, response);
		}
		
	// 显示订单细节
		private void showDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String oid = request.getParameter("oid");
			List<Detail> details = null;
			try {
				details = idao.getdetailById(oid);
				for(Detail detail:details){
					Goods goods = detail.getGoods();
					goods = gs.showGoodsDetail(goods);
					detail.setGoods(goods);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(details);
			request.setAttribute("details", details);
			request.getRequestDispatcher("Orderdetail.jsp").forward(request, response);
		}
		
	// 删除订单
		private void delOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String oid = request.getParameter("oid");
			System.out.println(oid);
			boolean flag = idao.delOrderAndDetail(oid);
			
			if(flag){
				request.getRequestDispatcher("OrderAction?action=showOrder").forward(request, response);
			} else {
				response.sendRedirect("error.html");
			}
			
		}
			

			
	
	
}
