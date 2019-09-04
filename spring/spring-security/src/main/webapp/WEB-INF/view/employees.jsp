<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Employees Page</title>
	</head>
	
	<body>
		<h2>Employees Page</h2>
		
		<hr>
		
		<h3>Welcome <security:authentication property="principal.username" />!</h3>
		
		<hr>
		
		<!-- Display username and role -->
		<p>
			<label>Your role(s): </label>
			<security:authentication property="principal.authorities" />
		</p>
		
		<hr>
		
		<security:authorize access="hasRole('MANAGER')">
			<p>
				<a href="${pageContext.request.contextPath}/managers">Managers Page</a>
			</p>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<p>
				<a href="${pageContext.request.contextPath}/admins">Admins Page</a>
			</p>
		</security:authorize>
		
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<input type="submit" value="Logout" />
		</form:form>
	</body>
</html>