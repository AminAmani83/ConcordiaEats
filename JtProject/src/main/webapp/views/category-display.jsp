<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <%@include file="common/bootstrap.jsp" %>
    <title>ConcordiaEats - Search</title>
</head>
<body>

<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="common/header.jsp" %>
        <h4 class="ml-5 mt-3">Cateogires</h4>
        <div class="row px-4 justify-content-center">
            <c:choose>
                <c:when test="${empty products}">
                    <div class="col-8 text-center pt-4" style="min-height: 500px">
                        No Categories Found!<br><br>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${allCategories}" var="product">
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