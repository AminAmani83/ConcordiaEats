<div class="rating">
    <c:choose>
        <c:when test="${thisProduct.rating > 0}">
            <!-- Filled stars -->
            <c:forEach var="i" begin="1" end="${thisProduct.rating}">
                <i class="fa fa-star text-warning"></i>
            </c:forEach>
            <!-- Half filled stars
            <i class="fas fa-star-half-alt text-warning"></i> -->
        </c:when>
        <c:otherwise>
            <span class="text-warning">No rating yet!</span>
        </c:otherwise>
    </c:choose>
</div>
