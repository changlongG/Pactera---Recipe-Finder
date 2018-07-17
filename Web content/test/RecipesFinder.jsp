<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.text.SimpleDateFormat,java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recipes Finder</title>
</head>
<body>
<h1>Recipes Finder</h1>
<form method="post" action="/SSHProject/UploadServlet" enctype="multipart/form-data">
   <%
   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
   Date dateToday = new Date();
   String dateTodayStr = sdf.format(dateToday);
   
   %>
   Date: <%out.println(dateTodayStr); %>
    <br/><br/>
    fridge (CSV) :
    <input type="file" name="uploadCSVFile" /> 
    recipes (Json) :
    <input type="file" name="uploadJsonFile" />
    <br/><br/>
    <input type="submit" value="submit" />
</form>
</body>
</html>