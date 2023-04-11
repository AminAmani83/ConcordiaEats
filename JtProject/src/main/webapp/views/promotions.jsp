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
    <title>Category Management</title>
</head>
<body class="bg-light">
<%@include file="adminHeader.jsp" %>
<div class="container">

    <!-- Button trigger modal -->
    <button type="button" style="margin: 20px 0" class="btn btn-primary"
            data-toggle="modal" data-target="#exampleModalCenter">Add
        Promotion
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1"
         role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="createPromotion" method="post">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Add New
                            Promotion</h5>
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body  text-center">
                        <input type="text" name="name" class="form-control"
                               id="name" required="required" placeholder="Promotion Name">
                        <input type="date" name="promotionStartDate" class="form-control"
                               id="startDate" required="required" placeholder="Promotion Start Date">
                        <input type="date" name="promotionEndDate" class="form-control"
                               id="endDate" required="required" placeholder="Promotion End Date">
                        <label for="promotionType">Promotion Type:</label>
                        <select name="promotionType" class="form-control" id="promotionType" required>
                            <option value="" selected disabled hidden>Choose a promotion type</option>
                            <option value="10% Site-wide Discount">Discount</option>
                            <option value="10% purchase Discount">Discount</option>
                            <option value="Free Shipping">Free Shipping</option>
                            <option value="Buy One Get One Free">Buy One Get One Free</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary"
                                data-dismiss="modal">Close
                        </button>
                        <input type="submit" value="Save Changes" class="btn btn-primary">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <br>

    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">SN</th>
            <th scope="col">Promotion Name</th>
            <th scope="col">Start Date</th>
            <th scope="col">End Date</th>
            <th scope="col">Type</th>
            <th scope="col">Delete</th>
            <th scope="col">Update</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allPromotions}" var="promotion">
            <tr>
                <td><c:out value="${promotion.id}"/></td>
                <td><c:out value="${promotion.name}"/></td>
                <td><c:out value="${promotion.startDate}"/></td>
                <td><c:out value="${promotion.endDate}"/></td>
                <td><c:out value="${promotion.type}"/></td>

                <td>
                    <form action="promotions/delete" method="get">
                        <input type="hidden" name="id" value="<c:out value="${promotion.id}" />">
                        <input type="submit" value="Delete" class="btn btn-danger">
                    </form>
                </td>

                <td>
                    <form action="promotions/update" method="get">

                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-warning" data-toggle="modal"
                                data-target="#exampleModalCenter2"
                                onclick="document.getElementById('promotionName').value =
                                            '<c:out value="${promotion.name}"/>';
                                        document.getElementById('promotionId').value =
                                            '<c:out value="${promotion.id}"/>';
                                        document.getElementById('promotionStartDate').value =
                                            '<c:out value="${promotion.startDate}"/>';
                                        document.getElementById('promotionEndDate').value =
                                            '<c:out value="${promotion.endDate}"/>';
                                        document.getElementById('promoType').value =
                                            '<c:out value="${promotion.type}"/>';">Update
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModalCenter2" tabindex="-1"
                             role="dialog" aria-labelledby="exampleModalCenterTitle"
                             aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">

                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle2">Update
                                            Product Details</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body text-center">
                                        <div class="form-group">
                                            <input class="form-control" type="number"
                                                   readonly="readonly" name="promotionId" id="promotionId" value="0">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="promotionName"
                                                   id="promotionName"
                                                   value="promotionName">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="date" name="promotionStartDate"
                                                   id="promotionStartDate"
                                                   value="promotionStartDate">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="date" name="promotionEndDate"
                                                   id="promotionEndDate"
                                                   value="promotionEndDate">
                                        </div>
                                        <div class="form-group">
                                            <label for="promoType">Promotion Type:</label>
                                            <select name="promoType" class="form-control" id="promoType" required>
                                                <option value="" selected disabled hidden>Choose a promotion type</option>
                                                <option value="10% Site-wide Discount">Discount</option>
                                                <option value="10% purchase Discount">Discount</option>
                                                <option value="Free Shipping">Free Shipping</option>
                                                <option value="Buy One Get One Free">Buy One Get One Free</option>
                                            </select>
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