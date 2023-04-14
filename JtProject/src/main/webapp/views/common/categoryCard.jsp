<div class="col-3 py-4">
    <div class="card text-center">
             
        <div class="card-body">
            <h4 class="card-title">
                <c:out value="${category.name}"/>
            </h4>

            <div class="py-3">
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