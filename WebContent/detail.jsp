<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

h1,form {
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

h2{
	font-size:40px;
}

#d1{
	width:80%;
	height:200px;
	margin:10px auto;
	border:1px solid #F00 ;
	text-align:center;
	line-height:100px;

}

#d2{
	width:80%;
	height:40%;
	margin:10px auto;
	border:1px solid #F00 ;
}

img{
	width:100%;
	height:100%;
}

#foot{
	position:fixed;
	bottom:0;
	width:100%;
	height:130px;
	background:blue;
	opacity:0.3;
}
#footer{
	position:fixed;
	bottom:0;
	width:100%;
	height:130px;
	font-size:20px;
}

#footer #login{
	position:absolute;
	bottom:0;
}

</style>

</head>
<body>
	<h1>商品详情页</h1>
		<c:if test="${user!=null }">欢迎你：${user.name }</c:if>
	<c:if test="${user==null }">您尚未登陆</c:if>
	<a href="GoodsAction?action=ShowAllGoods">返回首页</a>
	
	<div id="d1">
		<h2>商品名：${goods.name }</h2>
	</div>
	<div id="d1">
		<h2>商品介绍：${goods.detail_info }</h2>
	</div>
	<div id="d2">
		<img src="${goods.big_img }"/>
	</div>
	
	<c:if test="${comments!=null }">
	<table>
		<tr>
			<td>评分</td>
			<td>评论</td>
			<td>用户</td>
			<td></td>
		</tr>
		<c:set var="count" value="0"/>
		<c:forEach var="comment" items="${comments }">
			<tr>
				<c:set var="count" value="${count+1 }"/>
				<td>${comment.score }</td>
				<td>${comment.content }</td>
				<td>${comment.user.name }</td>
				<td>#${count }</td>
			</tr>
		</c:forEach>
	</table>
	</c:if>
	
	
	<c:if test="${comments==null }">
		<h2>暂无评论</h2>
	</c:if>
		
	<div id="foot"></div>
	<div id="footer">
		<c:if test="${flag==1 }">
		<form action="CommentAction" method="get">
			<input type="hidden" name="action" value="addComments"/>
			<input type="hidden" name="gid" value="${goods.id }"/>
			评分：<input type="text" name="score" placeholder="评分"></br>
			评价：<textarea name="content"> </textarea></br>
			<input type="submit" name="submit" value="添加评论">
		</form>
		</c:if>
		
		<c:if test="${flag==0 }">
			您未购买过该商品，不能评论
		</c:if>
		<div id="login">
			<c:if test="${user==null }"><a href="login.html">未登录</a></c:if>
			<c:if test="${user!=null }">已登录 <a id="logout" href="UserAction?action=logout&uid=${user.id }">注销</a></c:if>
		</div>
		
	</div>
</body>
</html>