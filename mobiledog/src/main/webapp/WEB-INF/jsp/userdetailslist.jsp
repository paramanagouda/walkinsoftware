<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h2>Customer Manager</h2>
		<form method="get" action="queryUserDetailsList">
			User Name <input type="text" name="userName" /> &nbsp; 
			Mobile Number <input type="text" name="mobileNumber" /> &nbsp; <input
				type="submit" value="Search" />
		</form>
		<h3>
			<a href="/new">New Customer</a>
		</h3>
		<table border="1" cellpadding="5">
			<tr>
				<th>ID</th>
				<th>User Name</th>
				<th>Full Name</th>
				<th>Mobile</th>
				<th>E-mail</th>
				<th>Role</th>
				<th>Bar code</th>
			</tr>
			<c:forEach items="${userDetailsList}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.userName}</td>
					<td>${user.fullName}</td>
					<td>${user.mobileNumber}</td>
					<td>${user.emailId}</td>
					<td>${user.role.name}</td>
					<td>${user.barcode}</td>

				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>