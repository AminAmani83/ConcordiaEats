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

                

            </div>

            <div class="col-sm-5">
                <div class="form-group">
                    <label for="salesCount">Available Quantity</label>
                    <input type="number" class="form-control border border-success" required name="salesCount"
                           id="salesCount" value="${ product.salesCount }" min="1" placeholder="Quantity">
                </div>
                <div class="form-group">
                    <label for="description">Product Description</label>
                    <textarea class="form-control border border-success" rows="4" name="description" id="description"
                              placeholder="Product Details">${ product.description }</textarea>
                </div>

                <div class="form-group">
                    <label for="isOnSale">On Sale</label>
                    <input type="checkbox" name="onSale" <c:if test="${product.onSale}">checked</c:if>  id="onSale" value="true"/>
                </div>

                <div class="form-group">
                    <label for="imagePath">Product Image Path</label>
                    <input type="text" class="form-control border border-success" name="imagePath"
                           id="imagePath" value="${product.imagePath}" placeholder="Product Iamge Path">
                </div>

                <div class="form-group">
                    <input type="submit" value="Update Details" class="btn btn-primary">
                </div>
                
            </div>
        </div>
    </form>
</div>


</body>
</html>