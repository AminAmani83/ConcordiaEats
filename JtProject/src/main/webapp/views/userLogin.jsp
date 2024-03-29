<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <style>
        .login-container {
            margin-top: 2rem;
        }
        .linkControl {
            text-decoration: none;
            color: #007bff;
        }
        .linkControl:hover {
            text-decoration: underline;
        }
    </style>
    <title>User Login</title>
</head>
<body>
<div class="container login-container">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <h2>User Login</h2>
            <form action="userloginvalidate" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" name="username" id="username" placeholder="Username*" required
                           class="form-control form-control-lg">
                </div>
                          <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control form-control-lg" placeholder="Password*" required
                       name="password" id="password">
            </div>
            <div class="form-group mb-4">
                <span>Don't have an account? <a class="linkControl" href="/register">Register here</a></span>
            </div>

            <input type="submit" value="Login" class="btn btn-primary btn-block">
            <br>
            <div class="text-center"><h6 class="text-danger">${message}</h6></div>
        </form>
    </div>
</div>
</div>
</body>
</html>