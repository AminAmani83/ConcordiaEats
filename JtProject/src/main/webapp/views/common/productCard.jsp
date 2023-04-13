<div class="col-3 py-4">
    <div class="card text-center">
        <div class="card-header">
            <div class="row">
                <div class="col-2"></div>
                <div class="col-8"><span>On Sale!</span></div>
                <div class="col-2">
                    <c:choose>
                        <c:when test="${favoriteProducts.contains(product)}">
                            <a href="/product/remove-favorite?productid=${product.id}&src=${productCardFavSrc}"
                               title="Remove from Your Favorites">
                                <i class="fas fa-heart text-danger"></i>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="/product/make-favorite?productid=${product.id}&src=${productCardFavSrc}"
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
                <c:choose>
                    <c:when test="${product.rating > 0}">
                        <!-- Filled stars -->
                        <c:forEach var="i" begin="1" end="${product.rating}">
                            <i class="fa fa-star text-warning"></i>
                        </c:forEach>
                        <!-- Half filled stars
                        <i class="fas fa-star-half-alt text-warning"></i> -->
                    </c:when>
                    <c:otherwise>
                        No rating yet!
                    </c:otherwise>
                </c:choose>
            </div>

            <div>
                <c:choose>
                    <c:when test="${purchasedProducts.contains(product)}">
                        <form action="product/rate-product" method="get">
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-warning" data-toggle="modal"
                                    data-target="#exampleModalCenter2-${product.id}"
                                    onclick="document.getElementById('productId').value =  '<c:out
                                            value="${product.id}"/>'; document.getElementById('productName').value =
                                            '<c:out value="${product.name}"/>';">You had it, now
                                rate it!
                            </button>

                            <!-- Modal -->
                            <div class="modal fade" id="exampleModalCenter2-${product.id}"
                                tabindex="-1"
                                role="dialog" aria-labelledby="exampleModalCenterTitle"
                                aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered" role="document">

                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="exampleModalLongTitle">Rate
                                                this product</h5>
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body text-center">

                                            <input class="form-control" type="hidden"
                                                name="productId" id="productId"
                                                value="${product.id}">

                                            <input class="form-control" type="hidden" readonly="readonly"
                                                name="src" id="src" value="${productCardFavSrc}">

                                            <div class="form-group">
                                                <input class="form-control" type="text"
                                                    readonly="readonly" name="productName"
                                                    id="productName" value="${product.name}">
                                            </div>
                                            <div class="form-group">
                                                <input class="form-control" type="number"
                                                    name="rating"
                                                    min="1" max="5"
                                                    id="rating"
                                                    value="5">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary"
                                                    data-dismiss="modal">Close
                                            </button>
                                            <button type="submit" class="btn btn-primary">Rate it!
                                            </button>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <!-- Button trigger modal -->
                            <button type="button" class="btn btn-warning" data-toggle="modal"
                                    data-target="#exampleModalCenter2-${product.id}-no-effect"
                                    onclick="document.getElementById('productId').value =  '<c:out
                                            value="${product.id}"/>'; document.getElementById('productName').value =
                                            '<c:out value="${product.name}"/>';">Try if first, rate it later!
                            </button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <p class="card-text">
                <c:out value="${product.description}"/>
            </p>
            <form action="order/add/${product.id}" method="get">
                 <input type="submit" value="Add to Basket" class="btn btn-danger">
            </form>
        </div>
        <div class="card-footer">
            <small class="text-muted">$<c:out value="${product.price}"/> CAD</small>
        </div>
    </div>
</div>