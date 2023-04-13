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

        <div class="categories">
            <a href="#" onmouseover="showMenu()">Categories</a>
            <ul id="menu">
                <li><input type="checkbox" name="category" value="category1"><label>Burger</label></li>
                <li><input type="checkbox" name="category" value="category2"><label>Soup</label></li>
                <li><input type="checkbox" name="category" value="category3"><label>Salad</label></li>
                <li><button onclick="searchProducts()">Search</button></li>
            </ul>
        </div>

        <style>
            .categories {
                position: relative;
                display: inline-block;
            }

            #menu {
                display: none;
                position: absolute;
                top: 30px;
                left: 0;
                z-index: 1;
            }

            #menu li {
                display: block;
            }

            #menu li a {
                display: block;
                padding: 5px 10px;
                text-decoration: none;
                color: #333;
                background-color: #fff;
            }

            #menu li a:hover {
                background-color: #f5f5f5;
            }
        </style>

        <script>
            function showMenu() {
                document.getElementById("menu").style.display = "block";
            }

            function searchProducts() {
                var selectedCategories = document.getElementsByName("category");
                var selectedCategoriesValues = [];
                for (var i = 0; i < selectedCategories.length; i++) {
                    if (selectedCategories[i].checked) {
                        selectedCategoriesValues.push(selectedCategories[i].value);
                    }
                }
                var queryString = selectedCategoriesValues.join("&");
                window.location.href = "search-results.jsp?" + queryString;
            }
        </script>


        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto"></ul>
            <ul class="navbar-nav">
                <li class="nav-item active"><a class="nav-link" href="/favorites">Favorites</a></li>
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
