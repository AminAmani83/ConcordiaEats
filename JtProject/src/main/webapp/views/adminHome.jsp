<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Admin Home Page</title>
</head>

<body class="bg-dark">
<%@include file="common/adminHeader.jsp" %>
<div class="jumbotron text-center">
    <h1 class="display-4">Welcome Back, Admin</h1>
    <hr>
    <p>Manage your data from this Admin Panel</p>
</div>
<br>
<div class="container-fluid">
    <div class="row justify-content-center">
        <div class="col-sm-3 pt-4">
            <div class="card border border-info" style="background-color: white;">
                <div class="card-body text-center">
                    <h4 class="card-title">Categories</h4>
                    <hr>
                    <p class="card-text">Manage all categories here</p>
                    <a href="/admin/categories" class="card-link btn btn-primary">Manage</a>

                </div>
            </div>
        </div>
        <div class="col-sm-3 pt-4">
            <div class="card" style="background-color: white;">
                <div class="card-body text-center">
                    <h4 class="card-title">Products</h4>
                    <hr>
                    <p class="card-text">Manage all products here</p>
                    <a href="/admin/products" class="card-link btn btn-primary">Manage</a>

                </div>
            </div>
        </div>
        <div class="col-sm-3 pt-4">
            <div class="card" style="background-color: white;">
                <div class="card-body text-center">
                    <h4 class="card-title">Customers</h4>
                    <hr>
                    <p class="card-text">Manage all customers here</p>
                    <a href="/admin/customers" class="card-link btn btn-primary">Manage</a>

                </div>
            </div>
        </div>
        <div class="col-sm-3 pt-4">
            <div class="card" style="background-color: white;">
                <div class="card-body text-center">
                    <h4 class="card-title">Promotions</h4>
                    <hr>
                    <p class="card-text">Manage all promotions here</p>
                    <a href="/admin/promotions" class="card-link btn btn-primary">Manage</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>