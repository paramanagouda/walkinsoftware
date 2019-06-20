<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mobiledog-Welcome</title>
</head>
<body>
	Welcome ${userdetails.fullName}!!
	<br></br>

	<jsp:forward page="userdetailslist.jsp">
<jsp:param name="userName" value="${ userdetails.userName }" /></jsp:forward>
</body>
</html>