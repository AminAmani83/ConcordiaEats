<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>Shopping Cart</title>
</head>
<body>
<section class="wrapper">
    <div class="container-fostrap">
<%@include file="common/header.jsp" %>
<div class="container-fluid" style="min-height: 500px;">

	<br><c:if test="${delivery == 0.0}">
        <b style="background-color: yellow;">There is currently an active promotion: Free Delivery!</b><br>
    </c:if><br>

    <table class="table">
        <tr>
            <th scope="col">Product Name</th>
			<th scope="col">Preview</th>
			<th scope="col">Quantity</th>
			<th scope="col">Price</th>
			<th scope="col">Discount (%)</th>
			<th scope="col">You Save</th>
			<th scope="col">You Pay</th>
			<th scope="col">Description</th>
			<th scope="col">Update</th>
			<th scope="col">Delete</th>
        </tr>
        <tbody>

        <c:forEach items="${allProducts}" var="product">
            <tr>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td><img
                        src= "${product.imagePath}"
                        height="100px" width="100px"></td>
                    <form action="../../../order/update/${product.id}" method="get">
               <td>
                    <input type="number" class="form-control border border-success" required name="quantity"
                           id="quantity" value="${ product.salesCount }" min="1" placeholder="Qty"
                           oninput="this.value = this.value.slice(0, 3)" style="width: 70px;">
                </td>
                <td>$
                    <c:out value="${product.price}"/> CAD
                </td>
                <td>
					<c:out value="${product.discountPercent}"/>%
				</td>
				<td>$
					<c:out value="${product.price * product.discountPercent / 100}"/> CAD
				</td>
				<td>$
					<c:out value="${product.price - (product.price * product.discountPercent / 100)}"/> CAD
				</td>
                <td>
                    <c:out value="${product.description}"/>
                </td>
                <td>
                         <input type="hidden" name="pid" value="${product.id}">
                        <input type="submit" value="Update" class="btn btn-warning">
                    </form>
                </td>
                <td>
                    <form action="../../../order/delete/${product.id}" method="get">
                        <input type="submit" value="Delete" class="btn btn-danger">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <b>SubTotal:</b> $ ${total} CAD<br>
	<b>Taxes:</b> $ ${total * 0.15} CAD<br>
	<b>Delivery:</b> $ ${delivery} CAD<br>
	<b>GrandTotal:</b> $ ${total * 1.15 + delivery} CAD<br><br>

<form action="../../../checkout" method="get">
	<c:if test="${not empty allProducts}">
    	<input type="submit" value="Checkout" class="btn btn-danger"><br><br>
  	</c:if>
  	<c:if test="${empty allProducts}">
    	<button type="button" class="btn btn-danger" onclick="alert('Your shopping cart is empty!')">Checkout</button><br><br>
 	</c:if>
  	<a href="/index">Continue Shopping</a>
</form>


</div>
    <%@include file="common/footer.jsp" %>
</div>
    </div>
</section>

</body>
</html>
