<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
	<head>
		<title>Login Page</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
		
		<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</head>
	
	<body>
		<div>
			<div id="loginBox" style="margin-top: 50px;"
				 class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">
							<label>Sign In</label>
						</div>
					</div>
				
					<div style="padding-top: 30px" class="panel-body">
					
						<!-- Login Form -->
						<form:form action="${pageContext.request.contextPath}/authenticateUser"
								   method="POST" class="form-horizontal">
						
							<!-- Alert messages -->
							<div class="form-group">
								<div class="col-xs-15">
									<div>
									
										<!-- Check for login error -->
										<c:if test="${param.error != null}">
											<div class="alert alert-danger col-xs-offset-1 col-xs-10">
												<label>Invalid username or password</label>
											</div>
										</c:if>
										
										<!-- Check for logout -->
										<c:if test="${param.logout != null}">
											<div class="alert alert-success col-xs-offset-1 col-xs-10">
												<label>You've been logged out</label>
											</div>
										</c:if>
										
									</div>
								</div>
							</div>
							
							<!-- Username -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<input type="text" name="username" placeholder="username" class="form-control">
							</div>
							
							<!-- Password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								
								<input type="password" name="password" placeholder="password" class="form-control">
							</div>
							
							<!-- Login/submit button -->
							<div style="margin-top 10px" class="form-group">
								<div class="col-sm-6 controls">
									<button type="submit" class="btn btn-success">Login</button>
								</div>
							</div>
							
							<!-- Manually add CSRF token -->
							<!-- <input type="hidden"
								   name="${_csrf.parameterName}"
								   value="${_csrf.token}" />
							-->
						</form:form>
					</div>
				</div>
				
				<div>
					<a href="${pageContext.request.contextPath}/register/showRegistrationForm" 
					   class="btn btn-primary" 
					   role="button" 
					   aria-pressed="true">Register New User</a>
				</div>
				
			</div>
		</div>
	</body>
</html>