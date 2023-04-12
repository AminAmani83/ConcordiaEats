<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Add a Product</title>
</head>
<body>
<%@include file="common/adminHeader.jsp" %>
<div class="jumbotron container border border-info">
    <h3>Add a new Product</h3>
    <c:url var="add_product_url" value="/admin/products/add"/>
    <form action="${add_product_url}" method="post" modelAttribute="product">
        <div class="row">
            <div class="col-sm-5">

                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control border border-warning" required name="name" id="name"
                           placeholder="Enter name">
                </div>

                <div class="form-group">
                    <label for="categoryid">Select Category</label>
                    <select class="form-control border border-warning" name="categoryid" id="categoryid" required>
                        <c:forEach items="${allCategories}" var="category">
                            <option value="${category.id}" name="${category.name}">${category.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="number" class="form-control border border-warning" required name="price" id="price"
                           min="1" placeholder="Price">
                </div>

                <div class="form-group">
                    <label for="salesCount">Available Quantity</label>
                    <input type="number" class="form-control border border-warning" required name="salesCount"
                           id="salesCount" min="1" placeholder="Quantity">
                </div>
            </div>

            <div class="col-sm-5"><br>

                <div class="form-group">
                    <label for="description">Product Description</label>
                    <textarea class="form-control border border-warning" rows="4" name="description" id="description"
                              placeholder="Product Details" value="no product details"></textarea>
                </div>

                <p>Product Image</p>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" name="productImage" accept="image/jpeg, image/png"
                           id="productImage" onchange="loadfile(event)"/>
                    <label class="custom-file-label border border-warning" for="productImage">Choose file</label>
                </div>

                <div class="form-group">
                    <img src="<c:url value='/resources/Product Images/one.jpg'/>" alt="Hello" id="imgPreview"
                         height="100px" width="100px"
                         style="margin-top: 20px">
                </div>

                <input type="hidden" name="imgName">
                <input type="submit" class="btn btn-primary">
            </div>
        </div>
    </form>
</div>

<script type="text/javascript">
    var loadFile = function (event) {
        var image = document.getElementById('imgPreview');
        image.src = URL.createObjectURL(event.target.files[0]);
    };
</script>
</body>
</html>