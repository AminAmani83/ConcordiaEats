    <style>
        .carousel-caption {
            font-size: 1.2em;
            background-color: rgba(0, 0, 0, 0.5);
            padding: 0.5rem;
            border-radius: 5px;
        }
        .rating {
            margin-bottom: 0.5rem;
        }
        .details {
            padding: 1.5rem;
        }
        .carousel-control-prev-icon, .carousel-control-next-icon {
            font-size: 2rem;
            background-color: rgba(0, 0, 0, 0.5);
            width: 50px;
            height: 50px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
        }
    </style>


<div class="mt-5">
    <div class="container">
        <div class="row bg-light">
            <div id="sliderproduct" class="carousel slide " data-ride="carousel" data-interval="10000">
                <div class="carousel-inner" role="listbox" data-interval="10000000">
                <c:set var="isBestSellerProductNotNull" value="${bestSellerProduct != null}" />
	 			<c:set var="isHighestRatedProductNotNull" value="${highestRatedProduct != null}" />
	 			<c:set var="isRecommendedProductNotNull" value="${recommendedProduct != null}" />
                <c:set var="isBestSellerProductAndHighestRatedProductEqual" value="${bestSellerProduct.id == highestRatedProduct.id}" />
				<c:set var="isBestSellerProductAndrecommendedProductEqual" value="${bestSellerProduct.id == recommendedProduct.id}" />
				<c:set var="isRecommendedProductAndHighestRatedProductEqual" value="${highestRatedProduct.id == recommendedProduct.id}" />
				<c:set var="isRecommendedProductAndHighestRatedProductAndBestSellerProductEqual" value="${isBestSellerProductAndHighestRatedProductEqual && isRecommendedProductAndHighestRatedProductEqual}" />
                <c:choose>
                
   					 <c:when test="${isBestSellerProductAndHighestRatedProductEqual}">
   							
   					 
                    <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller and Highest Rated!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${bestSellerProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="order/add/${bestSellerProduct.id}" method="get">
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
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${recommendedProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${recommendedProduct.price}</p>
                                        <p>${recommendedProduct.description}</p>
                                        <form action="order/add/${recommendedProduct.id}" method="get">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->
                      </c:when>
					<c:when test="${isBestSellerProductAndrecommendedProductEqual}">  
					   				
					
                  <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller and Recommended for you!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${bestSellerProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="order/add/${bestSellerProduct.id}" method="get">
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
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${highestRatedProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${highestRatedProduct.price}</p>
                                        <p>${highestRatedProduct.description}</p>
                                        <form action="order/add/${highestRatedProduct.id}" method="get">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item--> 
                     </c:when>
                   	<c:when test="${isRecommendedProductAndHighestRatedProductEqual}">  
                     
                  <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Hight Rated and Recommended for You!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${highestRatedProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${highestRatedProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${highestRatedProduct.price}</p>
                                        <p>${highestRatedProduct.description}</p>
                                        <form action="order/add/${highestRatedProduct.id}" method="get">
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
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${bestSellerProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="order/add/${bestSellerProduct.id}" method="get">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item--> 
                    </c:when>
                   <c:when test="${isRecommendedProductAndHighestRatedProductAndBestSellerProductEqual}">  
                    
                  <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller, Highest Rating and Recommended for You!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${bestSellerProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="order/add/${bestSellerProduct.id}" method="get">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->
                   	                    </c:when>
                   	
    <c:otherwise>
                  <div class="carousel-item active">
                        <div class="container text-center">
                            <div class="row">
                                <div class="col-sm-6 image">
                                    <img class="img-fluid" src="${bestSellerProduct.imagePath}"
                                         alt="${bestSellerProduct.name}">
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Best Seller</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${bestSellerProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${bestSellerProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${bestSellerProduct.price}</p>
                                        <p>${bestSellerProduct.description}</p>
                                        <form action="order/add/${bestSellerProduct.id}" method="get">
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
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${recommendedProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${recommendedProduct.price}</p>
                                        <p>${recommendedProduct.description}</p>
                                        <form action="order/add/${recommendedProduct.id}" method="get">
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
                                    <p class="carousel-caption" style="font-size: 1.2em">Our Highest Rating!</p>
                                </div>
                                <div class="col-sm-6">
                                    <div class="details">
                                        <h2>${highestRatedProduct.name}</h2>
                                        <div class="rating">
                                            <!-- Filled stars -->
                                            <c:forEach var="i" begin="1" end="${highestRatedProduct.rating}">
                                                <i class="fa fa-star text-warning"></i>
                                            </c:forEach>
                                        </div>
                                        <p>$${highestRatedProduct.price}</p>
                                        <p>${highestRatedProduct.description}</p>
                                        <form action="order/add/${highestRatedProduct.id}" method="get">
                                            <input type="submit" value="Add to Basket" class="btn btn-danger">
                                        </form>
                                    </div><!--enddetails-->
                                </div><!--endcol-->
                            </div><!--endrow-->
                        </div><!--endcontainer-->
                    </div><!--endcarousel-item-->

                    
				    </c:otherwise>
</c:choose>

  
                    
                      <a class="carousel-control-prev" href="#sliderproduct" role="button"
                       data-slide="prev">
                        <span class="carousel-control-prev-icon"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#sliderproduct" role="button"
                       data-slide="next">
                        <span class="carousel-control-next-icon"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div><!--endslidesliderproduct-->
            </div><!--endrow-->
        </div><!--endcontainer-->
    </div><!--endbg-product-->
</div>
