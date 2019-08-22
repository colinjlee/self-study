<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${ pageContext.request.contextPath }/resources/css/test.css">
		<script src="${ pageContext.request.contextPath }/resources/js/test.js"></script>
	</head>
	<body>
		<h2>Spring MVC Demo - Home Page</h2>
		
		<img alt="menuImage" 
			src="${ pageContext.request.contextPath }/resources/images/menu.jpg"
			class="img">
		
		<hr>
		
		<a href="showForm">Hello World Form</a>
		<br><br>
		<a href="student/showForm">Student Form</a>
		<br><br>
		<a href="customer/showForm">Customer Form</a>
		
		<hr>
		
		<input type="button" 
			onclick="doSomething()" 
			value="Click Me"
			class="btn" />
	</body>
</html>