<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <div><label> User Name : </label><input type="text" name="username" id="username"/></div>
    <div><label> Password: </label><input type="password" name="password" id="password"/></div>
    <div><input type="submit" value="Sign In"/></div>
</form>
<c:if test="${param.error != null}">
	<p>Invalid username and password.</p>
</c:if>
<c:if test="${param.logout != null}">
	<p>You have been logged out.</p>
</c:if>
</body>
</html>