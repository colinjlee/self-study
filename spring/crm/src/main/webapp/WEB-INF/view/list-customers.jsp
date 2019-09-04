<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>

<html>
	<head>
		<title>List Customers</title>
		
		<link type="text/css" 
			  rel="stylesheet" 
			  href="${pageContext.request.contextPath}/resources/css/style.css" />
	</head>
	
	<body>
		<div id="wrapper">
			<div id="header">
				<h2>CRM - Customer Relationship Manager</h2>
			</div>
		</div>
		
		<div id="container">
			<div id="content">
			
				<p>
					<label>User: </label>
					<security:authentication property="principal.username" />
					<label>, Role(s): </label>
					<security:authentication property="principal.authorities" />
				</p>
			
				<!-- Add new customer button -->
				<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
					<input type="button" value="Add Customer" 
						   onclick="window.location.href='showFormForAdd'; return false;"
						   class="add-button" />
				</security:authorize>
					   
				<!-- Search bar -->
				<form:form action="search" method="GET">
					<label>Search customer:</label>
					<input type="text" name="searchName" />
					<input type="submit" value="Search" class="add-button" />
					<input type="button" value="Show Full List" 
					   onclick="window.location.href='list'; return false;"
					   class="add-button" />
				</form:form>
			
				<!-- Customer table -->
				<table class="customer-list-table">
					<thead>
						<tr>
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Email</th>
							
							<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
								<th>Action</th>
							</security:authorize>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="tempCustomer" items="${customers}">
						
							<!-- Make update link -->
							<c:url var="updateLink" value="/customer/showFormForUpdate">
								<c:param name="customerId" value="${tempCustomer.id}" />
							</c:url>
							
							<!-- Make delete link -->
							<c:url var="deleteLink" value="/customer/delete">
								<c:param name="customerId" value="${tempCustomer.id}" />
							</c:url>
						
							<tr>
								<td></td>
								<td>${tempCustomer.firstName}</td>
								<td>${tempCustomer.lastName}</td>
								<td>${tempCustomer.email}</td>
								<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
									<td>
										<security:authorize access="hasAnyRole('MANAGER', 'ADMIN')">
											<a href="${updateLink}">Update</a>
										</security:authorize>
									
										<security:authorize access="hasAnyRole('ADMIN')">
											<label>|</label>
											<a href="${deleteLink}"
											   onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false">Delete</a>
										</security:authorize>
									</td>
								</security:authorize>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<br>
				
				<form:form action="${pageContext.request.contextPath}/logout" method="POST">
					<input type="submit" value="Logout" class="add-button" />
				</form:form>
			</div>
		</div>
	</body>
</html>