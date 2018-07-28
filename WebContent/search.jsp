 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

h1,h2,form {
	  text-align: center;
}

h1{
 	width:80%;
 	height:100px;
 	background:red;
 	color:white;
 	line-height:100px;
 	font-size:48px;
 	margin:30px auto;
}

table{
	width:80%;
	margin:50px auto;
	border-collapse:collapse;

}

img{
	width:160px;
	height:100px;
	
}

#item{
	width:91%;
	height:70px;
	margin:0 auto;
}

#item table{
	width:100%;
	height:100px;
}

#item td{
	width:30%;
	height:100px;
	background:yellow;
	opacity:0.3;
	line-hright:100px;
	font-size:25px;
	text-align:center;	
	transition:all 1s;
}

#item td:hover{
	background:green;
	opacity:0.3;
	cursor:pointer;
}


#foot{
	position:fixed;
	bottom:0;
	width:100%;
	height:50px;
	background:blue;
	opacity:0.3;
}
#footer{
	position:fixed;
	bottom:0;
	width:100%;
	height:50px;
	font-size:20px;
}

#footer a:hover{
	color:red;
}
#footer #logout{
	margin-right:30px;
}
#footer #showCart{
	float:right;
	margin-right:50px;
}
table .choose{
	width:30px;
}

</style>

</head>
<body>

	<h1>搜索页</h1>
		<c:if test="${user!=null }">欢迎你：${user.name }</c:if>
	<c:if test="${user==null }">您尚未登陆</c:if>
   	<a href="GoodsAction?action=ShowAllGoods">返回首页</a>
	
	<form action="GoodsAction" method="get">
		<input type="text" name="key" placeholder="请输入搜索关键词">
		<input type="hidden" name="action" value="SearchGoods"/>
		<button type="submit">search</button>
	</form> 
	
	<c:if test="${size == 0 }">
		<h1>您搜索的商品不存在</h1>
	</c:if>
	<div id="item">
		<table>
			<c:forEach var="kind" items="${goodskind }">
				<td><a href="GoodsAction?action=ShowGoodsByKind&kid=${kind.id }">${kind.name }</a></td>
			</c:forEach>
		</table>
	</div>
		<table>
			<tr>
				<td></td>
				<td>商品名</td>
				<td>图片</td>
				<td>价格</td>
				<td>描述</td>
				<td>评价</td>
			</tr>
			
			<c:if test="${search != null }">
				<c:forEach var="goods" items="${search }">
				<tr>
					<c:set var="count" value="${count+1 }"/>
					<input type="hidden" id="gid${count }" value="${goods.id }"/>
					<td><input id="selected" type="checkbox"/></td>
					<td><a href="GoodsAction?action=ShowDetails&gid=${goods.id }">${goods.name }</a></td>
					<td><a href="GoodsAction?action=ShowDetails&gid=${goods.id }"><img src="${goods.small_img }"></a></td>
					<td>${goods.price }</td>
					<td>${goods.brief_info }</td>
					<td><a href="GoodsAction?action=ShowDetails&gid=${goods.id }">${goods.score }分</a></td>
					<td><input class="choose" id="choose${count }" type="number" min="1" value="1"/></td>
					<td><a href="#" id="addToCart${count }">加入购物车</a></td>
				</tr>
			</c:forEach>
			</c:if>	
		</table>
		
	<div id="foot"></div>
	
	<div id="footer">
		<c:if test="${user!=null }">
			<a id="logout" href="UserAction?action=logout&uid=${user.id }">注销</a>
			<a href="OrderAction?action=showOrder">显示订单</a>
			<a id="showCart" href="ShoppingAction?action=show">显示购物车</a>
			<a href="CommentAction?action=ShowAllCommentsUnderUser">显示评论</a>
		</c:if>
		
		<c:if test="${user==null }">
			<a href="login.html">登录</a>
			
			<a id="showCart" href="ShoppingAction?action=show">显示购物车</a>
			

		</c:if>

		

	
	<c:if test="${page.currentPage != 1 }">
		<a href="GoodsAction?action=SearchGoods&currentPage=${page.currentPage-1}">上一页</a>
	</c:if>
	<c:if test="${page.currentPage < page.total }">
		<a href="GoodsAction?action=SearchGoods&currentPage=${page.currentPage+1}">下一页</a>
	</c:if>
	</div>

</body>
</html>