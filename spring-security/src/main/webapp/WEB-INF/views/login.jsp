<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token}"/>
<title>Login</title>
</head>
<body>
<form id="loginForm">
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
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
	var loginForm = document.getElementById('loginForm');
	loginForm.addEventListener('submit', function(e) {
		e.preventDefault();
		
		var username = document.getElementById('username').value;
		var password = document.getElementById('password').value;
		var token = document.querySelector('meta[name="_csrf"]').getAttribute("content");
		
		var data = {
				username: username,
				password: password,
		};
		
		axios.defaults.headers['X-CSRF-TOKEN'] = token;
		axios.post('/spring/api/post', data)
			.then(function(response) {
				console.log(response);
			})
			.catch(function(error) {
				console.log(error);
			});
		
	});
</script>
</body>
</html>