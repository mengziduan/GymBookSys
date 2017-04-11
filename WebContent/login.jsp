 <%@page import="java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <% 
String name=null;
String tel=null;
Cookie[] cookies=request.getCookies();	  
for(int i=0;i<cookies.length;i++){
 	if(cookies[i].getName().equals("username")){
 		name=URLDecoder.decode(cookies[i].getValue(),"utf-8");
 	}
 	if(cookies[i].getName().equals("tel"))
 	{
 		tel=cookies[i].getValue();
 	}
}
if(name==null) {
%>
   <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false" >个人中心<span class="caret"></span></a>
    <ul class="dropdown-menu" role="menu">
        <li><a href="#" data-toggle="modal" data-target="#myModal" >登录</a>
        <li><a href="#" data-toggle="modal" data-target="#myModal2" >注册</a>
    </ul>

  
<% } else {   %>
    <a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-expanded='false' id='uname'><%=name%><span class='caret'></span></a>
    <ul class='dropdown-menu' role='menu'>
        <li><a href='personal.html'>个人中心</a>
        <li><a  id="exit11" href="" onclick="exit(event)">注销</a>
    </ul>
 <%} %>
</body>
</html>