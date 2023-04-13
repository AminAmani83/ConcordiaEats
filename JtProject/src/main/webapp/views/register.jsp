<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Register</title>
    <style>
        /* Custom styles for the form */
        .form-group label {
            font-weight: bold;
        }
        .form-group input[type="checkbox"] {
            margin-left: 10px;
        }
        .form-group small {
            margin-top: 5px;
            font-style: italic;
        }
        .form-group .error {
            border-color: #dc3545;
        }
    </style>
</head>
<body>

<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <div class="col-sm-6 mx-auto"> <!-- Add mx-auto class to center the form -->
            <h3 style="margin-top: 10px">Sign Up Now</h3>
            <p>Please fill out this form to register</p>
            <form action="newuserregister" method="post" modelAttribute="customer">
                <div class="form-group">
                    <label for="firstName">User Name</label>
                    <input type="text" name="username" id="firstName" required placeholder="Your Username*"
                           class="form-control form-control-lg">
                </div>
                <div class="form-group">
                    <label for="email">Email address</label>
                    <input type="email" class="form-control form-control-lg" required minlength="6" placeholder="Email*"
                           name="email" id="email"
                           aria-describedby="emailHelp">
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" class="form-control form-control-lg" required minlength="10"
                           placeholder="Phone Number*" name="phone" id="phone"
                           aria-describedby="phoneHelp">
                    <small id="phoneHelp" class="form-text text-muted">We'll never share your phone number with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <div class="input-group">
                        <input type="password" class="form-control form-control-lg" required placeholder="Password*"
                               name="password" id="password"
                               pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*?[~`!@#$%\^&*()\-_=+[\]{};:\x27.,\x22\\|/?><]).{8,}"
                               title="Must contain: at least one number, one uppercase letter, one lowercase letter, one special character, and 8 or more characters">
                        <div class="input-group-append">
                            <div class="input-group-text">
                                <input type="checkbox" onclick="showPassword()"> Show Password
                            </div>
                        </div>
                    </div>
               
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <textarea class="form-control form-control-lg" rows="3" placeholder="Enter Your Address"
                          name="address" id="address"></textarea>
            </div>
            <span style="margin-top: 10px">Already have an account <a class="linkControl"
                                                                      href="index">Login here</a></span> <br><br>
            <button type="submit" class="btn btn-primary btn-block">Register</button>
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
    // Add validation styles to form inputs
    $('input, textarea').on('keyup blur', function () {
        if ($(this).is(':invalid')) {
            $(this).addClass('error');
        } else {
            $(this).removeClass('error');
        }
    });
</script>
               </section>
</body>
</html>