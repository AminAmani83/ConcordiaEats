<%@ page import="ca.concordia.eats.dto.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results</title>
</head>
<body>
<h1>Search Results</h1>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products != null && !products.isEmpty()) {
%>
<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Description</th>
        <th>Image URL</th>
    </tr>
    <% for (Product product : products) { %>
    <tr>
        <td><%= product.getId() %></td>
        <td><%= product.getName() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No products found.</p>
<% } %>
</body>
</html>
