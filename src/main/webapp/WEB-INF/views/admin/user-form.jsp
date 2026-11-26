<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="${editing ? 'Sửa người dùng' : 'Thêm người dùng'}"/>
<%@ include file="../shared/view-start.jspf" %>
<section class="panel narrow">
    <h1><c:out value="${pageTitle}"/></h1>
    <c:url var="actionUrl" value="${formPath}"/>
    <form:form method="post" action="${actionUrl}" modelAttribute="accountForm" cssClass="stack">
        <%@ include file="../shared/account-fields.jspf" %>
        <div class="actions">
            <button type="submit">Lưu người dùng</button>
            <a class="button quiet" href="<c:url value='/admin/users'/>">Hủy</a>
        </div>
    </form:form>
</section>
<%@ include file="../shared/view-end.jspf" %>
