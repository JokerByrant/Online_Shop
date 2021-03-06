<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="entity.Goods"%>
<%@page import="entity.Cart"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1>我的购物车</h1>
 	<c:if test="${user!=null }">欢迎你：${user.name }</c:if>
	<c:if test="${user==null }">您尚未登陆</c:if>
   <a href="GoodsAction?action=ShowAllGoods">首页</a> > <a href="GoodsAction?action=ShowAllGoods">商品列表</a>
   
   <hr> 
   <div id="shopping">
   <form action="ShoppingAction?action=sumbitCart" method="post">		
			<table>
				<tr>
					<th>商品名称</th>
					<th>商品单价</th>
					<th>商品价格</th>
					<th>购买数量</th>
					<th>操作</th>
				</tr>
				<% 
				   //首先判断session中是否有购物车对象
				   if(request.getSession().getAttribute("cart")!=null)
				   {
				%>
				<!-- 循环的开始 -->
				     <% 
				         Cart cart = (Cart)request.getSession().getAttribute("cart");
				         HashMap<Goods,Integer> goods = cart.getGoods();
				         Set<Goods> items = goods.keySet();
				         Iterator<Goods> it = items.iterator();
				         
				         while(it.hasNext())
				         {
				        	 Goods i = it.next();
				     %> 
				<tr name="products" id="product_id_1">
					<td class="thumb"><a href="#"><%=i.getName()%></a></td>
					<td class="number"><%=i.getPrice() %></td>
					<td class="price" id="price_id_1"><span><%=i.getPrice()*goods.get(i) %></span><input type="hidden" value="" /></td>
					<td class="number"><%=goods.get(i)%></td>                        
                    <td class="delete"><a href="ShoppingAction?action=delete&id=<%=i.getId()%>">删除</a></td>					                  
				</tr>
				     <% 
				         }
				     %>
				<!--循环的结束-->
				
			</table>
			 <div class="total"><span id="total">总计：<%=cart.getTotalPrice() %>￥</span></div>
			  <% 
			    }else{ %>
			    	<h3>购物车内没有物品，快去购物吧</h3>
			   
			<%   }  %>
			<div class="button"><input type="submit" value="提交" /><a href="ShoppingAction?action=empty"><input type="button" value="清空"></a></div>
		</form>
	</div>
</body>
</html>