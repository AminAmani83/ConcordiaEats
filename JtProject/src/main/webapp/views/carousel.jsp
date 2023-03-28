<div class="mt-5">
    <div class="container">
        <div class="row bg-light">
            <div id="sliderproduct" class="carousel slide " data-ride="carousel" data-interval="10000">
                <div class="carousel-inner" role="listbox" data-interval="10000000">
                    <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fas fa-star-half-alt text-warning"></i>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="basket/add" method="get">
                                            <input type="hidden" name="id" value="${bestSellerProduct.id}">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->

                    <div class="carousel-item">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${highestRatedProduct.imagePath}"
                                         alt="${highestRatedProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Highest Rated!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${highestRatedProduct.name}</h2>
                                        <div class="rating">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                        </div>
                                        <p>$${highestRatedProduct.price}</p>
                                        <p>${highestRatedProduct.description}</p>
                                        <form action="basket/add" method="get">
                                            <input type="hidden" name="id" value="${highestRatedProduct.id}">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->

                    <div class="carousel-item">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${recommendedProduct.imagePath}"
                                         alt="${recommendedProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Recommended for You!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${recommendedProduct.name}</h2>
                                        <div class="rating">
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fa fa-star text-warning"></i>
                                            <i class="fas fa-star-half-alt text-warning"></i>
                                        </div>
                                        <p>$${recommendedProduct.price}</p>
                                        <p>${recommendedProduct.description}</p>
                                        <form action="basket/add" method="get">
                                            <input type="hidden" name="id" value="${recommendedProduct.id}">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->

                    <a class="carousel-control-prev fa fa-angle-left" href="#sliderproduct" role="button"
                       data-slide="prev">
                    </a>
                    <a class="carousel-control-next fa fa-angle-right" href="#sliderproduct" role="button"
                       data-slide="next">
                    </a>
                </div><!--endslidesliderproduct-->
            </div><!--endrow-->
        </div><!--endcontainer-->
    </div><!--endbg-product-->
</div>
