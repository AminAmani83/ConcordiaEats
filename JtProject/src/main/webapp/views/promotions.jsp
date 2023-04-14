<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Promotion Management</title>
</head>
<body class="bg-light">
<%@include file="common/adminHeader.jsp" %>
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
                <form action="create" method="post">
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
                            <option value="SITEWIDE_DISCOUNT_10">10% Site-wide Discount</option>
                            <option value="SITEWIDE_DISCOUNT_20">20% Site-wide Discount</option>
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
                                                <option value="SITEWIDE_DISCOUNT_10">10% Site-wide Discount</option>
                                                <option value="SITEWIDE_DISCOUNT_20">20% Site-wide Discount</option>
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

<div aria-live="polite" aria-atomic="true" style="position: relative; min-height: 200px;">
    <div class="toast" style="position: absolute; top: 0; right: 0;" data-delay="700" data-autohide="false">
        <div class="toast-header">
            <img src="../images/error.png" class="rounded mr-2" alt="Error">
            <strong class="mr-auto">Error!</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
            <!-- to be filled by JS -->
        </div>
    </div>
</div>


<script>
    $(document).ready(function(){
        const pageUrl = new URL(window.location.toLocaleString());
        if (pageUrl.searchParams.get('msg') === 'removalError') {
            $('.toast .toast-body').html('You cannot delete this promotion because it exists in purchase records' +
                ' for record-keeping.');
            $('.toast').toast('show');
        }
    });
</script>

</body>
</html>