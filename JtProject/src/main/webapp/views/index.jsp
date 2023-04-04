<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
            integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
            integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
            crossorigin="anonymous"></script>
    <title>ConcordiaEats</title>
</head>
<body>

<section class="wrapper">
    <div class="container-fostrap">
        <%@include file="header.jsp" %>
        <%@include file="carousel.jsp" %>

        <div class="row px-4">
            <c:forEach items="${allProducts}" var="product">
                <div class="col-3 py-4">
                    <div class="card text-center">
                        <div class="card-header">
                            <div class="row">
                                <div class="col-2"></div>
                                <div class="col-8"><span>On Sale!</span></div>
                                <div class="col-2">
                                    <c:choose>
                                        <c:when test="${favoriteProducts.contains(product)}">
                                            <a href="/product/remove-favorite?productid=${product.id}"
                                               title="Remove from Your Favorites">
                                                <i class="fas fa-heart text-danger"></i>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="/product/make-favorite?productid=${product.id}"
                                               title="Add to Your Favorites">
                                                <i class="far fa-heart text-danger"></i>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                        <img class="card-img-top"
                             src="<c:out value="${product.imagePath}" />"
                             alt="<c:out value="${product.name}"/>"/>
                        <div class="card-body">
                            <h4 class="card-title">
                                <c:out value="${product.name}"/>
                            </h4>
                            <div class="rating">
                                <i class="fa fa-star text-warning"></i>
                                <i class="fa fa-star text-warning"></i>
                                <i class="fa fa-star text-warning"></i>
                                <i class="fas fa-star-half-alt text-warning"></i>
                            </div>
                            <p class="card-text">
                                <c:out value="${product.description}"/>
                            </p>
                            <form action="basket/add" method="get">
                                <input type="hidden" name="id" value="<c:out value="${product.id}" />">
                                <input type="submit" value="Add to Basket" class="btn btn-danger">
                            </form>
                        </div>
                        <div class="card-footer">
                            <small class="text-muted">$<c:out value="${product.price}"/> CAD</small>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>