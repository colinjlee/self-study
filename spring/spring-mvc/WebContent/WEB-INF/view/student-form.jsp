<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Student Registration Form</title>
	</head>

	<body>
		<form:form action="processForm" modelAttribute="student">
		
		First name: <br>
		<form:input path="firstName" />
		<br><br>
		Last name: <br>
		<form:input path="lastName" />
		<br><br>
		Country: <br>
		<form:select path="country">
			<form:options items="${ countryOptions }" />
		</form:select>
		<br><br>
		Choose your gender: <br>
		Male <form:radiobutton path="gender" value="Male"/>
		Female <form:radiobutton path="gender" value="Female"/>
		Other <form:radiobutton path="gender" value="Other"/>
		<br><br>
		Operating Systems: <br>
		Windows <form:checkbox path="operatingSystems" value="Windows" />
		Linux <form:checkbox path="operatingSystems" value="Linux" />
		Mac OS <form:checkbox path="operatingSystems" value="Mac OS" />
		<br><br>
		<input type="submit" value="Submit" />
		
		</form:form>
	</body>
</html>