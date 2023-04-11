<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <link rel="stylesheet"
          href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
          crossorigin="anonymous">
    <title>Customer Management</title>
</head>
<body class="bg-light">
<%@include file="adminHeader.jsp" %>
<div class="container">
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Address</th>
            <th scope="col">Phone</th>
            <th scope="col">Delete</th>
            <th scope="col">Update</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allCustomers}" var="customer">
            <tr>
                <!-- Displaying values -->
                <td><c:out value="${customer.userId}"/></td>
                <td><c:out value="${customer.username}"/></td>
                <td><c:out value="${customer.email}"/></td>
                <td><c:out value="${customer.address}"/></td>
                <td><c:out value="${customer.phone}"/></td>

                <!-- Deleting a customer -->
                <td>
                    <form action="customers/delete" method="get">
                        <input type="hidden" name="id" value="<c:out value="${customer.userId}" />">
                        <input type="submit" value="Delete" class="btn btn-danger">
                    </form>
                </td>

                <!-- Updating the customer -->
                <td>
                    <form action="customers/update" method="get">

                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-warning" data-toggle="modal"
                                data-target="#exampleModalCenter2-${customer.userId}"
                                onclick="document.getElementById('customerName').value =  '<c:out
                                        value="${customer.username}"/>'; document.getElementById('customerid').value =
                                        '<c:out value="${customer.userId}"/>';">Update
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModalCenter2-${customer.userId}" tabindex="-1"
                                role="dialog" aria-labelledby="exampleModalCenterTitle"
                                aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">

                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Update
                                            Customer Details</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body text-center">

                                        <input class="form-control" type="hidden"
                                        name="customerId" id="customerId" value="${customer.userId}">

                                        <div class="form-group">
                                            <input class="form-control" type="text"
                                                    readonly="readonly" name="customerName" id="customerName" value="${customer.username}">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="email"
                                                    id="email"
                                                    value="${customer.email}">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="address"
                                                    id="address"
                                                    value="${customer.address}">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="phone"
                                                    id="phone"
                                                    value="${customer.phone}">
                                        </div>

                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary"
                                                data-dismiss="modal">Close
                                        </button>
                                        <button type="submit" class="btn btn-primary">Update
                                            changes
                                        </button>
                                    </div>

                                </div>
                            </div>
                        </div>

                    </form>
                </td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>