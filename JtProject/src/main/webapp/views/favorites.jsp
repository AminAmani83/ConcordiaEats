<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>ConcordiaEats - Favorites</title>
</head>
<body>

<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <h4 class="ml-5 mt-3">Favorites</h4>
        <div class="row px-4 justify-content-center">
            <c:choose>
                <c:when test="${empty favoriteProducts}">
                    <div class="col-8 text-center pt-4" style="min-height: 500px">
                        Your Favorite Products Will be Displayed Here.<br><br>
                        This page is currently empty. You can click on the red heart icon (<i
                            class="far fa-heart text-danger"></i>)<br>
                        near each product to add it to your favorite products.<br><br>
                        <a href="/index">Back to Home Page</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${favoriteProducts}" var="product">
                        <%@include file="common/productCard.jsp" %>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <%@include file="common/footer.jsp" %>
    </div>
</section>

</body>
</html>