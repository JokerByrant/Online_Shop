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
	private String action ; //��ʾ���ﳵ�Ķ��� ,add,show,delete
	//��Ʒҵ���߼���Ķ���
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
			
			
			if(action.equals("delete")) //�����ִ��ɾ�����ﳵ�е���Ʒ
			{
					this.deleteFromTemporaryOrder(request,response);
					request.getRequestDispatcher("/temporaryOrder.jsp").forward(request, response);		
			}
			
			
			if(action.equalsIgnoreCase("sumbitTemporaryOrder")){//�ύ�����ʱ����������������ַ���棬��д��ַ���ſɳ�Ϊ�����Ķ���
				
				this.sumbitTemporaryOrder(request, response);
				request.getRequestDispatcher("/address.jsp").forward(request, response);//����ַ����	
			}
			
			
			if(action.equals("cancel")){//ȡ���������
				request.getSession().removeAttribute("TemporaryOrder");
				request.getRequestDispatcher("/temporaryOrder.jsp").forward(request, response);
			}
			
			if(action.equals("sumbitOrder")){//�ύ�������
				becomeOrder(request, response);
				request.getRequestDispatcher("/shoppingok.jsp").forward(request, response);
				
			}
			
			if(action.equals("showOrder")){//��ʾ����
				showOrders(request, response);
			}
			
			if(action.equals("showDetail")){//��ʾ����ϸ��
				showDetails(request, response);
			}
			
			if(action.equals("delOrder")){//ɾ������
				delOrder(request,response);
			}
					
			
		}else{
			response.sendRedirect("/error.jsp");
		
		}
		
	
			
	}

	//�Ӷ�����ɾ����Ʒ(��session���TemporaryOrder���и��ģ�����TemporaryOrderˢ��)
		private void deleteFromTemporaryOrder(HttpServletRequest request, HttpServletResponse response) 
		{
			int id = Integer.parseInt(request.getParameter("id"));
			Cart temporaryOrder = (Cart)request.getSession().getAttribute("TemporaryOrder");
		    Goods item = new Goods();
		    item.setId(id);
			try {
				//ͨ��id�ҵ�item
				item = gs.showGoodsDetail(item);
				//�޸Ķ���
				temporaryOrder.removeGoodsFromCart(item);					   
		    	request.getSession().setAttribute("TemporaryOrder", temporaryOrder);//ˢ�¶���
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		   
		    
		}
	//�ύ��ʱ�������־û������ʱ����������������͸�����ʱ�����־û�����ϸ�ڣ�
		private void sumbitTemporaryOrder(HttpServletRequest request, HttpServletResponse response){
			order=new Order();
			User user=(User)request.getSession().getAttribute("user");
			System.out.println(user);
			java.util.Date date = new java.util.Date ();
	        int data = (int) date.getTime();
	      //�Լ����ɵ�ַid
	        String oid=user.getId()+data+"";
	        oid=oid.substring(1, 10);
	    
	        
			order.setId(oid);
			order.setUser(user);
			order.setDate(new Date(0));
			order.setStatus(0);
			//�������ʱ�����ύ�����ݿ�t_order��t_detail,���Ƕ�����status=false,��ַҲΪ�գ���Ҫ��д��ַ����£��ſɱ�Ϊ��������
			try {
				idao.addorder(order);
			//�Ѷ�����ϸ��Ҳ�������ݿ�
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
			
	//������������(���ɵ�ַ�����ĸö�����״̬�����־û�)
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
	      //�Լ����ɵ�ַid
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
				
				//��ӵ�ַ�����ݿ�
				idao.addaddress(address);
				//���Ķ�����״̬
				idao.changeorder(1, addr_id, order.getId());
				request.getSession().removeAttribute("TemporaryOrder");//������ɺ��ɾ������
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
			
	//��ʾ���� 
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
		
	// ��ʾ����ϸ��
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
		
	// ɾ������
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
