<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Category Management</title>
</head>
<body class="bg-light">
<%@include file="common/adminHeader.jsp" %>
<div class="container">
    <!-- Button trigger modal -->
    <button type="button" style="margin: 20px 0" class="btn btn-primary"
            data-toggle="modal" data-target="#exampleModalCenter">Add
        Category
    </button>

    <!-- Modal -->
    <div class="modal fade" id="exampleModalCenter" tabindex="-1"
         role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <form action="sendcategory" method="get">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Add New
                            Category</h5>
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body  text-center">
                        <input type="text" name="categoryname" class="form-control"
                               id="name" required="required" placeholder="Category name">
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
            <th scope="col">Category Name</th>
            <th scope="col">Delete</th>
            <th scope="col">Update</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${allCategories}" var="category">
            <tr>
                <td><c:out value="${category.id}"/></td>
                <td><c:out value="${category.name}"/></td>

                <td>
                    <form action="categories/delete" method="get">
                        <input type="hidden" name="id" value="<c:out value="${category.id}" />">
                        <input type="submit" value="Delete" class="btn btn-danger">
                    </form>
                </td>

                <td>
                    <form action="categories/update" method="get">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-warning" data-toggle="modal"
                                data-target="#exampleModalCenter2-${category.id}"
                                onclick="document.getElementById('categoryname').value =  '<c:out
                                        value="${category.name}"/>'; document.getElementById('categoryid').value =
                                        '<c:out value="${category.id}"/>';">Update
                        </button>

                        <!-- Modal -->
                        <div class="modal fade" id="exampleModalCenter2-${category.id}" tabindex="-1"
                             role="dialog" aria-labelledby="exampleModalCenterTitle"
                             aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">

                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Update
                                            Product Details</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body text-center">

                                        <input class="form-control" type="hidden"
                                        name="categoryid" id="categoryid" value="${category.id}">

                                        <div class="form-group">
                                            <input class="form-control" type="number"
                                                   readonly="readonly" name="categoryid" id="categoryid" value="${category.id}">
                                        </div>
                                        <div class="form-group">
                                            <input class="form-control" type="text" name="categoryname"
                                                   id="categoryname"
                                                   value="${category.name}">
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
            $('.toast .toast-body').html('The category could <u>not</u> be deleted. Please first delete all the products within this category.');
            $('.toast').toast('show');
        }
    });
</script>

</body>
</html>