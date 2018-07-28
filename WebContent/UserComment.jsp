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
</style>

</head>

<body>
   	<a href="GoodsAction?action=ShowAllGoods">返回首页</a>
	<c:if test="${comment!=null }">
	<table>
		<tr>
			<td>评分</td>
			<td>评论</td>
			<td>商品</td>
			<td>展示</td>
			<td></td>
		</tr>

		<c:set var="count" value="0"/>
		<c:forEach var="comment" items="${comments }">
			<tr>
				<c:set var="count" value="${count+1 }"/>
				<td>${comment.score }</td>
				<td>${comment.content }</td>
				<td><a href="GoodsAction?action=ShowDetails&gid=${comment.goods.id }">${comment.goods.name }</a></td>
				<td><img src="${comment.goods.small_img }"></td>
				<td>#${count }</td>
			</tr>
		</c:forEach>
				
	</table>
	</c:if>
	
	<c:if test="${comment==null }">
		<h2>暂无评论</h2>
	</c:if>
	
</body>
</html>