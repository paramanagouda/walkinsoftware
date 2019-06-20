<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mobiledog-login</title>
</head>
<body>
	<font color="red">${errorMessage}</font>
	<form method="post" action="loginByPassword">
		User Name : <input type="text" name="userName" /> Password : <input
			type="password" name="password" /> <input type="submit" />
	</form>
</body>
</html>