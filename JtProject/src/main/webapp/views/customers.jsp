<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Customer Management</title>
</head>
<body class="bg-light">
<%@include file="common/adminHeader.jsp" %>
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

</body>
</html>