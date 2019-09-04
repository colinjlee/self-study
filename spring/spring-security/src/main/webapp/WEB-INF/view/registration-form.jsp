<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="en">
	<head>
		<title>Register New User Form</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
		
		<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		
		<style>
			.error {
				color: red;
			}
		</style>
	</head>
	
	<body>
		<div>
			<div id="loginBox" style="margin-top: 50px;"
				 class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading">
						<div class="panel-title">
							<label>Register New User</label>
						</div>
					</div>
				
					<div style="padding-top: 30px" class="panel-body">
					
						<!-- Registration Form -->
						<form:form action="${pageContext.request.contextPath}/register/processRegistrationForm"
								   modelAttribute="crmUser"
								   class="form-horizontal">
						
							<!-- Alert messages -->
							<div class="form-group">
								<div class="col-xs-15">
									<div>
									
										<!-- Check for registration error -->
										<c:if test="${param.error != null}">
											<div class="alert alert-danger col-xs-offset-1 col-xs-10">
												<label>${registrationError}</label>
											</div>
										</c:if>
										
									</div>
								</div>
							</div>
							
							<!-- Username -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<form:errors path="userName" cssClass="error" />
								<form:input path="userName" placeholder="username (*)" class="form-control" />
							</div>
							
							<!-- Password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								
								<form:errors path="password" cssClass="error" />
								<form:input path="password" placeholder="password (*)" class="form-control" />
							</div>
							
							<!-- Confirm password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								
								<form:errors path="matchingPassword" cssClass="error" />
								<form:input path="matchingPassword" placeholder="confirm password (*)" class="form-control" />
							</div>
							
							<!-- First name -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<form:errors path="firstName" cssClass="error" />
								<form:input path="firstName" placeholder="first name (*)" class="form-control" />
							</div>
							
							<!-- Last name -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<form:errors path="lastName" cssClass="error" />
								<form:input path="lastName" placeholder="last name (*)" class="form-control" />
							</div>
							
							<!-- Email -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<form:errors path="email" cssClass="error" />
								<form:input path="email" placeholder="email (*)" class="form-control" />
							</div>
							
							<!-- Roles -->
							<div style="margin-bottom: 25px" class="input-group">
								<form:select path="formRole" items="${roles}" class="form-control" />
							</div>
							
							<!-- Submit button -->
							<div style="margin-top 10px" class="form-group">
								<div class="col-sm-6 controls">
									<button type="submit" class="btn btn-primary">Register</button>
								</div>
							</div>
							
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>