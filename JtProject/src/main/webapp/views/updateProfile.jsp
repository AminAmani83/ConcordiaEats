<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <style>
        .main-container {
            margin-top: 2rem;
        }
    </style>
    <title>ConcordiaEats - User Profiles</title>
</head>
<body>
<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <div class="container main-container">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <h4 class="mt-3">User Profile</h4>
                    <hr>
                    <form action="updateuser" method="post">
                        <div class="form-group">
                            <label for="username">User Name</label>
                            <input type="text" name="username" id="username" required placeholder="Your Username*"
                                   value="${username}" class="form-control form-control-lg">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control form-control-lg" required placeholder="Password*"
                                   value="${password}" name="password" id="password" autocomplete="off">
                            <div class="form-check mt-2">
                                <input type="checkbox" class="form-check-input" id="showPassword" onclick="showPassword()">
                                <label class="form-check-label" for="showPassword">Show Password</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" class="form-control form-control-lg" required minlength="6" placeholder="Email*"
                                   value="${email}" name="email" id="email"
                                   aria-describedby="emailHelp">
                            <small id="emailHelp" class="form-text text-muted">We'll never share your email with
                                anyone else.</small>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="text" class="form-control form-control-lg" minlength="10" placeholder="Phone"
                                   value="${phone}" name="phone" id="phone">
                        </div>
                        <div class="form-group">
                            <label for="address">Address</label>
                            <textarea class="form-control form-control-lg" rows="3" placeholder="Enter Your Address"
                                      name="address" id="address">${address}</textarea>
                        </div>
                             <input type="submit" value="Update Profile" class="btn btn-primary btn-block mb-3">

                </form>
            </div>
        </div>
    </div>
    <%@include file="common/footer.jsp" %>
</div>
</section>
<script>
    function showPassword() {
        var x = document.getElementById("password");
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }
</script>
</body>
</html>