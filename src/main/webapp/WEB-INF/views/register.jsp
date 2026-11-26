<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Đăng ký"/>
<%@ include file="shared/view-start.jspf" %>
<section class="panel narrow">
    <h1>Đăng ký</h1>
    <c:url var="registerUrl" value="/register"/>
    <form:form method="post" action="${registerUrl}" modelAttribute="accountForm" cssClass="stack">
        <%@ include file="shared/account-fields.jspf" %>
        <div class="actions"><button type="submit">Tạo tài khoản</button></div>
    </form:form>
    <p>Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
</section>
<%@ include file="shared/view-end.jspf" %>
