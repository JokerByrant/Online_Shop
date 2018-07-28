<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	img{
	width:160px;
	height:100px;
	
}
</style>

</head>
<body>
<h1>我的订单</h1>
   <a href="GoodsAction?action=ShowAllGoods">首页</a> > <a href="GoodsAction?action=ShowAllGoods">商品列表</a>
   <hr> 
   <div id="shopping">
   <form action="ShoppingAction?action=sumbitCart" method="post">		
			<table>
				<tr>
					<th>编号</th>
					<th>商品名</th>
					<th></th>
					<th>数量</th>
					<th>操作</th>
				</tr>
				
				<c:forEach var="detail" items="${details }">
				<tr>
					<th>${detail.id }</th>
					<th><a href="GoodsAction?action=ShowDetails&gid=${detail.goods.id }">${detail.goods.name }</a></th>
					<th><img src="${detail.goods.small_img }"/></th>
					<th>${detail.num }</th>
					<th>删除</th>
				</tr>
				</c:forEach>
				
			</table>
	</form>
	</div>
</body>
</html>