<%@page import="java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
 <% 
String name=null;
String tel=null;
 Cookie[] cookies=request.getCookies();	  
 
 for(int i=0;i<cookies.length;i++){
 	if(cookies[i].getName().equals("username")){
 		name=URLDecoder.decode(cookies[i].getValue(),"utf-8");;
 	}
 	if(cookies[i].getName().equals("tel"))
 	{
 		tel=cookies[i].getValue();
 	}
 	}
 System.out.print(name);
 if(name==null) {%>
<a href="#" data-toggle="modal" data-target="#myModal" style="color:#ffffff">登录</a>
 <% } else {   %>
<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" ><%=name%><span class="caret"></span></a>
<ul class="dropdown-menu" role="menu">
    <li><a href="personal.html" >我的订单</a></li>
    <li><a href="perinfo.html" >个人信息</a></li>
    <li><a  id="exit11" href="" onclick="exit(event)">注销</a></li>
</ul>
<%} %>
</body>
</html>