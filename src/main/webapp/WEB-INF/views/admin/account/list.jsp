<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account List</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1>Account List</h1>
    <table class="table table-bordered table-striped mt-3">
        <thead class="thead-dark">
        <tr>
            <th>STT</th>
            <th>Tên đăng nhập</th>
            <th>Mật khẩu</th>
            <th>Họ tên</th>
            <th>Email</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="ac" varStatus="loop">
            <tr>
                <td>${loop.count}</td>
                <td>${ac.username}</td>
                <td>${ac.password}</td>
                <td>${ac.fullname}</td>
                <td>${ac.email}</td>
                <td>
                    <a href="/admin/account/update/${ac.username}" class="btn btn-warning btn-sm">Update</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/admin/account/create" class="btn btn-primary">Add</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
