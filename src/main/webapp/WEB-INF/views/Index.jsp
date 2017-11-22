<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Schedular | Home</title>
</head>
<body>

	<label>Schedular Status</label>
	<input type="radio" name="isSchedularON" value="1"
		<c:if test="${isSchedularON == true }">checked="checked"</c:if> />ON
	<input type="radio" name="isSchedularON" value="0"
		<c:if test="${isSchedularON == false }">checked="checked"</c:if> />OFF


	<script src="http://code.jquery.com/jquery-latest.min.js"
		type="text/javascript"></script>
	<script>
		$('input[type=radio][name=isSchedularON]').change(function() {
			$.ajax({
				url : "/schedular?isSchedularON=" + $(this).val(),
				type : "GET"
			})
		});
	</script>
</body>
</html>