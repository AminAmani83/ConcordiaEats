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

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto"></ul>
            <ul class="navbar-nav">
            	  <li class="nav-item active"><a class="nav-link" href="/order">Shopping Cart</a></li>
                <li class="nav-item active"><a class="nav-link" href="/favorites">Favorites</a></li>
                <li class="nav-item active"><a class="nav-link" href="/profileDisplay">Profile</a></li>
                <li class="nav-item active"><a class="nav-link" href="/logout">Logout</a></li>
            </ul>
        </div>

        <%@ include file="search-form.jsp" %>

    </div>
</nav>