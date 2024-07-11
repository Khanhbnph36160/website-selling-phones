<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="utf-8"%>
<form:form method="post" modelAttribute="cata">
    <p>ID: <form:input path="id"/></p>

    <p>Tên: <form:input path="name"/></p>

    <button formaction="/admin/category/create">Add</button>
    <button formaction="/admin/category/update/${cata.id}">Update</button>
</form:form>