<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sản phẩm</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Custom styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .product-image {
            width: 50px;
            height: auto;
        }
        .table thead th {
            background-color: #343a40;
            color: #ffffff;
        }
        .table tbody tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center">Danh sách sản phẩm</h2>
    <div class="table-responsive mt-4">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>STT</th>
                <th>Id</th>
                <th>Tên sản phẩm</th>
                <th>Giá</th>
                <th>Hãng</th>
                <th>Ảnh</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listSP}" varStatus="loop" var="prd">
                <tr>
                    <td>${loop.count}</td>
                    <td>${prd.id}</td>
                    <td>${prd.name}</td>
                    <td>${prd.price}</td>
                    <td>${prd.category.name}</td>
                    <td><img src="/static/images/${prd.image}" class="product-image" alt="${prd.name}" /></td>
                    <td>
                        <a href="/admin/product/update/${prd.id}" class="btn btn-sm btn-primary">Update</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="text-center mt-4">
        <a href="/admin/product/create" class="btn btn-success">Add New Product</a>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>
