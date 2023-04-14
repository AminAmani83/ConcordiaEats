<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>ConcordiaEats</title>
</head>
<body>


<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <%@include file="carousel.jsp" %>

        <div class="row px-4" style="min-height: 100px">
            <c:forEach items="${allProducts}" var="product">
                <%@include file="common/productCard.jsp" %>
            </c:forEach>
        </div>

        <%@include file="common/footer.jsp" %>
    </div>
</section>

</body>
</html>