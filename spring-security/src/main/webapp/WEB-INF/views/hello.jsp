<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello World!</title>
</head>
<body>
<h1>Hello!</h1>
<form action="<c:url value='logout'/>" method="POST">
	<input type="submit" value="Sign Out" />
</form>
</body>
</html>