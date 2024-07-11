<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Categories</title>
    <!-- Link CSS Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>STT</th>
            <th>ID</th>
            <th>Name</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${listcata}" var="cata" varStatus="loop">
            <tr>
                <td>${loop.count}</td>
                <td>${cata.id}</td>
                <td>${cata.name}</td>
                <td>
                    <a href="/admin/category/update/${cata.id}" class="btn btn-primary">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/admin/category/create" class="btn btn-success">Add</a>
</div>

<!-- Link JS Bootstrap (Optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
