<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>我的订单</h1>
   <a href="GoodsAction?action=ShowAllGoods">首页</a> > <a href="GoodsAction?action=ShowAllGoods">商品列表</a>
   <hr> 
   <div id="shopping">
   <form action="ShoppingAction?action=sumbitCart" method="post">		
			<table>
				<tr>
					<th>订单号</th>
					<th>日期</th>
					<th>订单状态</th>
					<th>地址</th>
					<th>操作</th>
					<th></th>
				</tr>
				
				<c:forEach var="order" items="${orders }">
					<tr>
					<th><a href="OrderAction?action=showDetail&oid=${order.id }">${order.id }</a></th>
					<th>${order.date }</th>
					<th>${order.status }</th>
					<th>${order.address.province } ${order.address.city }</th>
					<c:if test="${order.status==0 }">
						<th><a href="OrderAction?action=sumbitTemporaryOrder&oid=${order.id }">提交</a></th>
					</c:if>
					<th><a href="OrderAction?action=delOrder&oid=${order.id }">删除</a></th>
				</tr>
				</c:forEach>
				
			</table>
	</form>
	</div>
</body>
</html>