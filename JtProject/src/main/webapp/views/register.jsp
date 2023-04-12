<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Register</title>
</head>
<body>

<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <div class="col-sm-6">
            <h3 style="margin-top: 10px">Sign Up Now</h3>
            <p>Please fill out this to register</p>
            <form action="newuserregister" method="post" modelAttribute="customer">
                <div class="form-group">
                    <label for="firstName">User Name</label>
                    <input type="text" name="username" id="firstName" required placeholder="Your Username*" required
                           class="form-control form-control-lg">
                </div>
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control form-control-lg" required minlength="6" placeholder="Email*"
                           required name="email" id="email"
                           aria-describedby="emailHelp">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with
                        anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" class="form-control form-control-lg" required minlength="10"
                           placeholder="Phone Number*" required name="phone" id="phone"
                           aria-describedby="phoneHelp">
                    <small id="phonelHelp" class="form-text text-muted">We'll never share your phone number with
                        anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control form-control-lg" required placeholder="Password*"
                           required
                           name="password"
                           id="password"
                           pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*?[~`!@#$%\^&*()\-_=+[\]{};:\x27.,\x22\\|/?><]).{8,}"
                           title="Must contain:
                       at least one number, one uppercase letter, one lowercase letter, one special character, and 8 or more characters"
                           required>
                    <input type="checkbox" onclick="showPassword()">Show Password

                </div>
                <div class="form-group">
                    <label for="address">Address</label>
                    <textarea class="form-control form-control-lg" rows="3" placeholder="Enter Your Address"
                              name="address"
                              id="address"></textarea>
                </div>
                <span style="margin-top: 10px">Already have an account <a class="linkControl"
                                                                          href="index">Login here</a></span> <br><br>
                <input type="submit" value="Register" class="btn btn-primary btn-block"><br>

            </form>
        </div>
        <%@include file="common/footer.jsp" %>
    </div>

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

</section>

</body>
</html>