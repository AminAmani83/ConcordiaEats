<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/index">Concordia Eats</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <c:choose>
            <c:when test="${!noCategoryFilter}">
                <c:catch var="exception"><span hidden>${query}</span></c:catch>
                <form method="get" class="pl-3" action="">
                    <div class="input-group">
                        <select class="selectpicker border-right-0 border" multiple data-live-search="true"
                                id="category-filter" name="category-filter[]" title="Filter By Category">
                            <c:forEach items="${allCategories}" var="category">
                                <option class="category-filter-option">${category.name}</option>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${empty exception}">
                                    <input type="hidden" name="query" value="${query}"/>
                                </c:when>
                            </c:choose>
                        </select>
                        <span class="input-group-append">
                          <button class="btn btn-outline-secondary border-left-0 border" type="submit">
                                <i class="fas fa-filter"></i>
                          </button>
                        </span>
                    </div>
                </form>

                <script>
                    $('select').selectpicker();

                    const pageUrl = new URL(window.location.toLocaleString());
                    const selectedCategories = pageUrl.searchParams.getAll('category-filter[]')

                    let catOptions = document.getElementById('category-filter').getElementsByTagName('option');
                    for (let i = 0; i < catOptions.length; i++) {
                        if (selectedCategories.includes(catOptions[i].innerText)) {
                            catOptions[i].selected = true;
                        }
                    }
                </script>

            </c:when>
        </c:choose>


        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto"></ul>
            <ul class="navbar-nav">
                <li class="nav-item active"><a class="nav-link" href="/favorites">Favorites
                    <c:choose>
                        <c:when test="${customerFavProductIsOnSale}">
                            <i class="fas fa-tags text-warning"></i>
                        </c:when>
                    </c:choose>
                </a></li>
                <li class="nav-item active"><a class="nav-link" href="/profileDisplay">Profile</a></li>
                <li class="nav-item active"><a class="nav-link" href="/logout">Logout</a></li>
                <li class="nav-item active"><a class="nav-link" href="/order"><i class="fas fa-cart-plus"></i></a>
                </li>
            </ul>
        </div>

        <c:choose>
            <c:when test="${!noSearchForm}">
                <%@ include file="search-form.jsp" %>
            </c:when>
        </c:choose>

        <c:choose>
            <c:when test="${!noContactUsLink}">
                <a href="/contact" id="contactUs" title="Contact Us">
                    <i class="far fa-envelope text-info bg-light py-1 px-2 border border-info rounded"></i>
                </a>
            </c:when>
        </c:choose>

    </div>

</nav>

