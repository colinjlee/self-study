<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<title>Custom Login Page</title>
	</head>
	
	<style>
		.failed {
			color: red;
		}
	</style>
	
	<body>
		<h2>Custom Login Page</h2>
		
		<form:form action="${ pageContext.request.contextPath }/authenticateUser"
				   method="POST">
				   
			<!-- Check for login error -->
			<c:if test="${param.error != null}">
				<i class="failed">Inavlid username or password</i>
			</c:if>
				   
			<p>
				<label>Username:</label>
				<input type="text" name="username" />
				
				<br>
				<br>
				
				<label>Password:</label>
				<input type="password" name="password" />
				
				<br>
				<br>
				
				<input type="submit" value="Login" />
			</p>
				   
		</form:form>
	</body>
</html>