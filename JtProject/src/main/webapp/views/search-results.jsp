<%@ page import="ca.concordia.eats.dto.Product" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #555;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #ddd;
        }

        .product-image {
            width: 100px;
            height: auto;
        }
    </style>

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
        <td><%= product.getPrice()%></td>
        <td><%= product.getDescription()%></td>
        <td><img src="<%= product.getImagePath() %>" alt="<%= product.getName() %>"></td>
    </tr>
    <% } %>
</table>

<% } else { %>
<p>No products found.</p>
<% } %>
</body>
</html>
