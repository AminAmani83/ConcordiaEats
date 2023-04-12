<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Contact Us</title>
</head>

<body>
<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <div class="col-sm-6">
            <h4 class="mt-3">Contact Us</h4>
            <br>

            <form action="https://formcarry.com/s/Q45i2lxCRS" method="POST" accept-charset="UTF-8" class="mb-5">

                <div class="form-group">
                    <label for="username">Name / Username</label>
                    <input type="text" name="username" id="username" required placeholder="Your Name or Username*"
                           value="${username}" class="form-control form-control-lg">
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
                    <label for="message">Description</label>
                    <textarea class="form-control form-control-lg" cols="51" rows="6" name="message"
                              id="message"></textarea>
                </div>

                <input type="submit" value="Submit" class="btn btn-primary btn-block">
            </form>
        </div>
        <%@include file="common/footer.jsp" %>
    </div>
</section>

</body>
</html>
