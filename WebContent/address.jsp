<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>

textarea{
	width:200px;
	height:80px;
	margin-top:30px;
}
input{
	width:200px;
	margin-top:30px;
}
button{
	margin-top:30px;
	width:60px;
}
</style>

</head>
<body>
<center>

<a href="GoodsAction?action=ShowAllGoods">首页</a> > <a href="GoodsAction?action=ShowAllGoods">返回上一级</a>

<form action="OrderAction?action=sumbitOrder" method="post">
	<input type="text" name="province" placeholder="省份"></br>
	<input type="text" name="city" placeholder="城市"></br>
	<textarea name="detail" placeholder="详细地址"></textarea></br>
	<button type="submit">提交</button>
	<button type="reset">重置</button>
</form>
</center>
</body>
</html>