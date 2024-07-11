<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="utf-8"%>
<form:form modelAttribute="ac" method="post">
 <p>Username: <form:input path="username"/></p>
 <form:errors path="username" />
 <p>Mật khẩu: <form:input  path="password"/></p>
 <form:errors path="password"/>
 <p>Họ tên:<form:input path="fullname"/></p>
 <form:errors path="fullname"/>
 <p>Email: <form:input path="email"/></p>
 <form:errors path="email"/>
 <button formaction="/admin/account/create">Save</button>
 <button formaction="/admin/account/update/${ac.username}">Update</button>
</form:form>