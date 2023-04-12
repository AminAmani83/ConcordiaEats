<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Admin Login</title>
</head>
<body class="bg-dark">

<div class="container my-5" style="width: 1800px;"><br>

    <div class="jumbotron border col-sm-5 mx-auto">
        <h2 class="text-center">Admin Login</h2><br>
        <form action="loginvalidate" method="post">
            <div class="form-group">
                <label for="username">Username :</label>
                <input type="text" name="username" id="username" placeholder="Admin username" required
                       class="form-control form-control-lg border border-danger">
            </div>

            <div class="form-group">
                <label for="password">Password :</label>
                <input type="password" class="form-control form-control-lg border border-danger	"
                       placeholder="Admin Password" required name="password" id="password">
            </div>
            <br>

            <input type="submit" value="Login" class="btn btn-primary btn-block">
            <br>
            <h3 style="color:red;">${message }</h3>
            <br>
        </form>
    </div>

</div>

</body>
</html>