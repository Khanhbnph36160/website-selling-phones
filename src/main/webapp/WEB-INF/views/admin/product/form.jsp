<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="utf-8"%>
<form:form modelAttribute="prd" method="post">
<p>Tên sản phẩm: <form:input path="name"/></p>
    <form:errors path="name"></form:errors>
    <p>Giá: <form:input path="price"/></p>
    <form:errors path="price"></form:errors>
    <p>Hãng: <form:select path="category.id">
        <form:options items="${categoryList}" itemLabel="id" itemValue="id"/>
    </form:select>
    </p>
    <div class="form-group">
        <lable>Ảnh</lable>
        <form:input path="image" type="file" cssClass="form-control"/>
        <c:if test="${not empty prd.image}">
            <p>Current Image: ${prd.image}</p>
        </c:if>
        <form:errors path="image" style="color:red"></form:errors>

    </div>
    <form:errors path="image"></form:errors>
    <button formaction="/admin/product/create">Add</button>
    <button formaction="/admin/product/update/${prd.id}">Update</button>
</form:form>