<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Update a Product</title>
</head>
<body>
<%@include file="common/adminHeader.jsp" %>
<div class="jumbotron container border border-info">
    <h3>Update an Existing Product</h3>
    <form action="/admin/products/updateData" method="post" enctype="multipart/form-data" modelAttribute="product">
        <div class="row">
            <div class="col-sm-5">

                <div class="form-group">
                    <label for="id">Id</label>
                    <input type="number" readonly="readonly" class="form-control border border-success" name="id"
                           id="id" value="${ product.id }">
                </div>

                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" class="form-control border border-success" required name="name" id="name"
                           value="${product.name }" placeholder="Enter name">
                </div>

                <div class="form-group">
                    <label for="categoryid">Select Category</label>
                    <select class="form-control border border-success" name="categoryid" id="categoryid" readonly>
                        <option value="${product.id}" name="${product.name}">${product.name}</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="price">Price</label>
                    <input type="number" step="any" class="form-control border border-success" required name="price" id="price"
                           value="${ product.price }" min="1" placeholder="Price">
                </div>

                <div class="form-group">
                    <label for="discountPercent">Discount Percent</label>
                    <input type="number" step="0.1" class="form-control border border-success" required name="discountPercent" id="discountPercent"
                           value="${ product.discountPercent }" min="0" max="1" placeholder="Price">
                </div>

                <div class="form-group">
                    <label for="salesCount">Available Quantity</label>
                    <input type="number" class="form-control border border-success" required name="salesCount"
                           id="salesCount" value="${ product.salesCount }" min="1" placeholder="Quantity">
                </div>

            </div>

            <div class="col-sm-5">

                <div class="form-group">
                    <label for="description">Product Description</label>
                    <textarea class="form-control border border-success" rows="4" name="description" id="description"
                              placeholder="Product Details">${ product.description }</textarea>
                </div>

                <p>Product Image</p>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" name="productImage" value="${ product.imagePath }"
                           accept="image/jpeg, image/png" id="productImage" onchange="loadfile(event)"/>
                    <label class="custom-file-label border border-success" for="productImage">Choose file</label>
                    <script type="text/javascript">
                        var loadFile = function (event) {
                            var image = document.getElementById('imgPreview');
                            image.src = URL.createObjectURL(event.target.files[0]);
                        };
                    </script>
                </div>

                <div class="form-group">
                    <img src="#" id="imgPreview" height="100px" width="100px"
                         style="margin-top: 20px" alt=" ">
                </div>

                <input type="hidden" name="imgName">
                <input type="submit" value="Update Details" class="btn btn-primary">
            </div>
        </div>
    </form>
</div>


</body>
</html>