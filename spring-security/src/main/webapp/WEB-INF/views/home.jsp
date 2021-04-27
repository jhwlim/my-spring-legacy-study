<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Welcome Home!</title>
</head>
<body>
<h1>Welcome!</h1>
<p>Click! <a href="<c:url value='/hello'/>">here</a> to see a greeting.</p>
</body>
</html>
